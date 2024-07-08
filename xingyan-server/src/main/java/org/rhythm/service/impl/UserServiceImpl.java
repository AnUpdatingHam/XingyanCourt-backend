package org.rhythm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.rhythm.constant.MessageConstant;
import org.rhythm.constant.PasswordConstant;
import org.rhythm.constant.StatusConstant;
import org.rhythm.dto.UserDTO;
import org.rhythm.dto.UserLoginDTO;
import org.rhythm.dto.UserPageQueryDTO;
import org.rhythm.entity.User;
import org.rhythm.entity.UserExtension;
import org.rhythm.exception.AccountLockedException;
import org.rhythm.exception.AccountNotFoundException;
import org.rhythm.exception.PasswordErrorException;
import org.rhythm.mapper.UserMapper;
import org.rhythm.result.PageResult;
import org.rhythm.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    
    @Override
    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(userPageQueryDTO.getPage(), userPageQueryDTO.getPageSize());
        Page<User> page = userMapper.pageQuery(userPageQueryDTO);
        for (User user : page.getResult())
            user.setPassword("******");
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(UserDTO userDTO) {
        User user = new User();
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        BeanUtils.copyProperties(userDTO,user);
        user.setStatus(StatusConstant.ENABLE);
        user.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        userMapper.insert(user);
    }

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        User user = userMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (user.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return user;
    }

    @Override
    public void starOrStop(Integer status, Long id) {
        User user = User.builder()
                .status(status)
                .id(id)
                .build();
        userMapper.update(user);
    }

    @Override
    public User getById(Long id) {
        User user = userMapper.getById(id);
        user.setPassword("******");
        return user;
    }

    @Override
    public void update(UserDTO userDTO) {
        User user = new User();
        user.setUpdateTime(LocalDateTime.now());
        BeanUtils.copyProperties(userDTO, user);
        userMapper.update(user);
    }
}

package org.rhythm.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import org.rhythm.constant.JwtClaimsConstant;
import org.rhythm.dto.UserDTO;
import org.rhythm.dto.UserLoginDTO;
import org.rhythm.dto.UserPageQueryDTO;
import org.rhythm.entity.User;
import org.rhythm.entity.UserExtension;
import org.rhythm.properties.JwtProperties;
import org.rhythm.result.PageResult;
import org.rhythm.result.Result;
import org.rhythm.service.UserService;
import org.rhythm.utils.JwtUtil;
import org.rhythm.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 */
@CrossOrigin
@RestController
@RequestMapping("/user/user")
@Slf4j
@Tag(name = "用户接口", description = "用户的增删改查")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);

        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = org.rhythm.vo.UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .createTime(user.getCreateTime())
                .updateTime(user.getUpdateTime())
                .imageUrl(user.getImageUrl())
                .build();
        log.info("userVO: {}", userLoginVO);

        return Result.success(userLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @Operation(summary = "用户退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        log.info("用户退出");

        return Result.success();
    }

    /**
     *新增用户
     * @param UserDTO
     * @return
     */
    @Operation(summary = "新增用户")
    @PostMapping
    public Result add(@RequestBody UserDTO UserDTO){
        log.info("新增用户: {}",UserDTO);
        userService.add(UserDTO);
        return Result.success();
    }

    /**
     * 分页查询用户
     * @param UserPageQueryDTO
     * @return
     */
    @Operation(summary = "分页查询用户")
    @GetMapping("/page")
    public Result<PageResult> page(UserPageQueryDTO UserPageQueryDTO){
        log.info("分页查询用户，参数为：{}",UserPageQueryDTO);
        PageResult pageResult = userService.pageQuery(UserPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用、禁用用户账号
     * @param status
     * @param id
     * @return
     */
    @Operation(summary = "启用、禁用用户账号")
    @PostMapping("/status/{id}/{status}")
    public Result startOrStop(@PathVariable  Long id, @PathVariable Integer status){
        log.info("启用、禁用用户账号: {}.{}",status,id);
        userService.starOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询用户")
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id){
        log.info("根据id查询用户:{}",id);
        User user = userService.getById(id);
        System.out.println(user);
        return Result.success(user);
    }

    /**
     * 根据id修改用户
     * @param UserDTO
     * @return
     */
    @Operation(summary = "根据id修改用户")
    @PutMapping
    public Result update(@RequestBody UserDTO UserDTO){
        log.info("根据id修改用户:{}",UserDTO);
        userService.update(UserDTO);
        return Result.success();
    }
}

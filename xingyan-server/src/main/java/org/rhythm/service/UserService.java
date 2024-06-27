package org.rhythm.service;

import org.rhythm.dto.UserDTO;
import org.rhythm.dto.UserLoginDTO;
import org.rhythm.dto.UserPageQueryDTO;
import org.rhythm.entity.User;
import org.rhythm.entity.UserExtension;
import org.rhythm.result.PageResult;
import org.springframework.stereotype.Service;

public interface UserService {
    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);

    void add(UserDTO userDTO);

    User login(UserLoginDTO userLoginDTO);

    void starOrStop(Integer status, Long id);

    User getById(Long id);

    void update(UserDTO userDTO);
}

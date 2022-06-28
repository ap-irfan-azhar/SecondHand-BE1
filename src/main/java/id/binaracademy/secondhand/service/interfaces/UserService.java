package id.binaracademy.secondhand.service.interfaces;


import id.binaracademy.secondhand.dto.UserInfoDto;
import id.binaracademy.secondhand.dto.UserRegisterDto;
import id.binaracademy.secondhand.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(UserRegisterDto user);
    User findUserById(Long id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    List<User> findAllUsers();
    User updateUser(Long id, UserRegisterDto user);
    void deleteUser(Long id);
    User addRoleToUser(Long userId, String roleName);
    String login(String username, String password);
    UserInfoDto findUserInfoDtoById(Long id);
    List<UserInfoDto> findAllUserInfoDtos();

    User updateUserInfo(Long id, UserInfoDto userInfoDto);
}


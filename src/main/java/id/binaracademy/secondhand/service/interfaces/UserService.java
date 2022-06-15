package id.binaracademy.secondhand.service.interfaces;


import id.binaracademy.secondhand.dto.PostUserDto;
import id.binaracademy.secondhand.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(PostUserDto user);
    User findUserById(Long id);
    User findUserByUsername(String username);
    List<User> findAllUsers();
    User updateUser(Long id, PostUserDto user);
    void deleteUser(Long id);
    User addRoleToUser(Long userId, String roleName);
    String login(String username, String password);
}


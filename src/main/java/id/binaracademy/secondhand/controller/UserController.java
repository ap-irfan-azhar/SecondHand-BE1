package id.binaracademy.secondhand.controller;

import id.binaracademy.secondhand.dto.UserInfoDto;
import id.binaracademy.secondhand.dto.UserRegisterDto;
import id.binaracademy.secondhand.entity.Role;
import id.binaracademy.secondhand.entity.User;
import id.binaracademy.secondhand.service.impl.RoleServiceImpl;
import id.binaracademy.secondhand.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;

    @GetMapping("/")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/info")
    public List<UserInfoDto> findAllUserDtos() {
        return userService.findAllUserInfoDtos();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/{id}/info")
    public UserInfoDto findUserInfoDto(@PathVariable Long id) {
        return userService.findUserInfoDtoById(id);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegisterDto user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserRegisterDto user) {
        return userService.updateUser(id, user);
    }

    @PutMapping("/{id}/info")
    public User updateUserInfo(@PathVariable Long id, UserInfoDto userInfoDto) {
        return userService.updateUserInfo(id, userInfoDto);
    }

    @PutMapping("/{id}/add_role")
    public User addRoleToUser(@PathVariable Long userId, @RequestParam String roleName) {
        return userService.addRoleToUser(userId, roleName);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/role/")
    public List<Role> findAllRoles() {
        return roleService.findAllRoles();
    }

    @GetMapping("/role/{id}")
    public Role findRoleById(@PathVariable Long id) {
        return roleService.findRoleById(id);
    }

    @PostMapping("/role/")
    public Role addRole(@RequestParam String roleName) {
        return roleService.saveRole(roleName);
    }
}

package id.binaracademy.secondhand.controller;

import id.binaracademy.secondhand.dto.PostUserDto;
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

    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody PostUserDto user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody PostUserDto user) {
        return userService.updateUser(id, user);
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

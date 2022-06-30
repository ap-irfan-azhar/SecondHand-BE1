package id.binaracademy.secondhand.controller;

import id.binaracademy.secondhand.dto.UserInfoDto;
import id.binaracademy.secondhand.dto.UserRegisterDto;
import id.binaracademy.secondhand.entity.Role;
import id.binaracademy.secondhand.entity.User;
import id.binaracademy.secondhand.service.impl.RoleServiceImpl;
import id.binaracademy.secondhand.service.impl.UserServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> findAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortType
    ) {
        Page<User> pageUser = userService.findAllUsers(page, size, sortBy, sortType);
        if (pageUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("users", pageUser.getContent());
        response.put("currentPage", pageUser.getNumber());
        response.put("totalItem", pageUser.getTotalElements());
        response.put("totalPage", pageUser.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
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

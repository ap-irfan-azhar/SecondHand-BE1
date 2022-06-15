package id.binaracademy.secondhand.service.impl;

import id.binaracademy.secondhand.dto.PostUserDto;
import id.binaracademy.secondhand.entity.Role;
import id.binaracademy.secondhand.entity.User;
import id.binaracademy.secondhand.repository.UserRepository;
import id.binaracademy.secondhand.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User saveUser(PostUserDto user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("User with username %s already exists", user.getUsername())
            );
        }
        Role userRole = roleService.findRoleByName("BUYER");
        Collection<Role> roles = new ArrayList<>(Arrays.asList(
                userRole
        ));
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        User userToSave = new User();
        userToSave.setUsername(user.getUsername());
        userToSave.setEmail(user.getEmail());
        userToSave.setPassword(encryptedPassword);
        userToSave.setRoles(roles);

        return userRepository.save(userToSave);
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("User with id %s not found", id.toString())
            );
        }
        return user.get();
    }

    @Override
    public User findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("User with username %s not found", username)
            );
        }
        return user.get();
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, PostUserDto user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("User with id %s not found", id.toString())

            );
        }
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        User userToSave = existingUser.get();
        userToSave.setUsername(user.getUsername());
        userToSave.setEmail(user.getEmail());
        userToSave.setPassword(encryptedPassword);

        return userRepository.save(userToSave);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("User with id %s not found", id.toString())
            );
        }
        userRepository.deleteById(id);
    }

    @Override
    public User addRoleToUser(Long userId, String roleName) {
        User user = findUserById(userId);
        Role role = roleService.findRoleByName(roleName);
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public String login(String username, String password) {
        String message;

        User user = findUserByUsername(username);
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        if (user.getPassword().equals(encryptedPassword)) {
            message = "login success";
        } else {
            message = "login failed";
        }
        return message;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format(
                            "User with username %s not found",
                            username
                    )
            );
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}

package id.binaracademy.secondhand.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.binaracademy.secondhand.dto.UserInfoDto;
import id.binaracademy.secondhand.dto.UserRegisterDto;
import id.binaracademy.secondhand.entity.Role;
import id.binaracademy.secondhand.entity.User;
import id.binaracademy.secondhand.repository.UserRepository;
import id.binaracademy.secondhand.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User saveUser(UserRegisterDto user) {
        String emailRegexPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Optional<User> foundUserByUsername = userRepository.findByUsername(user.getUsername());
        Optional<User> foundUserByEmail = userRepository.findByEmail(user.getEmail());

        boolean isEmailValid = Pattern
                .compile(emailRegexPattern)
                .matcher(user.getEmail())
                .matches();

        if (!isEmailValid) {
            throw new IllegalArgumentException("email is not valid");
        }

        if (foundUserByUsername.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("User with username %s already exists", user.getUsername())
            );
        } else if (foundUserByEmail.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("User with email %s already exists", user.getEmail())
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
        if (!user.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("User with id %s not found", id.toString())
            );
        }
        return user.get();
    }

    @Override
    public User findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("User with username %s not found", username)
            );
        }
        return user.get();
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("User with email %s not found", email)
            );
        }
        return user.get();
    }


    @Override
    public Page<User> findAllUsers(int page, int size, String sortParameter, String sortType) {
        String sortBy;
        if (sortParameter.equals("username") ||
                sortParameter.equals("city") ||
                sortParameter.equals("address") ||
                sortParameter.equals("phoneNumber")
        ) {
            sortBy = sortParameter;
        } else {
            sortBy = "id";
        }
        Sort sort = sortType.equals("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
            );
        return userRepository.findAll(pageable);
    }

    @Override
    public User updateUser(Long id, UserRegisterDto user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (!existingUser.isPresent()) {
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
        if (!user.isPresent()) {
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
    public UserInfoDto findUserInfoDtoById(Long id) {
        User user = findUserById(id);
        return new UserInfoDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles(),
                user.getCity(),
                user.getAddress(),
                user.getPhoneNumber()
        );
    }

    @Override
    public List<UserInfoDto> findAllUserInfoDtos() {
        List<User> users = userRepository.findAll();
        List<UserInfoDto> userInfoDtos = new ArrayList<>();
        for (User user: users) {
            userInfoDtos.add(
                    new UserInfoDto(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getRoles(),
                            user.getCity(),
                            user.getAddress(),
                            user.getPhoneNumber()
                    )
            );
        }
        return userInfoDtos;
    }

    @Override
    public User updateUserInfo(Long id, UserInfoDto userInfoDto) {
        User existingUser = findUserById(id);
        existingUser.setEmail(userInfoDto.getEmail());
        existingUser.setUsername(userInfoDto.getUsername());
        existingUser.setRoles(userInfoDto.getRoles());
        existingUser.setCity(userInfoDto.getCity());
        existingUser.setAddress(userInfoDto.getAddress());
        existingUser.setPhoneNumber(userInfoDto.getPhoneNumber());
        return userRepository.save(existingUser);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user;
                try {
                    user = userRepository.findByUsername(username).get();
                } catch (Exception e) {
                    throw new NotFoundException(e.getMessage());
                }
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 12 * 60 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception e){
                response.setHeader("error",e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else{
            throw new NotFoundException("Refresh token not found");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        String emailRegexPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        User user = null;

        if (Pattern.compile(emailRegexPattern).matcher(usernameOrEmail).matches()) {
            user = findUserByEmail(usernameOrEmail);
        } else {
            user = findUserByUsername(usernameOrEmail);
        }

        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format(
                            "User with username %s not found",
                            usernameOrEmail
                    )
            );
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}

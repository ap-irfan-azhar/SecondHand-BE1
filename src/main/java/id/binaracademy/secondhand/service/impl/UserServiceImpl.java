package id.binaracademy.secondhand.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.binaracademy.secondhand.dto.UpdateUserInfoDto;
import id.binaracademy.secondhand.dto.UserRegisterDto;
import id.binaracademy.secondhand.entity.Role;
import id.binaracademy.secondhand.entity.UserEntity;
import id.binaracademy.secondhand.entity.UserInfo;
import id.binaracademy.secondhand.repository.UserInfoRepository;
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

    public static final String EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserEntity saveUser(UserRegisterDto user) {
        Optional<UserEntity> foundUserByUsername = userRepository.findByUsername(user.getUsername());
        Optional<UserEntity> foundUserByEmail = userRepository.findByEmail(user.getEmail());

        boolean isEmailValid = Pattern
                .compile(EMAIL_REGEX_PATTERN)
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
        UserEntity userEntityToSave = new UserEntity();
        userEntityToSave.setUsername(user.getUsername());
        userEntityToSave.setEmail(user.getEmail());
        userEntityToSave.setPassword(encryptedPassword);
        userEntityToSave.setRoles(roles);

        return userRepository.save(userEntityToSave);
    }

    @Override
    public UserInfo findUserById(Long id) {
        Optional<UserInfo> user = userInfoRepository.findById(id);
        if (!user.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("User with id %s not found", id.toString())
            );
        }
        return user.get();
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("User with username %s not found", username)
            );
        }
        return user.get();
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("User with email %s not found", email)
            );
        }
        return user.get();
    }


    @Override
    public Page<UserInfo> findAllUsers(int page, int size, String sortParameter, String sortType) {
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
        return userInfoRepository.findAll(pageable);
    }

    @Override
    public UserInfo updateUser(Long id, UpdateUserInfoDto user) {
       boolean isEmailValid = Pattern
                .compile(EMAIL_REGEX_PATTERN)
                .matcher(user.getEmail())
                .matches();
        if (!isEmailValid) {
            throw new IllegalArgumentException("email is not valid");
        }
        Optional<UserInfo> existingUser = userInfoRepository.findById(id);
        if (!existingUser.isPresent()) {
            throw new NotFoundException(
                    String.format("User with id %s not found", id.toString())

            );
        }
        UserInfo userToSave = existingUser.get();
        if (!userToSave.getUsername().equals(user.getUsername())) {
            Optional<UserInfo> foundByUsername = userInfoRepository.findByUsername(user.getUsername());
            if (foundByUsername.isPresent()) {
                throw new IllegalArgumentException(
                        String.format("user with username %s is already exist", user.getUsername())
                );
            }
        }
        if (!userToSave.getEmail().equals(user.getEmail())) {
            Optional<UserInfo> foundByEmail = userInfoRepository.findByEmail(user.getEmail());
            if (foundByEmail.isPresent()) {
                throw new IllegalArgumentException(
                        String.format("user with email %s is already exist", user.getEmail())
                );
            }
        }

        userToSave.setUsername(user.getUsername());
        userToSave.setEmail(user.getEmail());
        userToSave.setCity(user.getCity());
        userToSave.setAddress(user.getAddress());
        userToSave.setPhoneNumber(user.getPhoneNumber());

        return userInfoRepository.save(userToSave);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("User with id %s not found", id.toString())
            );
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserInfo registerAsSeller(Long userId) {
        Optional<UserInfo> foundUser = userInfoRepository.findById(userId);
        if (!foundUser.isPresent()) {
            throw new NotFoundException(
                    String.format("User with id: %s not found", userId)
            );
        }
        UserInfo user = foundUser.get();
        Role sellerRole = roleService.findRoleByName("SELLER");
        if (user.getRoles().contains(sellerRole)) {
            throw new IllegalArgumentException(
                    String.format(
                            "user with id %s is already a seller", user.getId()
                    )
            );
        }
        Collection<Role> newRoles = user.getRoles();
        newRoles.add(sellerRole);
        return userInfoRepository.save(user);
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
                UserEntity userEntity;
                try {
                    userEntity = userRepository.findByUsername(username).get();
                } catch (Exception e) {
                    throw new NotFoundException(e.getMessage());
                }
                String accessToken = JWT.create()
                        .withSubject(userEntity.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 12 * 60 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", userEntity.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
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
        UserEntity userEntity = null;

        if (Pattern.compile(EMAIL_REGEX_PATTERN).matcher(usernameOrEmail).matches()) {
            userEntity = findUserByEmail(usernameOrEmail);
        } else {
            userEntity = findUserByUsername(usernameOrEmail);
        }

        if (userEntity == null) {
            throw new UsernameNotFoundException(
                    String.format(
                            "User with username %s not found",
                            usernameOrEmail
                    )
            );
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userEntity.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }
}

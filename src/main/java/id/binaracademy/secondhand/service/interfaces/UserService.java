package id.binaracademy.secondhand.service.interfaces;


import id.binaracademy.secondhand.dto.UpdateUserInfoDto;
import id.binaracademy.secondhand.dto.UserRegisterDto;
import id.binaracademy.secondhand.entity.UserEntity;
import id.binaracademy.secondhand.entity.UserInfo;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserService {
    UserEntity saveUser(UserRegisterDto user);
    UserInfo findUserById(Long id);
    UserEntity findUserByUsername(String username);
    UserEntity findUserByEmail(String email);
    Page<UserInfo> findAllUsers(int page, int size, String sortBy, String sortType);
    UserInfo updateUser(Long id, UpdateUserInfoDto user);
    void deleteUser(Long id);
    UserInfo registerAsSeller(Long userId);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}


package id.binaracademy.secondhand.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterDto implements Serializable {
    private final String username;
    private final String email;
    private final String password;
}

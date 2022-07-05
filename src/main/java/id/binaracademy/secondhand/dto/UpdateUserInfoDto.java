package id.binaracademy.secondhand.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateUserInfoDto implements Serializable {
    private final String username;
    private final String email;
    private final String city;
    private final String address;
    private final String phoneNumber;
}

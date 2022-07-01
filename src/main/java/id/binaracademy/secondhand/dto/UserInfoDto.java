package id.binaracademy.secondhand.dto;

import id.binaracademy.secondhand.entity.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class UserInfoDto implements Serializable {
    private final Long id;
    private final String username;
    private final String email;
    private final Collection<Role> roles;
    private final String city;
    private final String address;
    private final String phoneNumber;
}

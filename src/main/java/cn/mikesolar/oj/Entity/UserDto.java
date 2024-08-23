package cn.mikesolar.oj.Entity;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String password;
    private String phoneNumber;

    public UserDto(String username, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}

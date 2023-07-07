package com.rob.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class UserLoginRequest {
    private String nick;
    private String password;
    private String account;
}

package com.simplewall.my_rating.model.request.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    String login;
    String password;
    String repeatPassword;
    String email;
    String phone;

}

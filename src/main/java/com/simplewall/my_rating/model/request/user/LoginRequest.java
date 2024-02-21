package com.simplewall.my_rating.model.request.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    String login;
    String password;

}

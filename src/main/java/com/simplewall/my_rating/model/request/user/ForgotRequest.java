package com.simplewall.my_rating.model.request.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ForgotRequest {

    String email;
    String password;
    String repeatPassword;

}

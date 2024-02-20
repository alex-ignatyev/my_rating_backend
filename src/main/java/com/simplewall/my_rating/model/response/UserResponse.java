package com.simplewall.my_rating.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    private long id;
    private String login;
    private String email;
    private String phone;
    private List<CategoryResponse> categories;
}

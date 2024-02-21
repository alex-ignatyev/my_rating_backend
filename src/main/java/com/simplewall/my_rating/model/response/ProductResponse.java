package com.simplewall.my_rating.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductResponse {

    private long id;
    private String name;
    private int rate;

}

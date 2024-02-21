package com.simplewall.my_rating.model.request.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddProductRequest {

    private long categoryId;
    private String name;
    private int rate;

}

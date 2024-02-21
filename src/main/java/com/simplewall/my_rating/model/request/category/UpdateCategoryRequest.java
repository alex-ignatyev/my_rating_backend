package com.simplewall.my_rating.model.request.category;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCategoryRequest {

    private long id;
    private String name;
    private int icon;

}
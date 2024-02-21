package com.simplewall.my_rating.model.request.category;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCategoryRequest {

    private String name;
    private int icon;

}

package com.simplewall.my_rating.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CategoryResponse {
    private long id;
    private String name;
    private List<ProductResponse> products;
}

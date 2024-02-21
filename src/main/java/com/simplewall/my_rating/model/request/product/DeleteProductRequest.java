package com.simplewall.my_rating.model.request.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteProductRequest {

    private long categoryId;
    private long productId;

}

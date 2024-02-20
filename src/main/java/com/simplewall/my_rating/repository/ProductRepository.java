package com.simplewall.my_rating.repository;

import com.simplewall.my_rating.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByCategoryId(long categoryId);
}

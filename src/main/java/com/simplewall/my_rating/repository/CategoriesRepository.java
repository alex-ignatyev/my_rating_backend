package com.simplewall.my_rating.repository;

import com.simplewall.my_rating.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserLogin(String login);
}

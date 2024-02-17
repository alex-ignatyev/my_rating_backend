package com.simplewall.my_rating.repo;

import com.simplewall.my_rating.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByNameOrLogin(String name, String login);
    User findByLogin(String login);
}

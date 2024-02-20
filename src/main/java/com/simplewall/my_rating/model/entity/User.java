package com.simplewall.my_rating.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = User.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone", unique = true)
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

    public User(String login, String password, String email, String phone) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public void addCategory(Category category) {
        category.setUser(this);
        categories.add(category);
    }

    public Boolean updateCategory(Long categoryId, String newName) {
        Category category = categories.stream()
                .filter(c -> c.getId() == categoryId)
                .findFirst()
                .orElse(null);
        if (category != null) {
            category.setName(newName);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeCategory(Long categoryId) {
        return categories.removeIf(category -> category.getId() == categoryId);
    }
}

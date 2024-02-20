package com.simplewall.my_rating.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope= Category.class)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        product.setCategory(this);
        products.add(product);
    }

    public Boolean updateProduct(Long productId, String newName) {
        Product product = products.stream()
                .filter(c -> c.getId() == productId)
                .findFirst()
                .orElse(null);
        if (product != null) {
            product.setName(newName);
            return true;
        } else {
            return false;
        }
    }

    public void removeProduct(Long productId) {
        products.removeIf(c -> c.getId() == productId);
    }
}

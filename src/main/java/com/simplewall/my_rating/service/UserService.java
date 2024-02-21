package com.simplewall.my_rating.service;

import com.simplewall.my_rating.model.entity.Category;
import com.simplewall.my_rating.model.entity.Product;
import com.simplewall.my_rating.model.entity.User;
import com.simplewall.my_rating.model.exception.RestException;
import com.simplewall.my_rating.model.request.user.ForgotRequest;
import com.simplewall.my_rating.model.request.user.LoginRequest;
import com.simplewall.my_rating.model.request.user.RegisterRequest;
import com.simplewall.my_rating.model.response.CategoryResponse;
import com.simplewall.my_rating.model.response.ProductResponse;
import com.simplewall.my_rating.model.response.UserResponse;
import com.simplewall.my_rating.repository.CategoryRepository;
import com.simplewall.my_rating.repository.ProductRepository;
import com.simplewall.my_rating.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productsRepository;

    @Transactional
    public ResponseEntity<UserResponse> getUserInfo(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));

        List<CategoryResponse> categories = categoryRepository.findByUserLogin(user.getLogin())
                .stream()
                .map(category -> {
                    List<Product> products = productsRepository.findProductsByCategoryId(category.getId());
                    List<ProductResponse> productResponses = products.stream().map(this::mapProduct).toList();
                    return mapCategory(category, productResponses);
                }).collect(Collectors.toList());

        return ResponseEntity.ok(new UserResponse(user.getId(), user.getLogin(), user.getEmail(), user.getPhone(), categories));
    }

    @Transactional
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        User user = userRepository.findByLogin(loginRequest.getLogin())
                .orElseThrow(() -> new RestException("User not exist", HttpStatus.CONFLICT));

        // Проверка длинны логина < 4
        if (loginRequest.getLogin().length() < 4) {
            throw new RestException("Login length should be at least 4 characters", HttpStatus.BAD_REQUEST);
        }

        // Проверка длинны логина > 12
        if (loginRequest.getLogin().length() > 12) {
            throw new RestException("Login length should be at less 12 characters", HttpStatus.BAD_REQUEST);
        }

        // Проверка длинны пароля < 4
        if (loginRequest.getPassword().length() < 4) {
            throw new RestException("Password length should be at least 4 characters", HttpStatus.BAD_REQUEST);
        }

        // Проверка длинны пароля > 16
        if (loginRequest.getPassword().length() > 16) {
            throw new RestException("Password length should be at less 16 characters", HttpStatus.BAD_REQUEST);
        }

        // Проверка совпадения паролей
        if (!Objects.equals(user.getPassword(), loginRequest.getPassword())) {
            throw new RestException("Incorrect password", HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok("Success login");
    }

    @Transactional
    public ResponseEntity<?> register(RegisterRequest register) {

        // Проверка уникальности логина
        userRepository.findByLogin(register.getLogin()).ifPresent(u -> {
            throw new RestException("Login is already used", HttpStatus.CONFLICT);
        });

        // Проверка уникальности почты
        userRepository.findByEmail(register.getEmail()).ifPresent(u -> {
            throw new RestException("Email is already in used", HttpStatus.CONFLICT);
        });

        // Проверка уникальности телефона
        userRepository.findByPhone(register.getPhone()).ifPresent(u -> {
            throw new RestException("Phone is already in used", HttpStatus.CONFLICT);
        });

        // Проверка формата почты и длинна <= 20
        if (!isValidEmail(register.getEmail())) {
            throw new RestException("Invalid email format", HttpStatus.BAD_REQUEST);
        }

        // Проверка телефона на соответствие паттерну
        if (!register.getPhone().matches("[0-9]+") || register.getPhone().length() != 11) {
            throw new RestException("Invalid phone number", HttpStatus.BAD_REQUEST);
        }

        // Проверка совпадения паролей
        if (!register.getPassword().equals(register.getRepeatPassword())) {
            throw new RestException("Passwords should match", HttpStatus.BAD_REQUEST);
        }

        // Создание нового пользователя и сохранение в базе данных
        User newUser = userRepository.save(new User(register.getLogin(), register.getPassword(), register.getEmail(), register.getPhone()));
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/forgot")
    public ResponseEntity<User> forgot(ForgotRequest forgot) {
        User user = userRepository.findByEmail(forgot.getEmail())
                .orElseThrow(() -> new RestException("Can't fin user", HttpStatus.CONFLICT));

        // Проверка длинны пароля < 4
        if (forgot.getPassword().length() < 4) {
            throw new RestException("Password length should be at least 4 characters", HttpStatus.BAD_REQUEST);
        }

        // Проверка длинны пароля > 16
        if (forgot.getPassword().length() > 16) {
            throw new RestException("Password length should be at less 16 characters", HttpStatus.BAD_REQUEST);
        }

        if (!forgot.getPassword().equals(forgot.getRepeatPassword())) {
            throw new RestException("Passwords should be the same", HttpStatus.CONFLICT);
        }

        user.setPassword(forgot.getPassword());

        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        boolean emailHasGoodFormat = email.matches(emailRegex);
        boolean emailHasGoodLength = email.length() <= 20;
        return emailHasGoodFormat && emailHasGoodLength;
    }

    private CategoryResponse mapCategory(Category category, List<ProductResponse> products) {
        return new CategoryResponse(category.getId(), category.getName(), products);
    }

    private ProductResponse mapProduct(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getRate());
    }
}

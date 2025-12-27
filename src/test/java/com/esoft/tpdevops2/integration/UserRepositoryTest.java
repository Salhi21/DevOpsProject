package com.esoft.tpdevops2.integration;

import com.esoft.tpdevops2.entities.User;
import com.esoft.tpdevops2.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Tests repository User")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // clean the table before each test
    }

    @Test
    @DisplayName("Test: save user")
    void testSaveUser() {
        User user = new User(null, "Ahmed", "Ali");
        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getFirstName()).isEqualTo("Ahmed");
        assertThat(savedUser.getLastName()).isEqualTo("Ali");
    }

    @Test
    @DisplayName("Test: find user by id")
    void testFindById() {
        User user = new User(null, "Ahmed", "Ali");
        User savedUser = userRepository.save(user);

        Optional<User> found = userRepository.findById(Math.toIntExact(savedUser.getId()));

        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Ahmed");
    }

    @Test
    @DisplayName("Test: find all users")
    void testFindAll() {
        userRepository.save(new User(null, "Ahmed", "Ali"));
        userRepository.save(new User(null, "Sara", "Khaled"));

        assertThat(userRepository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("Test: delete user")
    void testDeleteUser() {
        User user = new User(null, "Ahmed", "Ali");
        User savedUser = userRepository.save(user);

        userRepository.delete(savedUser);

        assertThat(userRepository.findById(Math.toIntExact(savedUser.getId()))).isEmpty();
    }
}

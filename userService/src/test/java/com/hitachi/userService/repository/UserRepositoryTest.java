package com.hitachi.userService.repository;

import com.hitachi.userService.entity.User;
import com.hitachi.userService.entity.User.UserQualification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
// This prevents Spring Boot from replacing the data source (remove this for h2 DB)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        // Initialize some test data
        User user1 = new User("Alice", "alice@example.com", 30, UserQualification.Doctor);
        User user2 = new User("Bob", "bob@example.com", 25, UserQualification.Engineer);
        userRepository.saveAll(List.of(user1, user2));
    }

    @Test
    void testFindById() {
        User user = userRepository.findAll().get(0); // Get the first user from the repository
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("Alice");
    }

    @Test
    void testSave() {
        User user = new User("Charlie", "charlie@example.com", 35, UserQualification.Pilot);
        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo(user.getName());
    }

    @Test
    void testFindAll() {
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
    }

    @Test
    void testDeleteById() {
        User user = userRepository.findAll().get(0); // Get the first user from the repository
        userRepository.deleteById(user.getId());
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
    }
}

package ru.yandex.practicum.filmorate.util;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDBStorage;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/schema.sql"),
        @Sql(scripts = {"/data.sql", "/test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class UserDBStorageTest {

    UserDBStorage userStorage;

    @Test
    public void testFindUserById() {
        Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(1L));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1L)
                );
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("login", "dm1366"));
    }

    @Test
    public void testFindAllUsers() {
        Optional<List<User>> allUsersOptional = Optional.ofNullable(userStorage.readAllUsers());

        assertThat(allUsersOptional)
                .isPresent()
                .hasValueSatisfying(users ->
                        assertThat(users.size()).isEqualTo(4)

                );
    }

    @Test
    public void updateUserTest() {
        Optional<User> userOptional = Optional.ofNullable(userStorage.readAllUsers().get(0));
        assertThat(userOptional.get().getId()).isEqualTo(1);
        assertThat(userOptional.get().getName()).isEqualTo("Dmitriy");

        userOptional.get().setName("Sergio");
        userStorage.updateUser(userOptional.get());

        Optional<User> userWasUpdate = Optional.ofNullable(userStorage.readAllUsers().get(0));
        assertThat(userWasUpdate).isPresent()
                .hasValueSatisfying(user -> assertThat(user).hasFieldOrPropertyWithValue("id", 1L));
        assertThat(userWasUpdate).isPresent()
                .hasValueSatisfying(user -> assertThat(user).hasFieldOrPropertyWithValue("name", "Sergio"));
    }
}

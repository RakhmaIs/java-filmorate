package validatortest;


import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.validation.UserValidator.validateUser;

class UserValidatorTest {

    @Test
    public void shouldReturnTrueWhenUserIsValid() {
        User user = new User(1L, "login", "user1", "user1@mail.ru", LocalDate.of(1990, 1, 21));
        assertTrue(validateUser(user));
    }

    @Test
    public void shouldReturnFalseWhenEmailIsBlank() {
        User user = new User(1L, "login", "user1", " ", LocalDate.of(1990, 1, 21));
        assertFalse(validateUser(user));
    }

    @Test
    public void shouldReturnFalseIfEmailNotContainsDogSymbol() {
        User user = new User(1L, "login", "user1", "user1mail.ru", LocalDate.of(1990, 1, 21));
        assertFalse(validateUser(user));
    }

    @Test
    public void shouldReturnFalseIfBirthdayInFuture() {
        User user = new User(1L, "login", "user1", "user1mail.ru", LocalDate.of(2025, 1, 21));
        assertFalse(validateUser(user));
    }

    @Test
    public void shouldReturnFalseWhenLoginIsBlank() {
        User user = new User(1L, " ", "user1", "user1mail.ru", LocalDate.of(1990, 1, 21));
        assertFalse(validateUser(user));
    }

    @Test
    public void shouldReturnFalseWhenLoginContainsSpace() {
        User user = new User(1L, "login login ", "user1", "user1mail.ru", LocalDate.of(1990, 1, 21));
        assertFalse(validateUser(user));
    }

    @Test
    public void shouldReturnFalseIfNameEqualsNull() {
        User user = new User(1L, "login login ", null, "user1mail.ru", LocalDate.of(1990, 1, 21));
        assertFalse(validateUser(user));
    }

    @Test
    public void shouldReturnFalseIfNameIsBlank() {
        User user = new User(1L, "login login ", " ", "user1mail.ru", LocalDate.of(1990, 1, 21));
        assertFalse(validateUser(user));
    }
}
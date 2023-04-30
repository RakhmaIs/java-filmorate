package validatortest;


import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.UserValidator;


import java.time.LocalDate;
import java.util.HashSet;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class UserValidatorTest {


    @Test
    public void doesNotThrowValidationExcWhenUserIsValid() {
        User validUser = new User(1L, "login", "user1", "user1@mail.ru", LocalDate.of(1990, 1, 21), new HashSet<>());
        assertDoesNotThrow(() -> UserValidator.validateUser(validUser));
    }

    @Test
    public void shouldThrowValidationExceptionWhenEmailIsBlank() {
        User blankMail = new User(1L, "login", "user1", " ", LocalDate.of(1990, 1, 21), new HashSet<>());
        ValidationException ex = assertThrows(ValidationException.class, () -> UserValidator.validateUser(blankMail));
        assertEquals(HttpStatus.valueOf(500) + " " + "\"" + "Почта не может быть пустой и должна содержать символ '@'." + "\"", ex.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionWhenEmailNotContainsDogSymbol() {
        User invalidMail = new User(1L, "login", "user1", "user1mail.ru", LocalDate.of(1990, 1, 21), new HashSet<>());
        ValidationException ex = assertThrows(ValidationException.class, () -> UserValidator.validateUser(invalidMail));
        assertEquals(HttpStatus.valueOf(500) + " " + "\"" + "Почта не может быть пустой и должна содержать символ '@'." + "\"", ex.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionIfBirthdayInFuture() {
        User invalidBirthday = new User(1L, "login", "user1", "user1@mail.ru", LocalDate.of(2025, 1, 21), new HashSet<>());
        ValidationException ex = assertThrows(ValidationException.class, () -> UserValidator.validateUser(invalidBirthday));
        assertEquals(HttpStatus.valueOf(500) + " " + "\"" + "День рождение не может быть позже настоящего времени." + "\"", ex.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenLoginIsBlank() {
        User invalidLogin = new User(1L, " ", "user1", "user1@mail.ru", LocalDate.of(1990, 1, 21), new HashSet<>());
        ValidationException ex = assertThrows(ValidationException.class, () -> UserValidator.validateUser(invalidLogin));
        assertEquals(HttpStatus.valueOf(500) + " " + "\"" + "Логин не может быть пустым и логин не может сожержать пробелы." + "\"", ex.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenLoginContainsSpace() {
        User invalidLogin = new User(1L, "login login ", "user1", "user1@mail.ru", LocalDate.of(1990, 1, 21), new HashSet<>());
        ValidationException ex = assertThrows(ValidationException.class, () -> UserValidator.validateUser(invalidLogin));
        assertEquals(HttpStatus.valueOf(500) + " " + "\"" + "Логин не может быть пустым и логин не может сожержать пробелы." + "\"", ex.getMessage());
    }

    @Test
    public void doesNotThrowValidationExceptionWhenNameEqualsNull() {
        User noName = new User(1L, "login", null, "user1@mail.ru", LocalDate.of(1990, 1, 21), new HashSet<>());
        assertDoesNotThrow(() -> UserValidator.validateUser(noName));
    }

    @Test
    public void doesNotThrowValidationExceptionIfNameIsBlank() {
        User blankName = new User(1L, "login", " ", "user1@mail.ru", LocalDate.of(1990, 1, 21), new HashSet<>());
        assertDoesNotThrow(() -> UserValidator.validateUser(blankName));
    }
}
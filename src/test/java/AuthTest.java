import com.codeborne.selenide.Selenide;
import jdk.jfr.Registered;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredUser() {
        var RegisteredUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id='login'] input").setValue("vasya");
        $("[data-test-id='password'] input").setValue("password");
        $("button.button").click();
        $("h2").shouldHave(exactText("Личный кабинет")).shouldBe(visible);

    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(visible);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var RegisteredUser = DataGenerator.Registration.getUser("Blocked");
        $("[data-test-id='login'] input").setValue("vadim");
        $("[data-test-id='password'] input").setValue("password2");
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(text("Ошибка! Пользователь заблокирован"))
                .shouldBe(visible);
    }


}

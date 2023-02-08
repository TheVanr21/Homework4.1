package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {

    public String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldSendRequest(){
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");

        $("[data-test-id='date'] input").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);

        String date = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Рохлин Василий");
        $("[data-test-id='phone'] input").setValue("+79815553535");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Забронировать")).click();

        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15)).shouldBe(Condition.visible);

    }

    @Test
    public void shouldSendRequestHardWay(){
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Са");
        $x("//*[contains(@class, 'popup__container')]//*[contains(text(), 'Санкт-Петербург')]").click();

        $("[data-test-id='date'] button").click();

        int addDays = 7;
        String date = generateDate(addDays, "dd.MM.yyyy");

        if (!generateDate(3, "MM").equals(generateDate(addDays, "MM"))) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        $$(".calendar__day").find(Condition.exactText(generateDate(addDays, "d"))).click();

        $("[data-test-id='name'] input").setValue("Рохлин Василий");
        $("[data-test-id='phone'] input").setValue("+79815553535");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Забронировать")).click();

        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15)).shouldBe(Condition.visible);

    }
}

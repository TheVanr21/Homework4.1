package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {

    @Test
    public void shouldSendRequest(){
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");

        $("[data-test-id='date'] input").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 3);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = dateFormat.format(cal.getTime());

        $("[data-test-id='date'] input").setValue(strDate);

        $("[data-test-id='name'] input").setValue("Рохлин Василий");
        $("[data-test-id='phone'] input").setValue("+79815553535");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Забронировать")).click();

        $("[data-test-id='notification']").should(Condition.visible);

    }

    @Test
    public void shouldSendRequestHardWay(){
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Са");
        $x("//*[contains(@class, 'popup__container')]//*[contains(text(), 'Санкт-Петербург')]").click();

        $("[data-test-id='date'] button").click();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 7);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        String strDate = String.valueOf(cal.getTimeInMillis());

        if($("[data-day='" + strDate + "']").exists()){
            $("[data-day='" + strDate + "']").click();
        }else{
            $(".calendar__arrow_direction_right[data-step='1']").click();
            $("[data-day='" + strDate + "']").click();
        }

        $("[data-test-id='name'] input").setValue("Рохлин Василий");
        $("[data-test-id='phone'] input").setValue("+79815553535");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Забронировать")).click();

        $("[data-test-id='notification']").should(Condition.visible, Duration.ofSeconds(15));

    }
}

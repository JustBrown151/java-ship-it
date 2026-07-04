package ru.yandex.practicum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.parcel.PerishableParcel;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты метода isExpired")
public class IsExpiredTest {

    @Test
    @DisplayName("Посылка не испортилась")
    void testNotExpired() {
        PerishableParcel perishableParcel = new PerishableParcel("Молоко", 5,
                "Москва", 1, 5);
        assertFalse(perishableParcel.isExpired(3), "3 день - посылка не испортилась");
        assertFalse(perishableParcel.isExpired(6), "6 день - посылка не испортилась");
    }

    @Test
    @DisplayName("Посылка испортилась")
    void testExpired() {
        PerishableParcel perishableParcel = new PerishableParcel("Молоко", 5,
                "Москва", 1, 5);
        assertTrue(perishableParcel.isExpired(7), "7 день - посылка испортилась");
        assertTrue(perishableParcel.isExpired(30), "30 день - посылка испортилась");
    }

    @Test
    @DisplayName("Крайний день")
    void testNotExpiredLastDay() {
        PerishableParcel perishableParcel = new PerishableParcel("Молоко", 5,
                "Москва", 1, 5);
        assertFalse(perishableParcel.isExpired(6), "6 день - посылка не испортилась");
    }

    @Test
    @DisplayName("timeToLive = 0")
    void testNotExpiredZeroDay() {
        PerishableParcel perishableParcel = new PerishableParcel("Молоко", 5,
                "Москва", 1, 0);
        assertFalse(perishableParcel.isExpired(1), "1 день - посылка не испортилась");
        assertTrue(perishableParcel.isExpired(2), "2 день - посылка испортилась");
    }
}

package ru.yandex.practicum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.parcel.FragileParcel;
import ru.yandex.practicum.delivery.parcel.PerishableParcel;
import ru.yandex.practicum.delivery.parcel.StandardParcel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест расчета стоимости доставки")
public class DeliveryCostTest {

    @Test
    @DisplayName("Стандартная посылка")
    void testStandardParcelDeliveryCost() {
        StandardParcel standardParcel1 = new StandardParcel("Телефон", 5,
                "Москва", 1);
        assertEquals(10, standardParcel1.calculateDeliveryCost(), "Рассчет - 5 * 2 = 10");

        StandardParcel standardParcel2 = new StandardParcel("Гиря", 100,
                "Москва", 30);
        assertEquals(200, standardParcel2.calculateDeliveryCost(), "Рассчет - 100 * 2 = 200");
    }

    @Test
    @DisplayName("Хрупкая посылка")
    void testFragileParcelDeliveryCost() {
        FragileParcel fragileParcel1 = new FragileParcel("Стакан", 5,
                "Москва", 1);
        assertEquals(20, fragileParcel1.calculateDeliveryCost(), "Рассчет - 5 * 4 = 20");

        FragileParcel fragileParcel2 = new FragileParcel("Вино", 10,
                "Москва", 30);
        assertEquals(40, fragileParcel2.calculateDeliveryCost(), "Рассчет - 10 * 4 = 40");
    }

    @Test
    @DisplayName("Скоропортящаяся посылка")
    void testPerishableParcelDeliveryCost() {
        PerishableParcel perishableParcel1 = new PerishableParcel("Молоко", 5,
                "Москва", 1, 5);
        assertEquals(15, perishableParcel1.calculateDeliveryCost(), "Рассчет - 5 * 3 = 15");

        PerishableParcel perishableParcel2 = new PerishableParcel("Колбаса", 10,
                "Москва", 30, 5);
        assertEquals(30, perishableParcel2.calculateDeliveryCost(), "Рассчет - 10 * 3 = 30");
    }
}

package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.box.ParcelBox;
import ru.yandex.practicum.delivery.parcel.StandardParcel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тестирование добавления посылки в коробку")
public class AddParcelToBoxTest {
    private ParcelBox<StandardParcel> standardBox;

    @BeforeEach
    void init() {
        standardBox = new ParcelBox<>(50, "Стандартная");
    }

    @Test
    @DisplayName("Добавление посылки в коробку не превышая лимит веса")
    void testAddParcelWithinWeightLimit() {
        StandardParcel parcel1 = new StandardParcel("Книги", 10, "Москва", 1);
        StandardParcel parcel2 = new StandardParcel("Одежда", 15, "СПб", 2);

        standardBox.addParcel(parcel1);
        standardBox.addParcel(parcel2);

        assertEquals(25, standardBox.getCurrentWeight(), "Вес - 10 + 15 = 25");
        assertEquals(2, standardBox.getParcelsCount(), "Количество посылок - 1 + 1 = 2");
    }

    @Test
    @DisplayName("Добавление посылки превышает лимит веса")
    void testAddParcelExceedsWeightLimit() {
        StandardParcel parcel1 = new StandardParcel("Тяжёлый груз", 45, "Казань", 1);
        StandardParcel parcel2 = new StandardParcel("Ещё груз", 10, "Москва", 2);

        standardBox.addParcel(parcel1);
        standardBox.addParcel(parcel2);

        assertEquals(1, standardBox.getParcelsCount(),
                "В коробке должна быть только 1 посылка");
        assertEquals(45, standardBox.getCurrentWeight(),
                "Вес должен быть 45 кг (вторая посылка не добавлена)");
    }

    @Test
    @DisplayName("Граничный случай: добавление посылки ровно по лимиту веса")
    void testAddParcelExactlyAtWeightLimit() {
        StandardParcel parcel1 = new StandardParcel("Груз 1", 30, "Москва", 1);
        StandardParcel parcel2 = new StandardParcel("Груз 2", 20, "СПб", 2);

        standardBox.addParcel(parcel1);
        standardBox.addParcel(parcel2);

        assertEquals(2, standardBox.getParcelsCount());
        assertEquals(50, standardBox.getCurrentWeight(), "Вес должен быть ровно 50 кг");
    }
}

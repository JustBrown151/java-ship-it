package ru.yandex.practicum.delivery.box;

import ru.yandex.practicum.delivery.parcel.Parcel;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private final int maxWeight;
    private int currentWeight;
    private final List<T> parcels;
    private final String type;


    public ParcelBox(int maxWeight, String type) {
        this.maxWeight = maxWeight;
        this.type = type;
        this.currentWeight = 0;
        this.parcels = new ArrayList<>();
    }

    public void addParcel(T parcel) {
        int newWeight = currentWeight + parcel.getWeight();
        if (newWeight > maxWeight) {
            System.out.println("При упаковке посылки будет превышен максимальный вес коробки!");
            return;
        }
        parcels.add(parcel);
        currentWeight = newWeight;
        System.out.println("Посылка успешно упакована в коробку!");
    }

    public List<T> getAllParcels() {
        return parcels;
    }

    public boolean isBoxEmpty() {
        return parcels.isEmpty();
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public int getParcelsCount() {
        return parcels.size();
    }

    @Override
    public String toString() {
        return type + "\nМаксимальный вес: " + maxWeight + "\nТекущий вес: " + currentWeight
                + "\nКоличество посылок в коробке: " + parcels.size();
    }
}

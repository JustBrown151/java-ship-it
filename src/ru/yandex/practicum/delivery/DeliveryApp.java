package ru.yandex.practicum.delivery;

import ru.yandex.practicum.delivery.box.ParcelBox;
import ru.yandex.practicum.delivery.parcel.FragileParcel;
import ru.yandex.practicum.delivery.parcel.Parcel;
import ru.yandex.practicum.delivery.parcel.PerishableParcel;
import ru.yandex.practicum.delivery.parcel.StandardParcel;
import ru.yandex.practicum.delivery.parcel.interfaces.Trackable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Parcel> allParcels = new ArrayList<>();
    private static final List<Trackable> trackableParcels = new ArrayList<>();
    private static final ParcelBox<StandardParcel> standardBox = new ParcelBox<>(500, "Стандартная");
    private static final ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(300, "Хрупкая");
    private static final ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(400,
            "Скоропортящаяся");

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    reportStatus();
                    break;
                case 5:
                    showBoxContent();
                    break;
                case 0:
                    System.out.println("Программа закрыта.");
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Изменить местоположение отслеживаемых посылок");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        // Подсказка: спросите тип посылки и необходимые поля, создайте объект и добавьте в allParcels
        System.out.println("Введите краткое описание посылки: ");
        String description = scanner.nextLine();
        System.out.println("Введите вес посылки (целое число): ");
        int weight = Integer.parseInt(scanner.nextLine());
        if (weight < 1) {
            System.out.println("Вес посылки не может быть отрицательным или равен 0!");
            return;
        }
        System.out.println("Введите адрес доставки: ");
        String deliveryAddress = scanner.nextLine();
        System.out.println("Введите день отправления (от 1 до 30 включительно): ");
        int day = Integer.parseInt(scanner.nextLine());
        if (day < 1 || day > 30) {
            System.out.println("Введен некорректный день месяца!");
            return;
        }
        System.out.println("Введите тип посылки (1-Стандартная, 2-Хрупкая, 3-Скоропортящаяся): ");
        int type = Integer.parseInt(scanner.nextLine());
        switch (type) {
            case 1:
                StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, day);
                allParcels.add(standardParcel);
                standardBox.addParcel(standardParcel);
                break;
            case 2:
                FragileParcel fragileParcel = new FragileParcel(description, weight, deliveryAddress, day);
                fragileBox.addParcel(fragileParcel);
                allParcels.add(fragileParcel);
                trackableParcels.add(fragileParcel);
                break;
            case 3:
                System.out.println("Введите срок годности посылки (в днях): ");
                int timeToLive = Integer.parseInt(scanner.nextLine());
                if (timeToLive < 1) {
                    System.out.println("Срок годности не может быть отрицательным!");
                    return;
                }
                PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, day, timeToLive);
                allParcels.add(perishableParcel);
                perishableBox.addParcel(perishableParcel);
                break;
            default:
                System.out.println("Неизвестный тип посылки!");
                return;
        }
        System.out.println("Посылка успешно добавлена!");
    }

    private static void sendParcels() {
        if (allParcels.isEmpty()) {
            System.out.println("Посылок не найдено");
            return;
        }
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
        System.out.println("Все посылки отправлены!");
    }

    private static void calculateCosts() {
        if (allParcels.isEmpty()) {
            System.out.println("Посылок не найдено");
            return;
        }
        int sum = 0;
        for (Parcel parcel : allParcels) {
            sum += parcel.calculateDeliveryCost();
        }
        System.out.println("Общая стоимость доставок: " + sum);
    }

    private static void reportStatus() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Отслеживаемых посылок не найдено");
            return;
        }
        for (Trackable trackable : trackableParcels) {
            System.out.println(trackable);
            System.out.println("Введите новое местоположение посылки: ");
            String newLocation = scanner.nextLine();
            trackable.reportStatus(newLocation);
        }
    }

    private static void showBoxContent() {
        System.out.println("Введите тип коробки (1-Для стандартных посылок, 2-Для хрупких посылок, " +
                "3-Для скоропортящихся посылок");
        int type = Integer.parseInt(scanner.nextLine());
        switch (type) {
            case 1:
                boxContent(standardBox);
                break;
            case 2:
                boxContent(fragileBox);
                break;
            case 3:
                boxContent(perishableBox);
                break;
            default:
                System.out.println("Неизвестный тип коробки");
        }
    }

    private static <T extends Parcel> void boxContent(ParcelBox<T> parcelBox) {
        if (parcelBox.isBoxEmpty()) {
            System.out.println("Коробка пуста");
            return;
        }
        System.out.println(parcelBox);
        System.out.println("Содержимое коробки:");
        for (Parcel parcel : parcelBox.getAllParcels()) {
            System.out.println(parcel);
        }
    }

}


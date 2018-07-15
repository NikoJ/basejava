package com.urise.webapp;

public class Deadlock {
    public static void main(String[] args) throws InterruptedException {
        final Car BMW = new Car("BMW");
        final Car LADA = new Car("LADA");

        for (int i = 0; i < 10; i++) {
            new Thread(() -> LADA.before(BMW)).start();
            new Thread(() -> BMW.before(LADA)).start();
        }
        System.out.println("Конец");

    }

    static class Car {
        private final String brand;

        public Car(String brand) {
            this.brand = brand;
        }

        public String getBrand() {
            return brand;
        }

        public synchronized void before(Car car) {
            System.out.println("before" + car.getBrand());
            car.after(this);
            System.out.println("________________");
        }

        public synchronized void after(Car car) {
            System.out.println("after" + car.getBrand());
        }

    }

}

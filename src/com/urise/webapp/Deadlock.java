package com.urise.webapp;

import java.util.UUID;

public class Deadlock {
    public static void main(String[] args) throws InterruptedException {
        final Friend John = new Friend("John", 1000);
        final Friend Mars = new Friend("Mars", 1000);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> transferMoney(John, Mars, 100)).start();
            new Thread(() -> transferMoney(Mars, John, 100)).start();
        }
    }

    public static void transferMoney(Friend fromFriend, Friend toFriend, int amount) throws RuntimeException {
//        UUID fromUuid = fromFriend.getUuid();
//        UUID toUuid = toFriend.getUuid();
//        if (fromUuid.compareTo(toUuid) < 0) {
            synchronized (fromFriend) {
                synchronized (toFriend) {
                    doTransfer(fromFriend, toFriend, amount);
                }
//            }
//        } else {
//            synchronized (toFriend) {
//                synchronized (fromFriend) {
//                    doTransfer(fromFriend, toFriend, amount);
//                }
//            }
        }
    }

    public static void doTransfer(Friend fromFriend, Friend toFriend, int amount) throws RuntimeException {
        if (fromFriend.getBalance() < amount) {
            throw new RuntimeException();
        } else {
            fromFriend.debit(amount);
            toFriend.credit(amount);
            System.out.println(fromFriend.getName() + " перевел " + toFriend.getName() + "y " + amount);
            System.out.println("---->Balance " + fromFriend.getName() + ": " + fromFriend.getBalance());
            System.out.println("---->Balance " + toFriend.getName() + ": " + toFriend.getBalance());
        }
    }

    static class Friend {
        private final UUID uuid = UUID.randomUUID();
        private final String name;
        private int balance;

        public Friend(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }

        public String getName() {
            return name;
        }

        public int getBalance() {
            return balance;
        }

        public UUID getUuid() {
            return uuid;
        }

        public void debit(int amount) {
            this.balance -= amount;
        }

        public void credit(int amount) {
            this.balance += amount;
        }

    }

}

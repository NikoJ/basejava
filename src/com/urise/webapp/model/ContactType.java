package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    SKYPE("Skype"),
    EMAIL("Почта"),
    GITHUB("Профиль на GitHub"),
    STACKOVERFLOW("Профиль на StackOverflow"),
    HEADHUNTER("Профиль на HeadHunter");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

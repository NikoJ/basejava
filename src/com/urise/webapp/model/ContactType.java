package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    SKYPE("Skype") {
        @Override
        public String toHtmlNotNull(String value) {
            return getTitle() + ": " + "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("Почта") {
        @Override
        public String toHtmlNotNull(String value) {
            return getTitle() + ": " + "<a href='email:" + value + "'>" + value + "</a>";
        }
    },
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

    public String toHtml(String value) {
        return (value == null) ? "" : toHtmlNotNull(value);
    }

    protected String toHtmlNotNull(String value) {
        return title + ": " + value;
    }
}

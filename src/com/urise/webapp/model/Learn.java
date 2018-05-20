package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Learn {

    private final Site title;
    private final List<LearnList> learnLists;

    public Learn(String title, String url, List<LearnList> learnLists) {
        Objects.requireNonNull(title, "Title must not be null");
        this.title = new Site(title, url);
        this.learnLists = learnLists;
    }

    public Learn(String title, String url, LearnList... learnLists) {
        Objects.requireNonNull(title, "Title must not be null");
        this.title = new Site(title, url);
        this.learnLists = Arrays.asList(learnLists);
    }

    @Override
    public String toString() {
        return "Learn{" +
                "title=" + title +
                ", learnLists=" + learnLists +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Learn learn = (Learn) o;

        if (!title.equals(learn.title)) return false;
        return learnLists.equals(learn.learnLists);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + learnLists.hashCode();
        return result;
    }
}

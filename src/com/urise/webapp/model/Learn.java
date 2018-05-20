package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Learn {

    private final Site title;
    private final List<ListLearn> listLearns;

    public Learn(String title, String url, List<ListLearn> listLearns) {
        Objects.requireNonNull(title, "Title must not be null");
        this.title = new Site(title, url);
        this.listLearns = listLearns;
    }

    public Learn(String title, String url, ListLearn... listLearns) {
        Objects.requireNonNull(title, "Title must not be null");
        this.title = new Site(title, url);
        this.listLearns = Arrays.asList(listLearns);
    }

    @Override
    public String toString() {
        return "Learn{" +
                "title=" + title +
                ", listLearns=" + listLearns +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Learn learn = (Learn) o;

        if (!title.equals(learn.title)) return false;
        return listLearns.equals(learn.listLearns);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + listLearns.hashCode();
        return result;
    }
}

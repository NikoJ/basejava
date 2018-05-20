package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;

public class ListSection extends Section {
    private final List<String> list;

    public ListSection(List<String> list) {
        this.list = list;
    }

    public ListSection(String... arg) {
        this.list = Arrays.asList(arg);
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return list != null ? list.equals(that.list) : that.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }
}

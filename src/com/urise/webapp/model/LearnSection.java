package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;

public class LearnSection extends Section {
    private final List<Learn> learns;

    public LearnSection(List<Learn> learns) {
        this.learns = learns;
    }

    public LearnSection(Learn... learns) {
        this.learns = Arrays.asList(learns);
    }

    public List<Learn> getLearns() {
        return learns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LearnSection that = (LearnSection) o;

        return learns != null ? learns.equals(that.learns) : that.learns == null;
    }

    @Override
    public int hashCode() {
        return learns != null ? learns.hashCode() : 0;
    }
}

package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Company {

    private final Site title;
    private final List<Role> roles;

    public Company(String title, String url, List<Role> roles) {
        Objects.requireNonNull(title, "Title must not be null");
        this.title = new Site(title, url);
        this.roles = roles;
    }

    public Company(String title, String url, Role... roles) {
        Objects.requireNonNull(title, "Title must not be null");
        this.title = new Site(title, url);
        this.roles = Arrays.asList(roles);
    }

    @Override
    public String toString() {
        return "Company{" +
                "title=" + title +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!title.equals(company.title)) return false;
        return roles.equals(company.roles);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + roles.hashCode();
        return result;
    }
}

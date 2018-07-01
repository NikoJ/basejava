package com.urise.webapp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Company implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Site title;
    private final List<Role> roles;

    public Company(String title, String url, Role... roles) {
        this(new Site(title, url), Arrays.asList(roles));
    }

    public Company(Site title, List<Role> roles) {
        Objects.requireNonNull(title, "Title must not be null");
        this.title = title;
        this.roles = roles;
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

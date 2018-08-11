package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    public static final Company EMPTY = new Company("", "", Role.EMPTY);
    private static final long serialVersionUID = 1L;
    private Site sitePage;
    private List<Role> roles;

    public Company(String title, String url, Role... roles) {
        this(new Site(title, url), Arrays.asList(roles));
    }

    public Company(Site sitePage, List<Role> roles) {
        Objects.requireNonNull(sitePage, "Title must not be null");
        this.sitePage = sitePage;
        this.roles = roles;
    }

    public Company() {
    }

    public Site getSitePage() {
        return sitePage;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "Company{" +
                "title=" + sitePage +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!sitePage.equals(company.sitePage)) return false;
        return roles.equals(company.roles);
    }

    @Override
    public int hashCode() {
        int result = sitePage.hashCode();
        result = 31 * result + roles.hashCode();
        return result;
    }
}

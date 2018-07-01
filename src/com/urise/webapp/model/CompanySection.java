package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;

public class CompanySection extends Section {
    private static final long serialVersionUID = 1L;

    private final List<Company> companies;

    public CompanySection(List<Company> companies) {
        this.companies = companies;
    }

    public CompanySection(Company... companies) {
        this.companies = Arrays.asList(companies);
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return companies != null ? companies.equals(that.companies) : that.companies == null;
    }

    @Override
    public int hashCode() {
        return companies != null ? companies.hashCode() : 0;
    }
}

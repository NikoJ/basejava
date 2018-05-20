package com.urise.webapp.model;

import com.urise.webapp.common.DateUtil;

import java.util.Date;
import java.util.Objects;

public class ListLearn {
    private final Date dateStart;
    private final Date dateEnd;
    private final String name;
    private final String description;

    public ListLearn(String dateStart, String dateEnd, String name, String description) {
        Objects.requireNonNull(dateStart, "DateStart must not be null");
        Objects.requireNonNull(name, "Name must not be null");
        this.dateStart = DateUtil.valueOf(dateStart);
        this.dateEnd = DateUtil.valueOf(dateEnd);
        this.name = name;
        this.description = description;
    }

    public ListLearn(String dateStart, String dateEnd, String name) {
        Objects.requireNonNull(dateStart, "DateStart must not be null");
        Objects.requireNonNull(name, "Name must not be null");
        this.dateStart = DateUtil.valueOf(dateStart);
        this.dateEnd = DateUtil.valueOf(dateEnd);
        this.name = name;
        this.description = null;
    }

    public String getDateStart() {
        return dateStart.toString();
    }

    public String getDateEnd() {
        return dateEnd.toString();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListLearn learnSet = (ListLearn) o;

        if (!dateStart.equals(learnSet.dateStart)) return false;
        if (dateEnd != null ? !dateEnd.equals(learnSet.dateEnd) : learnSet.dateEnd != null) return false;
        if (!name.equals(learnSet.name)) return false;
        return description != null ? description.equals(learnSet.description) : learnSet.description == null;
    }

    @Override
    public int hashCode() {
        int result = dateStart.hashCode();
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
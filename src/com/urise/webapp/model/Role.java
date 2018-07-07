package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateStart;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateEnd;
    private String name;
    private String description;

    public Role(int yearStart, Month monthStart, int yearEnd, Month monthEnd, String name, String description) {
        this(DateUtil.of(yearStart, monthStart), DateUtil.of(yearEnd, monthEnd), name, description);
    }

    public Role(int yearStart, Month monthStart, int yearEnd, Month monthEnd, String name) {
        this(DateUtil.of(yearStart, monthStart), DateUtil.of(yearEnd, monthEnd), name);
    }

    public Role(LocalDate dateStart, LocalDate dateEnd, String name) {
        this(dateStart, dateEnd, name, null);
    }

    public Role(LocalDate dateStart, LocalDate dateEnd, String name, String description) {
        Objects.requireNonNull(dateStart, "DateStart must not be null");
        Objects.requireNonNull(dateEnd, "DateEnd must not be null");
        Objects.requireNonNull(name, "Name must not be null");
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.name = name;
        this.description = description;
    }

    public Role() {
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

        Role learnSet = (Role) o;

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

package com.venkonenterprises.humanmanager.domain;

import java.io.Serializable;

public class Employee implements Serializable {

    private final String id;
    private final String name;
    private final String lastName;
    private final String cellphone;
    private final String address;
    private final String referenceName;
    private final String referenceCellphone;
    private final String date;
    private final String country;
    private final String folio;
    private final String ssn;
    private final String uprc;
    private final String ftr;
    private final Float dailySalary;

    public Employee(String id, String name, String lastName, String cellphone, String address, String referenceName, String referenceCellphone, String date, String country, String folio, String ssn, String uprc, String ftr, Float dailySalary) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.cellphone = cellphone;
        this.address = address;
        this.referenceName = referenceName;
        this.referenceCellphone = referenceCellphone;
        this.date = date;
        this.country = country;
        this.folio = folio;
        this.ssn = ssn;
        this.uprc = uprc;
        this.ftr = ftr;
        this.dailySalary = dailySalary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getAddress() {
        return address;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public String getReferenceCellphone() {
        return referenceCellphone;
    }

    public String getDate() {
        return date;
    }

    public String getCountry() {
        return country;
    }

    public String getFolio() {
        return folio;
    }

    public String getSsn() {
        return ssn;
    }

    public String getUprc() {
        return uprc;
    }

    public String getFtr() {
        return ftr;
    }

    public Float getDailySalary() {
        return dailySalary;
    }

}

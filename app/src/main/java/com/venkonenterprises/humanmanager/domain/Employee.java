package com.venkonenterprises.humanmanager.domain;

import java.io.Serializable;

public class Employee implements Serializable {

    private String name;
    private String lastName;
    private String cellphone;
    private String address;
    private String referenceName;
    private String referenceCellphone;
    private String date;
    private String country;
    private String folio;
    private String ssn;
    private String uprc;
    private String ftr;
    private Float dailySalary;

    public Employee(String name, String lastName, String cellphone, String address, String referenceName, String referenceCellphone, String date, String country, String folio, String nss, String uprc, String ftr, Float dailySalary) {
        this.name = name;
        this.lastName = lastName;
        this.cellphone = cellphone;
        this.address = address;
        this.referenceName = referenceName;
        this.referenceCellphone = referenceCellphone;
        this.date = date;
        this.country = country;
        this.folio = folio;
        this.ssn = nss;
        this.uprc = uprc;
        this.ftr = ftr;
        this.dailySalary = dailySalary;
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

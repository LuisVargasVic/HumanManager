package com.venkonenterprises.humanmanager.domain;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String lastName;
    private String cellphone;
    private String address;
    private String referenceName;
    private String referenceCellphone;
    private String date;
    private String country;
    private String folio;
    private String nss;
    private String curp;
    private String rfc;
    private Float dailySalary;

    public User(String name, String lastName, String cellphone, String address, String referenceName, String referenceCellphone, String date, String country, String folio, String nss, String curp, String rfc, Float dailySalary) {
        this.name = name;
        this.lastName = lastName;
        this.cellphone = cellphone;
        this.address = address;
        this.referenceName = referenceName;
        this.referenceCellphone = referenceCellphone;
        this.date = date;
        this.country = country;
        this.folio = folio;
        this.nss = nss;
        this.curp = curp;
        this.rfc = rfc;
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

    public String getNss() {
        return nss;
    }

    public String getCurp() {
        return curp;
    }

    public String getRfc() {
        return rfc;
    }

    public Float getDailySalary() {
        return dailySalary;
    }
}

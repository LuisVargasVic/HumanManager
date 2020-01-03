package com.venkonenterprises.humanmanager.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DatabaseEmployee {

    @NonNull
    @PrimaryKey
    private String uid;
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

    public DatabaseEmployee(@NonNull String uid, String name, String lastName, String cellphone, String address, String referenceName, String referenceCellphone, String date, String country, String folio, String ssn, String uprc, String ftr, Float dailySalary) {
        this.uid = uid;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public String getReferenceCellphone() {
        return referenceCellphone;
    }

    public void setReferenceCellphone(String referenceCellphone) {
        this.referenceCellphone = referenceCellphone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getUprc() {
        return uprc;
    }

    public void setUprc(String uprc) {
        this.uprc = uprc;
    }

    public String getFtr() {
        return ftr;
    }

    public void setFtr(String ftr) {
        this.ftr = ftr;
    }

    public Float getDailySalary() {
        return dailySalary;
    }

    public void setDailySalary(Float dailySalary) {
        this.dailySalary = dailySalary;
    }
}

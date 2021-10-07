package com.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class Company {
    private Integer id;
    private String name;
    private String address;
    private String phoneNumber;
    private String inn;
    private LocalDate founded;
    private ArrayList<Security> securities;

    @Override
    public String toString() {
        return "«" + name + "»" +
                " - «Дата основания " +
                DateTimeFormatter
                        .ofLocalizedDate(FormatStyle.SHORT)
                        .format(founded)
                + "»";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public LocalDate getFounded() {
        return founded;
    }

    public void setFounded(LocalDate founded) {
        this.founded = founded;
    }

    public ArrayList<Security> getSecurities() {
        return securities;
    }

    public void setSecurities(ArrayList<Security> securities) {
        this.securities = securities;
    }
}

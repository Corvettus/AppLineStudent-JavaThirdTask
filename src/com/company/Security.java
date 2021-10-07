package com.company;

import java.time.LocalDate;
import java.util.HashSet;

public class Security {
    private String name;
    private HashSet<Currency> currencies;
    private String code;
    private LocalDate date;

    @Override
    public String toString() {
        return code + " - " + date + " - " + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(HashSet<Currency> currencies) {
        this.currencies = currencies;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

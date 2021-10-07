package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class Main {
    private static String line = null;
    private static BufferedReader reader = null;

    private static final BinaryOperator<String> delAllUpTo =
            (line, phrase) -> line.replaceFirst(".*" + phrase, "");
    private static final BinaryOperator<String> delAllFrom =
            (line, phrase) -> line.replaceFirst(phrase + ".*", "");

    private static final Function<String, Integer> getIntValue =
            line -> Integer.parseInt(delAllFrom.apply(line, ","));
    private static final Function<String, String> getStringValue =
            line -> delAllFrom.apply(line, "\"");
    private static final Function<String, LocalDate> getDateValue =
            line -> LocalDate.of(
                    Integer.parseInt(
                            delAllFrom.apply(line, ".")),
                    Integer.parseInt(
                            delAllFrom.apply(
                                    delAllUpTo.apply(line, "."),
                                    ".")),
                    Integer.parseInt(
                            delAllFrom.apply(
                                    delAllUpTo.apply(
                                            delAllUpTo.apply(line, "."),
                                            "."),
                                    "\"")));

    private static void parseJSONValue(String line, Company company, String fieldForm) {
        System.out.println("parseJSONValue company");
        if (line.contains(fieldForm)) {
            line = delAllUpTo.apply(line, fieldForm);
            switch (fieldForm) {
                case "\"id\": " -> company.setId(getIntValue.apply(line));
                case "\"name\": \"" -> company.setName(getStringValue.apply(line));
                case "\"address\": \"" -> company.setAddress(getStringValue.apply(line));
                case "\"phoneNumber\": \"" -> company.setPhoneNumber(getStringValue.apply(line));
                case "\"inn\": \"" -> company.setInn(getStringValue.apply(line));
                case "\"founded\": \"" -> company.setFounded(getDateValue.apply(line));
                case "\"securities\":" -> {
                    ArrayList<Security> securities = new ArrayList<Security>();
                    try {
                        if (line.contains("{")) {
                            Security security = new Security();
                            do {
                                System.out.println("line=" + line);
                                parseJSONValue(line, security, "\"name\": ");
                                parseJSONValue(line, security, "\"code\": ");
                                parseJSONValue(line, security, "\"date\": ");
                            } while (!line.contains("}") && (line = reader.readLine()) != null);
                            securities.add(security);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                        company.setSecurities(securities);
                    }
                }
            }
    }
    private static void parseJSONValue(String line, Security security, String fieldForm) {
        System.out.println("parseJSONValue security");
        if (line.contains(fieldForm)) {
            line = delAllUpTo.apply(line, fieldForm);
            switch (fieldForm) {
                case "\"name\": \"" -> security.setName(getStringValue.apply(line));
                case "\"currency\":" -> {
                    HashSet<Currency> currencies = new HashSet<Currency>();
                    try {
                        do {
                            System.out.println("line=" + line);
                            if (line.contains("\"USD\""))
                                currencies.add(Currency.USD);
                            if (line.contains("\"EU\""))
                                currencies.add(Currency.EU);
                            if (line.contains("\"RUB\""))
                                currencies.add(Currency.RUB);
                        } while (!line.contains("\\]") && (line = reader.readLine()) != null);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    security.setCurrencies(currencies);
                }
                case "\"code\": \"" -> security.setCode(getStringValue.apply(line));
                case "\"date\": \"" -> security.setDate(getDateValue.apply(line));
            }
        }
    }

    private static ArrayList<Company> readCompaniesJson(String filePath) {
        ArrayList<Company> companies = new ArrayList<Company>();
        try {
            File inputFile = new File(filePath);
            reader = new BufferedReader(new FileReader(inputFile));
            while ((line = reader.readLine()) != null) {
                System.out.println("line=" + line);
                if (line.contains("\"companies\":"))
                    do {
                        System.out.println("line=" + line);
                        if (line.contains("{")) {
                            Company company = new Company();
                            do {
                                System.out.println("line=" + line);
                                parseJSONValue(line, company, "\"id\": ");
                                parseJSONValue(line, company, "\"name\": ");
                                parseJSONValue(line, company, "\"address\": ");
                                parseJSONValue(line, company, "\"phoneNumber\": ");
                                parseJSONValue(line, company, "\"inn\": ");
                                parseJSONValue(line, company, "\"founded\": ");
                                parseJSONValue(line, company, "\"securities\":");
                            } while (!line.contains("}") && (line = reader.readLine()) != null);
                            companies.add(company);
                        }
                    } while (!line.contains("\\]") && (line = reader.readLine()) != null);
            }
            reader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return companies;
    }

    private static void printCompany(Company company) {
        System.out.println("printCompany");
        System.out.println(company + "\n");
        for (Security security : company.getSecurities()) {
            System.out.println(security);
        }
    }

    public static void main(String[] args) {
        ArrayList<Company> companies = readCompaniesJson("resources/inputFile.json");
        for (Company company : companies) {
            printCompany(company);
        }
    }
}

package com.project.cs.electricalbillngsystem.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//Model class of house aka bill per user
public class HouseBill {
    public static final String PATH = "C:\\Users\\pgarg\\Desktop\\Java";
    private String accName;
    private String address;
    private int accId;
    private String city;
    private String state;
    private int postalCode;
    private ElectricityRates electricityRates;
    protected int idCounter = 1;

    //constructor

    public HouseBill(
            String name,
            String address,
            String city,
            String state,
            int postalCode,
            ElectricityRates electricityRates) throws IOException, ParseException {
        this.accName = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.electricityRates = electricityRates;


        //Setting id Counter "Its a bit messy but it'll do the job"
        File idCounterFile = new File(PATH + "\\Electricity-Billing-System\\data\\CrucialData.json");
        FileReader reader = new FileReader(idCounterFile);
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
        String string = (String) jsonObject.get("idCounter");
        idCounter = Integer.parseInt(string);
        accId = idCounter;
        idCounter++;
        jsonObject = new JSONObject();
        jsonObject.put("idCounter",String.valueOf(idCounter));
        FileWriter writer = new FileWriter(idCounterFile);
        writer.write(jsonObject.toJSONString());
        reader.close();
        writer.close();
    }

    //Another Constructor using this constructor only for updating operations
    public HouseBill(String accName, String address, int accId, String city, String state, int postalCode, ElectricityRates electricityRates) {
        this.accName = accName;
        this.address = address;
        this.accId = accId;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.electricityRates = electricityRates;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public ElectricityRates getElectricityRates() {
        return electricityRates;
    }

    public void setElectricityRates(ElectricityRates electricityRates) {
        this.electricityRates = electricityRates;
    }
}

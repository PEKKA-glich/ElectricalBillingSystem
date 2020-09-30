package com.project.cs.electricalbillngsystem.model;


//Rates according to states
public class ElectricityRates {
    private String state;
    private int rates;

    //single constructor to only create a valid ElectricityRates object
    public ElectricityRates(String state, int rates){
        this.state = state;
        this.rates = rates;
    }

    //getters and setters

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRates() {
        return rates;
    }

    public void setRates(int rates) {
        this.rates = rates;
    }
}

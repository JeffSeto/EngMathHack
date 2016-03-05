package com.example.jeffrey.engmathhack;

import java.util.ArrayList;

/**
 * Created by Ivy Zhoo Zhoo on 3/4/2016.
 */
public class User {
    private String name;
    private double amount;
    private String note;

    // only call me if you have a name
    public User(String name){
        this.name = name;
    }

    public User(String name, double amount, String note){
        this.name = name;
        this.amount = amount;
        this.note = note;
    }

    public String getName() {
        return this.name;
    }

    public double getAmount() {
        return this.amount;
    }

    public void changeAmount(double deltaAmount){
        this.amount += deltaAmount;
    }

    public String getNote() {
        return this.note;
    }
}

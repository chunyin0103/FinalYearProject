package com.example.fyp4;

public class Payment {
    private String des;
    private double total_amount;

    public Payment() {
    }

    public Payment(String des, double total_amount) {
        this.des = des;
        this.total_amount = total_amount;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
}

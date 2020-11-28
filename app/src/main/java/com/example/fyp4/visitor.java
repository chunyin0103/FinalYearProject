package com.example.fyp4;

import androidx.recyclerview.widget.RecyclerView;

public class visitor {
    private String name;
    private String gender;
    private String dob;
    private String visit_date;
    private String visit_time;
    String documentID;
    public visitor() {
    }

    public visitor(String name, String gender, String dob, String visit_date, String visit_time) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.visit_date = visit_date;
        this.visit_time = visit_time;
    }

    public visitor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(String visit_time) {
        this.visit_time = visit_time;
    }
}

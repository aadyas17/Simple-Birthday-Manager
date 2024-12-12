package com.example.simplebirthdaymanager;


public class Birthday {

    private String name;
    private String date;

    public Birthday(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}


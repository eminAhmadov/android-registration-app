package com.example.peakapp;

public class User {
    private String first_name;
    private String last_name;
    private String nickname;
    private int age;
    private String gender;
    private String country;
    private String address;

    public User(String first_name,
                String last_name,
                String nickname,
                int age,
                String gender,
                String country,
                String address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.country = country;
        this.address = address;
    }

}

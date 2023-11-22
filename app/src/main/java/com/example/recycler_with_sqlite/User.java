package com.example.recycler_with_sqlite;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String phone;
    private int id;

    public User() {

    }

    public User(int id, String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

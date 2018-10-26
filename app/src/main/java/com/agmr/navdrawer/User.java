package com.agmr.navdrawer;

public class User {
    String name, phone_number, address, email_address;
    float balance;

    public User() {

    }

    public User(String mName, String mPhone_number, String mAddress, String email_address, float mBalance) {
        this.name = mName;
        this.phone_number = mPhone_number;
        this.address = mAddress;
        this.email_address = email_address;
        this.balance = mBalance;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}

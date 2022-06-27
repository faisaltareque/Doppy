package com.skyrentyle.faisal.doppy.DonorClass;

public class Donor {
    String firstName;
    String lastName;
    String email;
    String password;
    String contact;
    String key;

    public Donor(){

    }

    public Donor(String firstName, String lastName, String email, String password, String contact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.contact = contact;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getContact() {
        return contact;
    }

    public String getKey() {
        return key;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

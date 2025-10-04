package org.example.lab3.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class BuddyInfo {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String phoneNumber;

    @ManyToOne
    @com.fasterxml.jackson.annotation.JsonBackReference
    private AddressBook addressBook;  // foreign key column

    public BuddyInfo() {}

    public BuddyInfo(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setAddressBook(AddressBook addressBook) { this.addressBook = addressBook; }

    @Override
    public String toString(){
        return "Name: " + this.name + ", Phone Number: " + this.phoneNumber;
    }
}

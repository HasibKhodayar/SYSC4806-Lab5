/* Hasib Khodayar - 101225523*/
package org.example.lab3.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private List<BuddyInfo> buddies = new ArrayList<>();

    public AddressBook() {}

    public Long getId() {
        return id;
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    public void addBuddy(BuddyInfo buddy) {
        buddies.add(buddy);
        buddy.setAddressBook(this);
    }

    public void removeBuddy(BuddyInfo buddy) {
        buddies.remove(buddy);
        buddy.setAddressBook(null);
    }

    public void addNewBuddy(String name, String phoneNumber) {
        BuddyInfo newBuddy = new BuddyInfo(name, phoneNumber);
        this.addBuddy(newBuddy);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("AddressBook %d:%n", id));
        int i = 1;
        for (BuddyInfo buddy : buddies) {
            sb.append(String.format("%d. %s%n", i++, buddy));
        }
        return sb.toString();
    }

}



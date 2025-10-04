package org.example.lab3;

import org.example.lab3.entities.AddressBook;
import org.example.lab3.entities.BuddyInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddressBookTest {

    private AddressBook addressBook;
    private BuddyInfo buddy1;
    private BuddyInfo buddy2;

    @BeforeEach
    void setUp() {
        addressBook = new AddressBook();
        buddy1 = new BuddyInfo("Hasib", "123-456-7890");
        buddy2 = new BuddyInfo("Hasib Khodayar", "987-654-3210");
    }

    @Test
    void testAddBuddy() {
        addressBook.addBuddy(buddy1);
        List<BuddyInfo> buddies = addressBook.getBuddies();

        assertEquals(1, buddies.size());
        assertTrue(buddies.contains(buddy1));
    }

    @Test
    void testRemoveBuddy() {
        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);

        addressBook.removeBuddy(buddy1);
        List<BuddyInfo> buddies = addressBook.getBuddies();

        assertEquals(1, buddies.size());
        assertFalse(buddies.contains(buddy1));
        assertTrue(buddies.contains(buddy2));
    }

    @Test
    void testAddNewBuddy() {
        addressBook.addNewBuddy("Hasib K", "555-000-1111");
        List<BuddyInfo> buddies = addressBook.getBuddies();

        assertEquals(1, buddies.size());
        assertEquals("Hasib K", buddies.get(0).getName());
        assertEquals("555-000-1111", buddies.get(0).getPhoneNumber());
    }

    @Test
    void testToStringWithMultipleBuddies() {
        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);

        String result = addressBook.toString();

        assertTrue(result.startsWith("AddressBook"));
        assertTrue(result.contains("1. Name: Hasib, Phone Number: 123-456-7890"));
        assertTrue(result.contains("2. Name: Hasib Khodayar, Phone Number: 987-654-3210"));
    }

    @Test
    void testToStringEmptyAddressBook() {
        String result = addressBook.toString();

        assertTrue(result.startsWith("AddressBook"));
        assertFalse(result.contains("1."));
    }

}

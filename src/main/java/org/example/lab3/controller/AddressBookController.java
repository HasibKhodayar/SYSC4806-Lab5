package org.example.lab3.controller;

import org.example.lab3.entities.AddressBook;
import org.example.lab3.entities.BuddyInfo;
import org.example.lab3.repository.AddressBookRepository;
import org.example.lab3.repository.BuddyInfoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/addressbooks")
public class AddressBookController {

    private final AddressBookRepository addressBookRepository;
    private final BuddyInfoRepository buddyInfoRepository;

    public AddressBookController(AddressBookRepository addressBookRepository, BuddyInfoRepository buddyInfoRepository) {
        this.addressBookRepository = addressBookRepository;
        this.buddyInfoRepository = buddyInfoRepository;
    }

    @PostMapping
    public AddressBook createAddressBook() {
        AddressBook ab = new AddressBook();
        return addressBookRepository.save(ab);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressBook> getAddressBook(@PathVariable Long id) {
        return addressBookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/buddies")
    public ResponseEntity<AddressBook> addBuddy(@PathVariable Long id, @RequestBody BuddyInfo buddyInfo) {
        Optional<AddressBook> abOpt = addressBookRepository.findById(id);
        if (abOpt.isEmpty()) return ResponseEntity.notFound().build();

        AddressBook ab = abOpt.get();
        ab.addBuddy(buddyInfo);
        addressBookRepository.save(ab);

        return ResponseEntity.ok(ab);
    }

    @DeleteMapping("/{id}/buddies/{buddyId}")
    public ResponseEntity<AddressBook> removeBuddy(@PathVariable Long id, @PathVariable Long buddyId) {
        Optional<AddressBook> abOpt = addressBookRepository.findById(id);
        Optional<BuddyInfo> buddyOpt = buddyInfoRepository.findById(buddyId);

        if (abOpt.isEmpty() || buddyOpt.isEmpty()) return ResponseEntity.notFound().build();

        AddressBook ab = abOpt.get();
        BuddyInfo buddy = buddyOpt.get();
        ab.removeBuddy(buddy);
        buddyInfoRepository.delete(buddy);
        addressBookRepository.save(ab);

        return ResponseEntity.ok(ab);
    }
}

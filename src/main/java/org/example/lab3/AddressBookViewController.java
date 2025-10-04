package org.example.lab3;

import org.example.lab3.entities.AddressBook;
import org.example.lab3.repository.AddressBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AddressBookViewController {

    private final AddressBookRepository addressBookRepository;

    public AddressBookViewController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @GetMapping("/addressbooks/{id}")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        AddressBook ab = addressBookRepository.findById(id).orElse(null);
        model.addAttribute("addressBook", ab);
        return "addressbook"; // thymeleaf template name
    }
}

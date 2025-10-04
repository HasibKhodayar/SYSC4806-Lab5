package org.example.lab3.repository;
import org.example.lab3.entities.AddressBook;
import org.springframework.data.repository.CrudRepository;

public interface AddressBookRepository extends CrudRepository<AddressBook, Long>{

}

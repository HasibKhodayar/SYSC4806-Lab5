package org.example.lab3;

import org.example.lab3.entities.AddressBook;
import org.example.lab3.entities.BuddyInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressBookIntegrationTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void testAddAndRetrieveBuddy() {
        // create a new AddressBook
        ResponseEntity<AddressBook> createResponse =
                restTemplate.postForEntity(url("/api/addressbooks"), null, AddressBook.class);
        AddressBook book = createResponse.getBody();
        assertThat(book).isNotNull();

        // add a buddy
        BuddyInfo buddy = new BuddyInfo();
        buddy.setName("Hasib");
        buddy.setPhoneNumber("123-456");
        buddy.setAddress("44 street");

        ResponseEntity<AddressBook> addResponse =
                restTemplate.postForEntity(url("/api/addressbooks/" + book.getId() + "/buddies"),
                        buddy, AddressBook.class);
        assertThat(addResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // get address book
        AddressBook retrieved = restTemplate.getForObject(
                url("/api/addressbooks/" + book.getId()), AddressBook.class);
        assertThat(retrieved.getBuddies()).hasSize(1);
        assertThat(retrieved.getBuddies().get(0).getName()).isEqualTo("Hasib");
        assertThat(retrieved.getBuddies().get(0).getPhoneNumber()).isEqualTo("123-456");
        assertThat(retrieved.getBuddies().get(0).getAddress()).isEqualTo("44 street");
    }


    @Test
    void testGetNonexistentAddressBook() {
        ResponseEntity<AddressBook> response =
                restTemplate.getForEntity(url("/api/addressbooks/999"), AddressBook.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void testAddBuddyToNonexistentAddressBook() {
        BuddyInfo buddy = new BuddyInfo();
        buddy.setName("Hasib");
        buddy.setPhoneNumber("000-000");
        buddy.setAddress("44 street");

        ResponseEntity<AddressBook> response =
                restTemplate.postForEntity(url("/api/addressbooks/999/buddies"), buddy, AddressBook.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testRemoveBuddy() {
        // Create address book
        AddressBook ab = restTemplate.postForEntity(url("/api/addressbooks"), null, AddressBook.class).getBody();

        // Add buddy
        BuddyInfo buddy = new BuddyInfo();
        buddy.setName("Hasib");
        buddy.setPhoneNumber("343-555");
        AddressBook updated = restTemplate.postForEntity(
                url("/api/addressbooks/" + ab.getId() + "/buddies"), buddy, AddressBook.class
        ).getBody();

        Long buddyId = updated.getBuddies().get(0).getId();

        // Remove buddy
        restTemplate.delete(url("/api/addressbooks/" + ab.getId() + "/buddies/" + buddyId));

        // Verify removed
        AddressBook after = restTemplate.getForObject(url("/api/addressbooks/" + ab.getId()), AddressBook.class);
        assertThat(after.getBuddies()).isEmpty();
    }


}

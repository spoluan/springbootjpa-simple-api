package taodigital.interview.jpa.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import taodigital.interview.jpa.demo.entity.AppUser;
import taodigital.interview.jpa.demo.entity.Inventory;
import taodigital.interview.jpa.demo.model.request.BorrowAndReturnBooksRequest;
import taodigital.interview.jpa.demo.repository.AppUserRepository;
import taodigital.interview.jpa.demo.repository.InventoryRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    void testGetAllBooks() throws Exception {
        mockMvc.perform(
                get("/books")
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("offset", "6")
                        .queryParam("limit", "3")
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                }
        );
    }

    @Test
    void testGetBookByIdSuccess() throws Exception {
        mockMvc.perform(
                get("/books/703bcc77-2b38-422e-90e6-fd60e7ea8743")
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                }
        );
    }

    @Test
    void testGetBookByIdNotFound() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                get("/books/703bcc77-2b38-422e-90e6-fd60e7ea874")
        ).andExpectAll(
                status().isNotFound(),
                content().string(Matchers.containsString("Not found"))
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                }
        );
    }

    @Test
    void testGetBookByIdNotValidUUID() throws Exception {
        mockMvc.perform(
                get("/books/703bcc77")
        ).andExpectAll(
                status().isBadRequest(),
                content().string(Matchers.containsString("Invalid UUID string: 703bcc77"))
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                }
        );
    }

    @Test
    void testBooksBorrowSuccess() throws Exception {

        Optional<Inventory> inventory = inventoryRepository.findById(UUID.fromString("dd95d1e8-7cd1-4a2d-93af-81882f4aca52"));
        inventory.get().setBorrowDate(null);
        inventory.get().setAppUser(null);

        inventoryRepository.save(inventory.get());

        BorrowAndReturnBooksRequest request = new BorrowAndReturnBooksRequest();
        request.setUserId(UUID.fromString("2e1273f4-f574-4807-929e-d7c002643981"));
        request.setInventoryId(UUID.fromString("dd95d1e8-7cd1-4a2d-93af-81882f4aca52"));

        mockMvc.perform(
                put("/books/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isAccepted()
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                    System.out.println("Response code:" + result.getResponse().getStatus());
                }
        );
    }

    @Test
    void testBooksBorrowBadRequest() throws Exception {

        BorrowAndReturnBooksRequest request = new BorrowAndReturnBooksRequest();
//        request.setUserId(UUID.fromString("2e1273f4-f574-4807-929e-d7c002643981"));
//        request.setInventoryId(UUID.fromString("dd95d1e8-7cd1-4a2d-93af-81882f4aca52"));

        mockMvc.perform(
                put("/books/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest(),
                content().string(Matchers.containsString("inventoryId: must not be null")),
                content().string(Matchers.containsString("userId: must not be null"))
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                    System.out.println("Response code:" + result.getResponse().getStatus());
                }
        );
    }

    @Test
    void testBooksBorrowUserNotFound() throws Exception {

        BorrowAndReturnBooksRequest request = new BorrowAndReturnBooksRequest();
        request.setUserId(UUID.fromString("2e1273f4-f574-4807-929e-d7c00264398"));
        request.setInventoryId(UUID.fromString("dd95d1e8-7cd1-4a2d-93af-81882f4aca5"));

        mockMvc.perform(
                put("/books/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound(),
                content().string(Matchers.containsString("User not found"))
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                    System.out.println("Response code:" + result.getResponse().getStatus());
                }
        );
    }

    @Test
    void testBooksBorrowInventoryNotFound() throws Exception {

        BorrowAndReturnBooksRequest request = new BorrowAndReturnBooksRequest();
        request.setUserId(UUID.fromString("2e1273f4-f574-4807-929e-d7c002643981"));
        request.setInventoryId(UUID.fromString("dd95d1e8-7cd1-4a2d-93af-81882f4aca"));

        mockMvc.perform(
                put("/books/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound(),
                content().string(Matchers.containsString("Inventory not found"))
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                    System.out.println("Response code:" + result.getResponse().getStatus());
                }
        );
    }

    @Test
    void testBooksBorrowInventoryBookAlreadyBorrowed() throws Exception {

        Optional<AppUser> user = appUserRepository.findById(UUID.fromString("2e1273f4-f574-4807-929e-d7c002643981"));

        Inventory inventory = new Inventory();
        inventory.setBorrowDate(LocalDateTime.now());
        inventory.setAppUser(user.get());

        inventoryRepository.save(inventory);

        BorrowAndReturnBooksRequest request = new BorrowAndReturnBooksRequest();
        request.setUserId(UUID.fromString("2e1273f4-f574-4807-929e-d7c002643981"));
        request.setInventoryId(UUID.fromString("7b12b47f-d7b7-48f1-b845-f5da5be9e813"));

        mockMvc.perform(
                put("/books/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isConflict(),
                content().string(Matchers.containsString("The book is already borrowed"))
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                    System.out.println("Response code:" + result.getResponse().getStatus());
                }
        );
    }

    @Test
    void testBooksReturnSuccess() throws Exception {
        Optional<AppUser> user = appUserRepository.findById(UUID.fromString("2e1273f4-f574-4807-929e-d7c002643981"));
        Optional<Inventory> inventory = inventoryRepository.findById(UUID.fromString("dd95d1e8-7cd1-4a2d-93af-81882f4aca52"));

        inventory.get().setBorrowDate(LocalDateTime.now());
        inventory.get().setAppUser(user.get());

        inventoryRepository.save(inventory.get());

        BorrowAndReturnBooksRequest request = new BorrowAndReturnBooksRequest();
        request.setUserId(UUID.fromString("2e1273f4-f574-4807-929e-d7c002643981"));
        request.setInventoryId(UUID.fromString("dd95d1e8-7cd1-4a2d-93af-81882f4aca52"));

        mockMvc.perform(
                put("/books/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isAccepted()
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                }
        );
    }

    @Test
    void testBooksReturnUserNotBorrow() throws Exception {

        Optional<Inventory> inventory = inventoryRepository.findById(UUID.fromString("dd95d1e8-7cd1-4a2d-93af-81882f4aca52"));

        inventory.get().setBorrowDate(null);
        inventory.get().setAppUser(null);

        inventoryRepository.save(inventory.get());

        BorrowAndReturnBooksRequest request = new BorrowAndReturnBooksRequest();
        request.setUserId(UUID.fromString("2e1273f4-f574-4807-929e-d7c002643981"));
        request.setInventoryId(UUID.fromString("dd95d1e8-7cd1-4a2d-93af-81882f4aca52"));

        mockMvc.perform(
                put("/books/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound(),
                content().string(Matchers.containsString("This user has not borrowed this book"))
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                }
        );
    }

    @Test
    void testBooksReturnBadRequest() throws Exception {

        BorrowAndReturnBooksRequest request = new BorrowAndReturnBooksRequest();
//        request.setUserId(UUID.fromString("2e1273f4-f574-4807-929e-d7c002643981"));
//        request.setInventoryId(UUID.fromString("dd95d1e8-7cd1-4a2d-93af-81882f4aca52"));

        mockMvc.perform(
                put("/books/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest(),
                content().string(Matchers.containsString("inventoryId: must not be null")),
                content().string(Matchers.containsString("userId: must not be null"))
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                }
        );
    }

    @Test
    void testBooksReturnConflict() throws Exception {

        Optional<AppUser> user = appUserRepository.findById(UUID.fromString("2e1273f4-f574-4807-929e-d7c002643981"));
        Optional<Inventory> inventory = inventoryRepository.findById(UUID.fromString("dd95d1e8-7cd1-4a2d-93af-81882f4aca52"));

        inventory.get().setBorrowDate(LocalDateTime.now());
        inventory.get().setAppUser(user.get());

        inventoryRepository.save(inventory.get());

        BorrowAndReturnBooksRequest request = new BorrowAndReturnBooksRequest();
        request.setUserId(UUID.fromString("4fb4e9a8-2d65-4313-9e36-776c4b560b7c"));
        request.setInventoryId(UUID.fromString("dd95d1e8-7cd1-4a2d-93af-81882f4aca52"));

        mockMvc.perform(
                put("/books/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isConflict(),
                content().string(Matchers.containsString("The book cannot be returned by another user"))
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                }
        );
    }

}
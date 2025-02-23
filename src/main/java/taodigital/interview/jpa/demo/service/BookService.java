package taodigital.interview.jpa.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import taodigital.interview.jpa.demo.entity.AppUser;
import taodigital.interview.jpa.demo.entity.Book;
import taodigital.interview.jpa.demo.entity.Inventory;
import taodigital.interview.jpa.demo.model.request.BookRequest;
import taodigital.interview.jpa.demo.model.request.BorrowAndReturnBooksRequest;
import taodigital.interview.jpa.demo.model.response.BookPagingResponse;
import taodigital.interview.jpa.demo.model.response.BookResponse;
import taodigital.interview.jpa.demo.model.response.InventoryBookResponse;
import taodigital.interview.jpa.demo.model.response.Paging;
import taodigital.interview.jpa.demo.repository.AppUserRepository;
import taodigital.interview.jpa.demo.repository.BookRepository;
import taodigital.interview.jpa.demo.repository.InventoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ValidationService validationService;

    public BookPagingResponse query(Integer offset, Integer limit) {

        Integer pageNumber = offset / limit;
        Pageable pageable = PageRequest.of(pageNumber, limit);
        Page<Book> page = bookRepository.findAll(pageable);
        List<Book> books = page.getContent().stream().collect(Collectors.toList());
        List<BookResponse> bookList = books.stream().map(book -> toBookResponse(book)).toList();

        return BookPagingResponse.builder()
                .books(bookList)
                .paging(Paging.builder()
                        .pageNumber(pageable.getPageNumber())
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .totalPages(page.getTotalPages())
                        .build())
                .build();
    }

    public BookResponse get(UUID id) {
        BookRequest request = new BookRequest();
        request.setId(id);

        validationService.validate(request);

        Book book = bookRepository.findById(request.getId()).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        });
        return toBookResponse(book);
    }

    public String booksBorrow(UUID userId, UUID inventoryId) {
        BorrowAndReturnBooksRequest request = new BorrowAndReturnBooksRequest();
        request.setUserId(userId);
        request.setInventoryId(inventoryId);

        validationService.validate(request);

        AppUser user = appUserRepository.findById(request.getUserId()).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        });

        Inventory inventory = inventoryRepository.findById(request.getInventoryId()).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found");
        });

        Book book = bookRepository.findById(inventory.getBook().getId()).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        });


        if(inventory.getAppUser() != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The book is already borrowed");


        inventory.setBorrowDate(LocalDateTime.now());
        inventory.setAppUser(user);

        inventoryRepository.save(inventory);

        return "Accepted";
    }

    public String booksReturn(UUID userId, UUID inventoryId) {
        BorrowAndReturnBooksRequest request = new BorrowAndReturnBooksRequest();
        request.setUserId(userId);
        request.setInventoryId(inventoryId);

        validationService.validate(request);

        AppUser user = appUserRepository.findById(request.getUserId()).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        });

        Inventory inventory = inventoryRepository.findById(request.getInventoryId()).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found");
        });

        Book book = bookRepository.findById(inventory.getBook().getId()).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        });

        if (inventory.getAppUser() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user has not borrowed this book");
        }

        if (inventory.getAppUser().getId() != userId) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The book cannot be returned by another user");
        }

        inventory.setBorrowDate(null);
        inventory.setAppUser(null);

        inventoryRepository.save(inventory);

        return "Accepted";
    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .image(book.getImage())
                .inventories(book.getInventoryList().stream().map(inventory -> toInventoryResponse(inventory)).toList())
                .build();
    }

    public InventoryBookResponse toInventoryResponse(Inventory inventory) {
        return InventoryBookResponse.builder()
                .id(inventory.getId())
                .borrowDate(inventory.getBorrowDate())
                .userId((inventory.getAppUser() == null) ? null : inventory.getAppUser().getId())
                .role((inventory.getAppUser() == null) ? null: inventory.getAppUser().getRole())
                .username((inventory.getAppUser() == null) ? null : inventory.getAppUser().getUsername())
                .build();
    }
}

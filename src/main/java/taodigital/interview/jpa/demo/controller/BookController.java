package taodigital.interview.jpa.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import taodigital.interview.jpa.demo.model.request.BorrowAndReturnBooksRequest;
import taodigital.interview.jpa.demo.model.response.BookPagingResponse;
import taodigital.interview.jpa.demo.model.response.BookResponse;
import taodigital.interview.jpa.demo.service.BookService;
import java.util.UUID;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(
            path = "/books",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BookPagingResponse query(
            @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit) {
        return bookService.query(offset, limit);
    }

    @GetMapping(
            path = "/books/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BookResponse get(@PathVariable(value = "id") UUID id) {
        return bookService.get(id);
    }

    @PutMapping(path = "/books/borrow")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public String booksBorrow(@RequestBody BorrowAndReturnBooksRequest request) {
        return bookService.booksBorrow(request.getUserId(), request.getInventoryId());
    }

    @PutMapping(path = "/books/return")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public String booksReturn(@RequestBody BorrowAndReturnBooksRequest request) {
        return bookService.booksReturn(request.getUserId(), request.getInventoryId());
    }
}

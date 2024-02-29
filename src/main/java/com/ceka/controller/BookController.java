package com.ceka.controller;

import com.ceka.domain.Book;
import com.ceka.dto.BookDTO;
import com.ceka.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor


@RestController// json body. no view
@Controller //model and vieew
@RequestMapping("/books")//http://localhost:8080/books
public class BookController {//requestler default setting get run
    private BookService bookService;

    //save book
    @PostMapping//http://localhost:8080/books + POST
    public ResponseEntity<String> saveBook(@Valid @RequestBody Book book) {
        bookService.saveBook(book);
        return new ResponseEntity<>("Kitap Başarılı bir şekilde kaydedildi....", HttpStatus.CREATED);
    }

    //Get All Books
    @GetMapping//http://localhost:8080/books + GET
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }

    // get book by its ıd -- id yi path ile alıcaz
    @GetMapping("/id/{id}")//http://localhost:8080/books/id/1 + GET
    public ResponseEntity<Book> getByid(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);

    }

    // get book by its ıd -- id yi query param ile alıcaz.
    @GetMapping("/id")//http://localhost:8080/books/id?id_param=1 + GET
    public ResponseEntity<Book> getByidQuery(@RequestParam("id_param") Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);

    }

    @DeleteMapping("id/{id}")//http://localhost:8080/books/id/1 DELETE
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>(id + " numaralı kitabı Silme işlemi başarılı ", HttpStatus.OK);

    }

    //Get a book by its Id with
    @GetMapping("/query")//http://localhost:8080/books/query?id=1
    public ResponseEntity<Book> getBookByIdWithRequestParam(@RequestParam("id") Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);


    }

    @GetMapping("/search")//http://localhost:8080/books/search?title=Spring_book
    public ResponseEntity<Book> getBookByTitleWithRequrestParam(@RequestParam("title") String title) {
        Book book = bookService.findBookByTitle(title);
        return ResponseEntity.ok(book);

    }

    //Get Books With Page
    @GetMapping("/listing")//http://localhost:8080/books/s?page=1&size=2&sort=publicationDate&direction=ASC
    public ResponseEntity<Page<Book>> getBooksWithPage(
            @RequestParam(value = "page",defaultValue = "1") int page,
            @RequestParam("sort") String sort,
            @RequestParam("size") int size,
            @RequestParam("direction") Sort.Direction direction

    ) {

        //bookService.findAllBooksPage(page,sort,size,direction);
        PageRequest pageable = PageRequest.of(page-1, size, Sort.by(direction, sort));

        Page<Book> bookPage = bookService.findAllBooksWithPage(pageable);
        return ResponseEntity.ok(bookPage);

    }

    //update A book With ussing dto
    @PatchMapping("/update/{id}")//http://localhost:8080/books/update/3
    public ResponseEntity<Map<String,String>> updateBookWithDTO(
            @PathVariable Long id, @Valid  @RequestBody BookDTO bookDTO

    ){
        bookService.updateBookWithDTO(id,bookDTO);
        Map<String,String> msg=new HashMap<>();
        msg.put("message","Book has been updated successfully.");
        return ResponseEntity.ok(msg);
    }


    //Get a book BY ıts Author Using JPQL
    @GetMapping("/search/author")//http://localhost:8080/books/search/author?author=AB
    public ResponseEntity<List<Book>> getBookAuthorWithJPQL(@RequestParam("author") String author){
        List<Book> books=bookService.getBookByAuthorWithJPQL(author);
        return ResponseEntity.ok(books);

    }


}

package com.ceka.service;

import com.ceka.domain.Book;
import com.ceka.dto.BookDTO;
import com.ceka.exception.ResourceNotFoundException;
import com.ceka.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BookService {

    private BookRepository bookRepository;

    public void saveBook(Book book) {
        bookRepository.save(book);//türetilmiş method JpaRepository
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(id + " Numaralı kitap bulunamdı...")
        );//id li eleman yok ise null dönmesine karşılık orElseThrow
    }

    public void deleteBookById(Long id) {

        findById(id);//id li kitap yoksa cliente hata mesajı gönderiri. deleteById çalışmaz.
        bookRepository.deleteById(id);
    }

    public Book findBookByTitle(String title) {
        Book book = bookRepository.findBookByTitle(title);
        return book;


    }

    public Page<Book> findAllBooksWithPage(PageRequest pageable) {
        return bookRepository.findAll(pageable);

    }

    public void updateBookWithDTO(Long id, BookDTO bookDTO) {
        Book bookFromDB = findById(id);

        bookFromDB.setTitle(bookDTO.getTitle());
        bookFromDB.setAuthor(bookDTO.getAuthor());
        bookFromDB.setPublicationDate(bookDTO.getPublicationDate());

        bookRepository.save(bookFromDB);
    }

    public List<Book> getBookByAuthorWithJPQL(String author) {
       List<Book> books= bookRepository.findByAuthorWithJPQL(author);

       if(books.isEmpty()){
           throw new ResourceNotFoundException("No books were found for the author."+author);
       }

       return books;
    }
}

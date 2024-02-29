package com.ceka.repository;

import com.ceka.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//JpaRepository olduğu için gerek yok

public interface BookRepository extends JpaRepository<Book,Long> {

    Book findBookByTitle(String title);//türetilmiş method.

    @Query("SELECT b from Book b WHERE b.author=:yazar")//sql  : select * from book b where author=?
    List<Book> findByAuthorWithJPQL(@Param("yazar" )  String author);
    //@Param("yazar")  :  authoru query e yazar olarak ver
}

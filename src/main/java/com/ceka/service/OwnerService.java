package com.ceka.service;

import com.ceka.domain.Book;
import com.ceka.domain.Owner;
import com.ceka.exception.ConflictException;
import com.ceka.exception.ResourceNotFoundException;
import com.ceka.repository.BookRepository;
import com.ceka.repository.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor//Auto wired yeerine constructor bean kullnıyoruz.
public class OwnerService {
    private OwnerRepository ownerRepository;
    private BookService bookService;//owner!a book eklicez .onun için bu field var.
    private BookRepository bookRepository;
    public void saveOwner(Owner owner) {

     Boolean emailExists= ownerRepository.existsByEmail(owner.getEmail());
     if (emailExists){
         throw new ConflictException("Bu mail ile önceden kayıt yapılmış.Lütfen farklı bir mail kullnınız.");
     }
     ownerRepository.save(owner);


    }

    public List<Owner> getAllOwner() {
         //return ownerRepository.findAll();//boş liste gelmesin boş ise
         List<Owner> owners=ownerRepository.findAll();
        if (owners.isEmpty()) {
            throw new ResourceNotFoundException("No owner found...");
        }
        return owners;


    }

    public Owner findOwnerById(Long id) {
        return ownerRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(id + " ' e ait owner bulunamadı..."));
    }

    public void addBooKToOwner(Long ownerId, Long bookId) {
        //* owner a book eklicez.
        //ownerıd ile owneri bul.kontol et
        Owner owner=findOwnerById(ownerId);
        //bookId ile book u bul.
       Book book= bookService.findById(bookId);
        //belirtilen owner ,daha önce bu owner'a kayıt edidldi  mi?
         boolean bookExists=owner.getBooks()
                 .stream()//akış başlattık.ownerin kitapları için akış başlattık.
                 //==========
                 .anyMatch(bookInOwner->bookInOwner.getId().equals(bookId));//eşleşiyormu kontrolu yapar.
      //  akışdan gelen book (bookInOwner) diye isimlendirdik.ve  idlerden bizim bookid e eşit olan var mı ddiye bakacak
       //var ise tru alıcaz.
        //=====================
        if(bookExists){
            throw new ConflictException(ownerId+"  Bu id numaralı owner için bu kitap zaten mevcut..");
        }

        //herşey okey se owner'a book eklicez.
        owner.getBooks().add(book);
        ownerRepository.save(owner);

        //book'a owner ekleme
        book.setOwner(owner);//spring de save aynı zamanda update görevi görür
        bookRepository.save(book);





    }
}

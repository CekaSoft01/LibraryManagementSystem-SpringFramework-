package com.ceka.controller;

import com.ceka.domain.Owner;
import com.ceka.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("owners")
public class OwnerController {//http:localhost:8080/owners

    @Autowired
    private OwnerService ownerService;


    @PostMapping("/save")//http:localhost:8080/owners/save
    public ResponseEntity<Map<String, String>> saveOwner(@Valid @RequestBody Owner owner) {
        ownerService.saveOwner(owner);
        Map<String, String> msg = new HashMap<>();
        msg.put("message", "Owner has been saved successfully...");
        return ResponseEntity.ok(msg);


    }

    //fin All owner
    @GetMapping()//http:localhost:8080/owners
    public ResponseEntity<List<Owner>> getAllOwner() {
        List<Owner> owner = ownerService.getAllOwner();
        return ResponseEntity.ok(owner);
    }

    //Find an Owner By id
    @GetMapping("/id")//http:localhost:8080/owners/id?id=?
    public ResponseEntity<Owner> getOwnerByid(@RequestParam("id") Long id) {
        ;
        return ResponseEntity.ok(ownerService.findOwnerById(id));

    }

    //owner'a book ekleme
    @PostMapping("/{id}")//http://localhost:8080/owners/1?bookId=bookName
    public ResponseEntity<String> addBookToOwner(@RequestParam("bookId") Long bookId, @PathVariable("id") Long ownerId) {
        //@PathVariable("id")//Hata alıca id ekledik

        ownerService.addBooKToOwner(ownerId, bookId);
        return ResponseEntity.ok("message: Kitap başarılıbir şekilde kaydedildi");


    }


}

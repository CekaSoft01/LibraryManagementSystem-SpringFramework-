package com.ceka.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;


    @NotBlank(message = "İsim gerekli format ile yazınız...")//form saving control
    @NotNull(message = "İsim boş bırakılamaz.")//form saving control//
    @Size(min = 2, max = 25, message = "İsim '${validatedValue}', {min} ile {max} karakter olmalı.")//form posting control
    @Column(length = 25, nullable = false)//database saving control
    private  String name;


    @Column(length = 30,nullable = false)
    private String lastName;


    private String phoneNumber;


    @Column(nullable = false,unique = true)
    @Email(message = "Lütfen geçerli bir mail giriniz..")
    private String email;


    @Setter(AccessLevel.NONE)
    private LocalDateTime registrationDate=LocalDateTime.now();

    @OneToMany(mappedBy = "owner")//owner one  -- book  many
    //mantık olarak forein key in many  olan tarafda olması gerekir.
//    owner ı barından entity clasına forein key at demek.owner i barından class many olan class
    //iki tarafa da frein key atmasın diye

    private List<Book> books=new ArrayList<>();






}

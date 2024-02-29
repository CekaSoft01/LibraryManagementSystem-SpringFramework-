package com.ceka.dto;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class BookDTO {

    @NotBlank(message = "Kitap isini gerekli format ile yazınız...")//form saving control
    @NotNull(message = "Kitap ismi boş bırakılamaz.")//form saving control//
    @Size(min = 2, max = 25, message = "Kitap ismi '${validatedValue}', {min} ile {max} karakter olmalı.")//form posting control
    @Column(length = 25, nullable = false)//database saving control
    private String title; //başlık

    private String author;//yazar

    @Column(nullable = false)
    private String publicationDate;//yaın tarihi


}

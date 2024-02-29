package com.ceka.repository;

import com.ceka.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner,Long> {
    Boolean existsByEmail(String email);
    //owner entity clasıı için datbase metotları üretecek olan  repository interface miz.


}

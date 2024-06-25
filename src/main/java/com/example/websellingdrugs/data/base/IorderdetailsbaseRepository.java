package com.example.websellingdrugs.data.base;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.websellingdrugs.models.orderdetails;
import com.example.websellingdrugs.models.products;

public interface IorderdetailsbaseRepository extends JpaRepository<orderdetails , Long> {
     @Override
    List<orderdetails> findAll();

    @Override
    Optional<orderdetails> findById(Long id);

    @Override
    <S extends orderdetails> S save(S entity);

    @Override
    void delete(orderdetails entity);
   
}

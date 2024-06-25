package com.example.websellingdrugs.data.base;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.websellingdrugs.models.categories;
import com.example.websellingdrugs.models.orders;
import com.example.websellingdrugs.models.products;

public interface IodersbaseRepository extends JpaRepository<orders , Long> {
     @Override
    List<orders> findAll();

    @Override
    <S extends orders> S save(S entity);

    @Override
    void delete(orders entity);
    
    
}

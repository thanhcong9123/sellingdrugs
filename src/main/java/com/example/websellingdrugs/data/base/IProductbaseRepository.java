package com.example.websellingdrugs.data.base;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import com.example.websellingdrugs.models.products;


public interface IProductbaseRepository extends JpaRepository<products, Long> {

    @Override
    List<products> findAll();

    @Override
    Optional<products> findById(Long id);

    @Override
    <S extends products> S save(S entity);

    @Override
    void delete(products entity);
    Page<products> findByNameContainingIgnoreCase(String name, Pageable pageable);

}

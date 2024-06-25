package com.example.websellingdrugs.data.base;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.websellingdrugs.models.categories;
import com.example.websellingdrugs.models.products;

import jakarta.persistence.TypedQuery;

public interface ICategoriesbaseRepository extends JpaRepository<categories, Long> {
     @Override
    List<categories> findAll();

    @Override
    Optional<categories> findById(Long id);

    @Override
    <S extends categories> S save(S entity);

    @Override
    void delete(categories entity);

    TypedQuery<categories> findByName(String name);
    
}

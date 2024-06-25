package com.example.websellingdrugs.data.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.websellingdrugs.data.base.ICategoriesbaseRepository;
import com.example.websellingdrugs.data.base.IProductbaseRepository;
import com.example.websellingdrugs.models.categories;
import com.example.websellingdrugs.models.products;

import jakarta.persistence.TypedQuery;

@Service
public class categoriesService {
    private final ICategoriesbaseRepository  _CategoriesbaseRepository;
    private final JdbcTemplate _jdbcTemplate;
    @Autowired
    public categoriesService(ICategoriesbaseRepository CategoriesbaseRepository,JdbcTemplate jdbcTemplate)
    {
        _CategoriesbaseRepository = CategoriesbaseRepository;
        _jdbcTemplate =  jdbcTemplate;
    }
    public List<categories> getAll()
    {
        return _CategoriesbaseRepository.findAll();
    }
    public List<categories> findByName(String name)
    {
        String sql = "SELECT * FROM categories WHERE categories.name = '" + name + "'";
        return _jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(categories.class));
    }
}

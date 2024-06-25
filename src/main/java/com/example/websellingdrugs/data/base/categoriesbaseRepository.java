
package com.example.websellingdrugs.data.base;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.example.websellingdrugs.models.categories;
import com.example.websellingdrugs.models.products;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class categoriesbaseRepository extends SimpleJpaRepository<categories, Long> implements ICategoriesbaseRepository {

    private final EntityManager entityManager;

    public categoriesbaseRepository(Class<categories> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
        //TODO Auto-generated constructor stub
    }
    @Override
    public List<categories> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<categories> findById(Long id) {
        return super.findById(id);
    }

    @Override
    public <S extends categories> S save(S entity) {
        return super.save(entity);
    }
    @Override
    public TypedQuery<categories> findByName(String name) {
        String sql = "SELECT * FROM categories WHERE categories.name = 'name';";
        return entityManager.createQuery(sql, categories.class);
    }
    @Override
    public void delete(categories entity) {
        super.delete(entity);
    }
}

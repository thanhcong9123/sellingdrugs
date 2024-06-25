package com.example.websellingdrugs.data.services.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.websellingdrugs.data.base.IProductbaseRepository;
import com.example.websellingdrugs.data.base.IodersbaseRepository;
import com.example.websellingdrugs.data.base.IorderdetailsbaseRepository;
import com.example.websellingdrugs.data.viewmodels.cart;
import com.example.websellingdrugs.models.orderdetails;
import com.example.websellingdrugs.models.orders;
import com.example.websellingdrugs.models.products;
import com.example.websellingdrugs.models.users;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
public class productService {
    private final IProductbaseRepository _producRepository;
    private final JdbcTemplate _jdbcTemplate;
    private final EntityManager _entityManager;
    private final IorderdetailsbaseRepository _iodersbaseRepository;

    @Autowired
    public productService(IProductbaseRepository producRepository, JdbcTemplate jdbcTemplate,
            EntityManager entityManager,IorderdetailsbaseRepository iodersbaseRepository) {
        _producRepository = producRepository;
        _jdbcTemplate = jdbcTemplate;
        _entityManager = entityManager;
        _iodersbaseRepository =iodersbaseRepository;
    }

    public List<products> getAll() {
        List<products> product = _producRepository.findAll();
        if (product == null) {

        }

        Collections.sort(product, Comparator.comparingLong(products::getProductId).reversed());
        return product;
    }

    public List<products> bestSellers() {
        String sql = "SELECT p.* FROM orders o JOIN order_details od ON o.orderid = od.orderid JOIN products p ON od.productid = p.productid WHERE o.status = 'Confirmed' GROUP BY p.productid ORDER BY SUM(od.quantity) DESC LIMIT 10 ;";
        List<products> resulProducts = _jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(products.class));

        return resulProducts;
    }

    public products getProduct(Long id) {
        Optional<products> product = _producRepository.findById(id);

        return product.orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<products> geProductsByCategories(String categoriesName) {
        String sql = "SELECT p.* FROM products p JOIN categories c ON p.categoryid = c.categoryid WHERE c.name = '"+categoriesName+"';";
        return _jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(products.class));
    }

    public Page<products> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return _producRepository.findAll(pageable);
    }

    public Page<products> findPaginatedByCategoryName(Long categoryName,String sortBy, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String jpql = "SELECT p FROM products p WHERE p.category.categoryId = :categoryId";
        if(sortBy== "new")
        {
           jpql += " ORDER BY p.productId ASC";
        }
        if(sortBy== "old")
        {
            jpql += " ORDER BY p.productId DESC";
        }
        if(sortBy== "AZ")
        {
           jpql += " ORDER BY p.name ASC";
        }
        if(sortBy== "ZA")
        {
            jpql += " ORDER BY p.name DESC";
        }

        TypedQuery<products> query = _entityManager.createQuery(jpql, products.class);
        query.setParameter("categoryId", categoryName);
        
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<products> products = query.getResultList();

        long totalRecords = _entityManager
                .createQuery("SELECT COUNT(p) FROM products p WHERE p.category.categoryId = :categoryId", Long.class)
                .setParameter("categoryId", categoryName)
                .getSingleResult();

        return new PageImpl<>(products, pageable, totalRecords);
    }

    public List<products> getCartforUsert(users user) {
        String sql = "SELECT od.order_detailid as \"productId\", p.Name, p.Description, p.Price, od.Quantity, p.img\n" + //
                        "FROM users u\n" + //
                        "JOIN orders o ON u.UserID = o.UserID\n" + //
                        "JOIN order_details od ON o.OrderID = od.OrderID\n" + //
                        "JOIN products p ON od.ProductID = p.ProductID\n" + //
                        "WHERE u.UserID = "+user.getUserId()+" AND o.Status = 'Pending';";
        List<products> orders = _jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(products.class));

        return orders;
    }
    public Page<products> findPaginatedByProductName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return _producRepository.findByNameContainingIgnoreCase(name, pageable);
    }
    

}

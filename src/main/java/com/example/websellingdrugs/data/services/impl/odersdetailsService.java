package com.example.websellingdrugs.data.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.websellingdrugs.data.base.IorderdetailsbaseRepository;
import com.example.websellingdrugs.models.orderdetails;
import com.example.websellingdrugs.models.products;

@Service
public class odersdetailsService {
    private final IorderdetailsbaseRepository  _IorderdetailsbaseRepository;
    private final JdbcTemplate _jdbcTemplate;
    @Autowired
    public odersdetailsService(IorderdetailsbaseRepository oIorderdetailsbaseRepository,JdbcTemplate jdbcTemplate)
    {
        _IorderdetailsbaseRepository = oIorderdetailsbaseRepository;
        _jdbcTemplate =  jdbcTemplate;
    }
    public orderdetails addOdersdetails(orderdetails orderdetails)
    {
        
        return _IorderdetailsbaseRepository.save(orderdetails);
    }
    public orderdetails getorderdetails(Long id) {
        Optional<orderdetails> orderdetails = _IorderdetailsbaseRepository.findById(id);

        return orderdetails.orElseThrow(() -> new RuntimeException("Product not found"));
    }
    public void deleteorderdetails(orderdetails id) {
        _IorderdetailsbaseRepository.delete(id);
    }
   
}

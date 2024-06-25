package com.example.websellingdrugs.data.services.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.websellingdrugs.data.base.IProductbaseRepository;
import com.example.websellingdrugs.data.base.IUserbaseRepository;
import com.example.websellingdrugs.models.products;
import com.example.websellingdrugs.models.users;
import org.springframework.security.core.Authentication;


@Service
public class userService {
    private final IUserbaseRepository  _userbaseRepository;
    private final JdbcTemplate _jdbcTemplate;
    private final BCryptPasswordEncoder _bCryptPasswordEncoder;

    @Autowired
    public userService(IUserbaseRepository userbaseRepository,JdbcTemplate jdbcTemplate,BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        _userbaseRepository = userbaseRepository;
        _jdbcTemplate =  jdbcTemplate;
        _bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public Optional<users> findUsers (Long idUser)
    {
        return _userbaseRepository.findById(idUser);
    }


    public void save(users user) {
        user.setPassword(_bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        _userbaseRepository.save(user);
    }

    public users findByUsername(String username) {
        return _userbaseRepository.findByUsername(username);
    }
   
   
 
}

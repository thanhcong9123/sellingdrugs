package com.example.websellingdrugs.data.base;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.websellingdrugs.models.users;

@Repository
public interface IUserbaseRepository extends JpaRepository<users, Long> {
   users findByUsername(String username);

}

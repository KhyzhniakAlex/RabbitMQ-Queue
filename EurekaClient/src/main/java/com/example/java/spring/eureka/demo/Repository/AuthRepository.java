package com.example.java.spring.eureka.demo.Repository;

import com.example.java.spring.eureka.demo.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Users, Integer> {
    void deleteByUsername(String username);
    Users findByUsername(String username);
}

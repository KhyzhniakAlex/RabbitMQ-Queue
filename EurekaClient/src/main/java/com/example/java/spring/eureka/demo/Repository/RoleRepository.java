package com.example.java.spring.eureka.demo.Repository;

import com.example.java.spring.eureka.demo.Model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Authorities,Integer> {
    void deleteByUsername(String username);
}

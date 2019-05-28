package com.example.java.spring.eureka.demo.Repository;

import com.example.java.spring.eureka.demo.Model.LoggingTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingRepository  extends CrudRepository<LoggingTable, Integer> {
}

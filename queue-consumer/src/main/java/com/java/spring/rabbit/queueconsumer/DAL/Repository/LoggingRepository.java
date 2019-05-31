package com.java.spring.rabbit.queueconsumer.DAL.Repository;

import com.java.spring.rabbit.queueconsumer.DAL.Model.LoggingTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingRepository extends CrudRepository<LoggingTable, Integer> {
}

package com.zavadpe.springdemo.repository;


import com.zavadpe.springdemo.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {

    @Query("SELECT l FROM Lead l WHERE l.name LIKE ?1 OR l.surname LIKE ?1")
    List<Lead> findByName(String name);
}

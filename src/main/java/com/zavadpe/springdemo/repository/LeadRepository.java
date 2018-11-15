package com.zavadpe.springdemo.repository;


import com.zavadpe.springdemo.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {

    @Query("SELECT l FROM Lead l WHERE l.name LIKE ?1 OR l.surname LIKE ?1")
    List<Lead> findByName(String name);

    @Query(
            "SELECT l FROM Lead l WHERE l.name LIKE ?1 AND l.surname LIKE ?2 AND l.born LIKE ?3 AND l.address.postalCode LIKE ?4"
    )
    List<Lead> findDuplicate(String name, String surname, Date born, String postalCode);

    @Query(
            "SELECT l FROM Lead l JOIN Distribution d ON l.id = d.leadId AND d.agentId = ?1"
    )
    List<Lead> findLeadsByAgentId(Long agentId);
}

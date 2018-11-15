package com.zavadpe.springdemo.repository;

import com.zavadpe.springdemo.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    @Query("SELECT a FROM Agent a WHERE a.name LIKE ?1")
    List<Agent> findByName(String name);

    @Query(
            "SELECT a FROM Agent a WHERE a.name LIKE ?1 AND a.address.postalCode LIKE ?2"
    )
    List<Agent> findDuplicate(String name, String postalCode);

    @Query(
            // very naive comparision of postal codes
            "SELECT a FROM Agent a WHERE a.address.postalCode LIKE ?1"
    )
    List<Agent> findAgentByPostalCode(String postalCode);

    @Query(
            "SELECT a FROM Agent a JOIN Distribution d ON a.id = d.agentId AND d.leadId = ?1"
    )
    List<Agent> findAgentsByLeadId(Long leadId);
}

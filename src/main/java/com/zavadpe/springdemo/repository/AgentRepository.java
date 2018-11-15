package com.zavadpe.springdemo.repository;

import com.zavadpe.springdemo.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    @Query("SELECT a FROM Agent a WHERE a.name LIKE ?1")
    List<Agent> findByName(String name);
}

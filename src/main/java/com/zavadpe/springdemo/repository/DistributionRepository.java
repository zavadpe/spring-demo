package com.zavadpe.springdemo.repository;

import com.zavadpe.springdemo.model.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DistributionRepository extends JpaRepository<Distribution, Long> {

    @Query("SELECT d FROM Distribution d WHERE d.leadId = ?1")
    List<Distribution> findByLeadId(Long leadId);

    @Query("SELECT d FROM Distribution d WHERE d.agentId = ?1")
    List<Distribution> findByAgentId(Long agentId);
}

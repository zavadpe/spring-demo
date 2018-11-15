package com.zavadpe.springdemo.db;

import com.zavadpe.springdemo.repository.AgentRepository;
import com.zavadpe.springdemo.repository.DistributionRepository;
import com.zavadpe.springdemo.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements ApplicationRunner {

    private LeadRepository leadRepository;
    private AgentRepository agentRepository;
    private DistributionRepository distributionRepository;

    @Autowired
    public DatabaseLoader(LeadRepository leadRepository, AgentRepository agentRepository, DistributionRepository distributionRepository) {
        this.leadRepository = leadRepository;
        this.agentRepository = agentRepository;
        this.distributionRepository = distributionRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        leadRepository.saveAll(TestData.leads);
        agentRepository.saveAll(TestData.agents);
        distributionRepository.saveAll(TestData.distributions);
    }
}

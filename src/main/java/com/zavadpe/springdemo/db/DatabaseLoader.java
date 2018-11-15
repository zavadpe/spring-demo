package com.zavadpe.springdemo.db;

import com.zavadpe.springdemo.model.Address;
import com.zavadpe.springdemo.model.Agent;
import com.zavadpe.springdemo.model.Distribution;
import com.zavadpe.springdemo.model.Lead;
import com.zavadpe.springdemo.repository.AgentRepository;
import com.zavadpe.springdemo.repository.DistributionRepository;
import com.zavadpe.springdemo.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

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
        List<Lead> leads = Arrays.asList(
                new Lead("John", "Smith", "jsmith@gmail.com", "+421999888777", new Address("Washington", "Kennedy st.", "9", "10001"), new Date(82, 1, 1), true),
                new Lead("John", "Doe", "jdoe@gmail.co", "+421999777888", new Address("New York", "Wall st.", "12", "20001"), new Date(82, 2, 1), true),
                new Lead("Mark", "Webber", "marky@gmail.co", "+421999111000", new Address("Los Angeles", "Bouleward st.", "99", "30001"), new Date(85, 11, 25))
        );
        leadRepository.saveAll(leads);
        List<Agent> agents = Arrays.asList(
                new Agent("American Ins.", new Address("Washington", "Kennedy st.", "10", "10001")),
                new Agent("First New York Insurence", new Address("New York", "Wall st.", "10", "20001")),
                new Agent("California Ins. Comp.", new Address("Los Angeles", "Beverly Hils.", "90210", "30001"))
        );
        agentRepository.saveAll(agents);
        List<Distribution> distributions = Arrays.asList(
                new Distribution(1L, 4L),
                new Distribution(2L, 4L)
        );
        distributionRepository.saveAll(distributions);
    }
}

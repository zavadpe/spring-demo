package com.zavadpe.springdemo.rest;

import com.zavadpe.springdemo.errors.EntityIdNotFound;
import com.zavadpe.springdemo.model.Agent;
import com.zavadpe.springdemo.model.Distribution;
import com.zavadpe.springdemo.model.Lead;
import com.zavadpe.springdemo.repository.AgentRepository;
import com.zavadpe.springdemo.repository.DistributionRepository;
import com.zavadpe.springdemo.repository.LeadRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadRestController {

    @Autowired
    LeadRepository leadRepository;
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    DistributionRepository distributionRepository;

    @RequestMapping(method = RequestMethod.GET)
    Collection<Lead> getAllLeads() {
        return leadRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{leadId}")
    Lead getLeadById(@PathVariable Long leadId) {
        final Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new EntityIdNotFound("Lead", leadId));
        return lead;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{leadId}")
    ResponseEntity<?> editLeadById(@PathVariable Long leadId, @RequestBody Lead sourceLead) {
        final Lead targetLead = leadRepository.findById(leadId).orElseThrow(() -> new EntityIdNotFound("Lead", leadId));
        BeanUtils.copyProperties(sourceLead, targetLead, "id");
        return ResponseEntity.ok(leadRepository.save(targetLead));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{leadId}")
    ResponseEntity<?> deleteLeadById(@PathVariable Long leadId) {
        final Lead deletedLead = leadRepository.findById(leadId).orElseThrow(() -> new EntityIdNotFound("Lead", leadId));
        leadRepository.delete(deletedLead);
        return new ResponseEntity<>("Lead successfully deleted", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addLead(@RequestBody Lead lead) {
        List<Lead> duplicates = leadRepository.findDuplicate(lead.getName(), lead.getSurname(), lead.getBorn(), lead.getAddress().getPostalCode());
        if (duplicates.size() > 0) {
            return new ResponseEntity<>("Trying to create duplicate Lead!", HttpStatus.CONFLICT);
        }

        if ((lead = leadRepository.save(lead)) != null) {
            // Find Agent by postal code and distribute Lead if found
            List<Agent> agentsFound = agentRepository.findAgentByPostalCode(lead.getAddress().getPostalCode());
            if (agentsFound.size() > 0) {
                List<Distribution> distributions = new ArrayList<>();
                for (Agent agent: agentsFound) {
                    distributions.add(new Distribution(lead.getId(), agent.getId()));
                }
                distributionRepository.saveAll(distributions);
                lead.setDistributed(true);
                leadRepository.save(lead);
            }
            return new ResponseEntity<>("Lead successfully created", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error creating Lead", HttpStatus.BAD_REQUEST);
    }
}

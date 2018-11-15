package com.zavadpe.springdemo.rest;

import com.zavadpe.springdemo.errors.EntityIdNotFound;
import com.zavadpe.springdemo.model.Lead;
import com.zavadpe.springdemo.repository.LeadRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/leads")
public class LeadRestController {

    @Autowired
    LeadRepository leadRepository;

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
        if ((leadRepository.save(lead)) != null) {
            return new ResponseEntity<>("Lead successfully created", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error creating Lead", HttpStatus.BAD_REQUEST);
    }
}

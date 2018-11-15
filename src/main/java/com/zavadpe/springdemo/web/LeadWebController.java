package com.zavadpe.springdemo.web;

import com.zavadpe.springdemo.errors.EntityIdNotFound;
import com.zavadpe.springdemo.model.Agent;
import com.zavadpe.springdemo.model.Distribution;
import com.zavadpe.springdemo.model.Lead;
import com.zavadpe.springdemo.repository.AgentRepository;
import com.zavadpe.springdemo.repository.DistributionRepository;
import com.zavadpe.springdemo.repository.LeadRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/leads")
public class LeadWebController {

    @Autowired
    LeadRepository leadRepository;
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    DistributionRepository distributionRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String allLeads(Model model) {
        model.addAttribute("leads", leadRepository.findAll());
        return "Leads";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/create")
    public String createLead(Model model) {
        model.addAttribute("lead", new Lead());
        return "FormCreateLead";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public String createLeadSubmit(@Valid Lead lead, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("validation_errors", result.getAllErrors());
            return "FormCreateLead";
        }
        List<Lead> duplicates = leadRepository.findDuplicate(lead.getName(), lead.getSurname(), lead.getBorn(), lead.getAddress().getPostalCode());
        if (duplicates.size() > 0) {
            model.addAttribute("duplicate", "Lead already exists");
            return "FormCreateLead";
        }
        lead = leadRepository.save(lead);
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
            final List<Agent> assignedToAgents = agentRepository.findAgentsByLeadId(lead.getId());
            if (assignedToAgents.size() > 0) {
                model.addAttribute("assigned_to_agents", assignedToAgents);
            }
        }
        return "SingleLead";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{leadId}")
    public String singleLead(@PathVariable Long leadId, Model model) {
        final Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new EntityIdNotFound("Lead", leadId));
        model.addAttribute("lead", lead);
        if (lead.isDistributed()) {
            final List<Agent> assignedToAgents = agentRepository.findAgentsByLeadId(leadId);
            if (assignedToAgents.size() > 0) {
                model.addAttribute("assigned_to_agents", assignedToAgents);
            }
        }
        return "SingleLead";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{leadId}/edit")
    public String editLead(@PathVariable Long leadId, Model model) {
        final Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new EntityIdNotFound("Lead", leadId));
        model.addAttribute("lead", lead);
        return "FormEditLead";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{leadId}/edit")
    public String editLeadSubmit(@PathVariable Long leadId, @Valid Lead sourceLead, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "FormEditLead";
        }
        final Lead targetLead = leadRepository.findById(leadId).orElseThrow(() -> new EntityIdNotFound("Lead", leadId));
        BeanUtils.copyProperties(sourceLead, targetLead, "id");
        leadRepository.save(targetLead);
        model.addAttribute("lead", targetLead);
        return "SingleLead";
    }
}

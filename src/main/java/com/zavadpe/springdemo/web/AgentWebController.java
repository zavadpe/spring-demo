package com.zavadpe.springdemo.web;

import com.zavadpe.springdemo.errors.EntityIdNotFound;
import com.zavadpe.springdemo.model.Agent;
import com.zavadpe.springdemo.model.Lead;
import com.zavadpe.springdemo.repository.AgentRepository;
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
import java.util.List;

@Controller
@RequestMapping("/agents")
public class AgentWebController {

    @Autowired
    LeadRepository leadRepository;
    @Autowired
    AgentRepository agentRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String allAgents(Model model) {
        model.addAttribute("agents", agentRepository.findAll());
        return "Agents";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/create")
    public String createAgent(Model model) {
        model.addAttribute("agent", new Agent());
        return "FormCreateAgent";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public String createAgentSubmit(@Valid Agent agent, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("validation_errors", result.getAllErrors());
            return "FormCreateAgent";
        }
        List<Agent> duplicates = agentRepository.findDuplicate(agent.getName(), agent.getAddress().getPostalCode());
        if (duplicates.size() > 0) {
            model.addAttribute("duplicate", "Agent already exists");
            return "FormCreateAgent";
        }
        agentRepository.save(agent);
        return "SingleAgent";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{agentId}")
    public String singleAgent(@PathVariable Long agentId, Model model) {
        final Agent agent = agentRepository.findById(agentId).orElseThrow(() -> new EntityIdNotFound("Agent", agentId));
        model.addAttribute("agent", agent);
        List<Lead> assignedLeads = leadRepository.findLeadsByAgentId(agentId);
        if (assignedLeads.size() > 0) {
            model.addAttribute("assigned_leads", assignedLeads);
        }
        return "SingleAgent";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{agentId}/edit")
    public String editAgent(@PathVariable Long agentId, Model model) {
        final Agent agent = agentRepository.findById(agentId).orElseThrow(() -> new EntityIdNotFound("Agent", agentId));
        model.addAttribute("agent", agent);
        return "FormEditAgent";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{agentId}/edit")
    public String editAgentSubmit(@PathVariable Long agentId, @Valid Agent sourceAgent, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "FormEditAgent";
        }
        final Agent targetAgent = agentRepository.findById(agentId).orElseThrow(() -> new EntityIdNotFound("Agent", agentId));
        BeanUtils.copyProperties(sourceAgent, targetAgent, "id");
        agentRepository.save(targetAgent);
        model.addAttribute("agent", targetAgent);
        return "SingleAgent";
    }
}

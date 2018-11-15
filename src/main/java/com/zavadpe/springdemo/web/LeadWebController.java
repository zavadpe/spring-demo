package com.zavadpe.springdemo.web;

import com.zavadpe.springdemo.errors.EntityIdNotFound;
import com.zavadpe.springdemo.model.Lead;
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

@Controller
@RequestMapping("/leads")
public class LeadWebController {

    @Autowired
    LeadRepository leadRepository;

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
            return "FormCreateLead";
        }
        leadRepository.save(lead);
        return "SingleLead";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{leadId}")
    public String singleLead(@PathVariable Long leadId, Model model) {
        final Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new EntityIdNotFound("Lead", leadId));
        model.addAttribute("lead", lead);
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

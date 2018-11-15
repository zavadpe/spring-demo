package com.zavadpe.springdemo.web;

import com.zavadpe.springdemo.repository.AgentRepository;
import com.zavadpe.springdemo.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/home")
public class HomeWebController {

    @Autowired
    LeadRepository leadRepository;
    @Autowired
    AgentRepository agentRepository;


    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("leads", leadRepository.findAll());
        model.addAttribute("agents", agentRepository.findAll());
        return "Home";
    }
}

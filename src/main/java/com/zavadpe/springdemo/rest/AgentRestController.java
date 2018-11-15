package com.zavadpe.springdemo.rest;

import com.zavadpe.springdemo.errors.EntityIdNotFound;
import com.zavadpe.springdemo.model.Agent;
import com.zavadpe.springdemo.repository.AgentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class AgentRestController {

    @Autowired
    AgentRepository agentRepository;

    @RequestMapping(method = RequestMethod.GET)
    Collection<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{agentId}")
    Agent getAgentById(@PathVariable Long agentId) {
        final Agent agent = agentRepository.findById(agentId).orElseThrow(() -> new EntityIdNotFound("Agent", agentId));
        return agent;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{agentId}")
    ResponseEntity<?> editAgentById(@PathVariable Long agentId, @RequestBody Agent sourceAgent) {
        final Agent targetAgent = agentRepository.findById(agentId).orElseThrow(() -> new EntityIdNotFound("Agent", agentId));
        BeanUtils.copyProperties(sourceAgent, targetAgent, "id");
        return ResponseEntity.ok(agentRepository.save(targetAgent));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{agentId}")
    ResponseEntity<?> deleteAgentById(@PathVariable Long agentId) {
        final Agent deletedAgent = agentRepository.findById(agentId).orElseThrow(() -> new EntityIdNotFound("Agent", agentId));
        agentRepository.delete(deletedAgent);
        return new ResponseEntity<>("Agent successfully deleted", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addAgent(@RequestBody Agent agent) {
        List<Agent> duplicates = agentRepository.findDuplicate(agent.getName(), agent.getAddress().getPostalCode());
        if (duplicates.size() > 0) {
            return new ResponseEntity<>("Trying to create duplicate Agent!", HttpStatus.CONFLICT);
        }
        if ((agentRepository.save(agent)) != null) {
            return new ResponseEntity<>("Agent successfully created", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error creating agent!", HttpStatus.BAD_REQUEST);
    }
}

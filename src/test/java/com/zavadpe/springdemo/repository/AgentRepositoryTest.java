package com.zavadpe.springdemo.repository;

import com.zavadpe.springdemo.model.Address;
import com.zavadpe.springdemo.model.Agent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AgentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AgentRepository agentRepository;

    private final Agent agent = new Agent("Agent name", new Address("City", "Street", "2", "10001"));

    @Before
    public void setup() {
        entityManager.persist(agent);
        entityManager.flush();
    }

    @Test
    public void findByNameTest() {
        List<Agent> foundByName = agentRepository.findByName(agent.getName());

        assertThat(foundByName.size()).isEqualTo(1);
        assertThat(foundByName.get(0).getName()).isEqualTo(agent.getName());
    }

    @Test
    public void findDuplicateTest() {
        List<Agent> found = agentRepository.findDuplicate(agent.getName(), agent.getAddress().getPostalCode());

        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0)).isEqualTo(agent);
    }

    @Test
    public void findByPostalCodeTest() {
        List<Agent> found = agentRepository.findAgentByPostalCode(agent.getAddress().getPostalCode());

        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0)).isEqualTo(agent);
    }
}

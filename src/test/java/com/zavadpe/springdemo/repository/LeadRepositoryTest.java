package com.zavadpe.springdemo.repository;

import com.zavadpe.springdemo.model.Address;
import com.zavadpe.springdemo.model.Lead;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LeadRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private LeadRepository leadRepository;

    private final Lead lead = new Lead("John", "Smith", "jsmith@gmail.com", "+421999888777", new Address("Washington", "Kennedy st.", "9", "10001"), new Date(82, 1, 1), true);

    @Before
    public void setup() {
        entityManager.persist(lead);
        entityManager.flush();
    }

    @Test
    public void findByNameTest() {
        List<Lead> foundByName = leadRepository.findByName(lead.getName());
        List<Lead> foundBySurname = leadRepository.findByName(lead.getSurname());

        assertThat(foundByName.size()).isEqualTo(1);
        assertThat(foundByName.get(0).getName()).isEqualTo(lead.getName());
        assertThat(foundBySurname.size()).isEqualTo(1);
        assertThat(foundBySurname.get(0).getName()).isEqualTo(lead.getName());
    }

    @Test
    public void findDuplicateTest() {
        List<Lead> found = leadRepository.findDuplicate(lead.getName(), lead.getSurname(), lead.getBorn(), lead.getAddress().getPostalCode());

        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0)).isEqualTo(lead);
    }

    @Test
    public void findLeadsByAgentIdTest() {
        List<Lead> found = leadRepository.findLeadsByAgentId(4L);
        // there's no Distribution
        assertThat(found.size()).isEqualTo(0);
    }
}

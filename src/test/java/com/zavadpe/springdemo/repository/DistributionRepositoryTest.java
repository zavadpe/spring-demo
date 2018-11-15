package com.zavadpe.springdemo.repository;

import com.zavadpe.springdemo.model.Distribution;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DistributionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DistributionRepository distributionRepository;

    private final Distribution distribution = new Distribution(1L, 4L);

    @Before
    public void setup() {
        entityManager.persist(distribution);
        entityManager.flush();
    }

    @Test
    public void findByIdTest() {
        List<Distribution> foundByLeadId = distributionRepository.findByLeadId(distribution.getLeadId());
        List<Distribution> foundByAgentId = distributionRepository.findByAgentId(distribution.getAgentId());

        assertThat(foundByLeadId.size()).isEqualTo(1);
        assertThat(foundByAgentId.size()).isEqualTo(1);
        assertThat(foundByLeadId.get(0)).isEqualTo(distribution);
        assertThat(foundByAgentId.get(0)).isEqualTo(distribution);
    }
}

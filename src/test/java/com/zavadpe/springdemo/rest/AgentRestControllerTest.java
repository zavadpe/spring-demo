package com.zavadpe.springdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zavadpe.springdemo.db.TestData;
import com.zavadpe.springdemo.model.Agent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(AgentRestController.class)
public class AgentRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AgentRestController agentRestController;

    private JacksonTester<List<Agent>> jsonAgent;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getAgents() throws Exception {
        given(agentRestController.getAllAgents()).willReturn(TestData.agents);
        MockHttpServletResponse response = mvc.perform(get("/api/agents").contentType(APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonAgent.write(TestData.agents).getJson());
    }
}

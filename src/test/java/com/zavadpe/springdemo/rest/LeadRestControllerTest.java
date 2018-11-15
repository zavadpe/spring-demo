package com.zavadpe.springdemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zavadpe.springdemo.db.TestData;
import com.zavadpe.springdemo.model.Lead;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(LeadRestController.class)
public class LeadRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LeadRestController leadRestController;

    private JacksonTester<List<Lead>> jsonLead;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getLeads() throws Exception {
        given(leadRestController.getAllLeads()).willReturn(TestData.leads);
        MockHttpServletResponse response = mvc.perform(get("/api/leads").contentType(APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonLead.write(TestData.leads).getJson());
    }
}

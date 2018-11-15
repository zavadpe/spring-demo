package com.zavadpe.springdemo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Distribution {

    @Id
    @GeneratedValue
    protected Long id;
    @NotNull
    protected Long leadId;
    @NotNull
    protected Long agentId;

    public Distribution() {}

    public Distribution(Long leadId, Long agentId) {
        this.leadId = leadId;
        this.agentId = agentId;
    }
}

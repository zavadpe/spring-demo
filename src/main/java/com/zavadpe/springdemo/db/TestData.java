package com.zavadpe.springdemo.db;

import com.zavadpe.springdemo.model.Address;
import com.zavadpe.springdemo.model.Agent;
import com.zavadpe.springdemo.model.Distribution;
import com.zavadpe.springdemo.model.Lead;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public final class TestData {

    public static final List<Lead> leads = Arrays.asList(
            new Lead("John", "Smith", "jsmith@gmail.com", "+421999888777", new Address("Washington", "Kennedy st.", "9", "10001"), new Date(82, 1, 1), true),
            new Lead("John", "Doe", "jdoe@gmail.co", "+421999777888", new Address("New York", "Wall st.", "12", "20001"), new Date(82, 2, 1), true),
            new Lead("Mark", "Webber", "marky@gmail.co", "+421999111000", new Address("Los Angeles", "Bouleward st.", "99", "30001"), new Date(85, 11, 25))
    );

    public static final List<Agent> agents = Arrays.asList(
            new Agent("American Ins.", new Address("Washington", "Kennedy st.", "10", "10001")),
            new Agent("First New York Insurence", new Address("New York", "Wall st.", "10", "20001")),
            new Agent("California Ins. Comp.", new Address("Los Angeles", "Beverly Hils.", "90210", "30001"))
    );

    public static final List<Distribution> distributions = Arrays.asList(
            new Distribution(1L, 4L),
            new Distribution(2L, 4L)
    );
}

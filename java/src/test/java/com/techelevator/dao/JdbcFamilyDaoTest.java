package com.techelevator.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techelevator.model.Family;
import com.techelevator.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcFamilyDaoTest extends BaseDaoTests{

    private static List<User> family;
    private static final Family FAMILY_1 = new Family(1, "Harding", family);
    //private static final Family FAMILY_1 = new Family();

    private JdbcFamilyDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcFamilyDao(jdbcTemplate);
    }

    @Test
    public void getFamilyName_returns_correct_name() {
        Family family = sut.getFamily("Harding");
        Assert.assertNotNull("getFamily returned null", family);
        assertFamiliesMatch("getFamily returned wrong or partial data", FAMILY_1, family);
    }

    @org.junit.Test
    public void getDepartment_returns_null_when_id_not_found() {
        Family family = sut.getFamily("Does Not Exist");
        Assert.assertNull("getFamily failed to return null for id not in database", family);
    }

    @Test
    public void createFamily() {
    }

    @Test
    public void updateFamily() {
    }

    @Test
    public void addFamilyMember() {
    }

    @Test
    public void removeFamilyMember() {
    }

    @Test
    public void getFamily() {
    }

    private void assertFamiliesMatch(String message, Family expected, Family actual) {
        Assert.assertEquals(message, expected.getFamilyId(), actual.getFamilyId());
        Assert.assertEquals(message, expected.getFamilyName(), actual.getFamilyName());
    }
}
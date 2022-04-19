package com.techelevator.model;

import java.util.List;

public class Family {
    private Integer familyId;
    private String familyName;
    private List<User> familyMembers;

    public Family(Integer familyId, String familyName, List<User> familyMembers) {
        this.familyId = familyId;
        this.familyName = familyName;
        this.familyMembers = familyMembers;
    }

    public Family(){
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public List<User> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<User> familyMembers) {
        this.familyMembers = familyMembers;
    }
}

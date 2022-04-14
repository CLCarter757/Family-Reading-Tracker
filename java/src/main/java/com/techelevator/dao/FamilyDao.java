package com.techelevator.dao;

import com.techelevator.model.Family;

public interface FamilyDao {
    Family createFamily(String userName, Family family) throws Exception;
    Family updateFamily(int familyId, String userName, Family family) throws Exception;
    Family addFamilyMember (String parentUserName, String childUserName) throws Exception;
    Family removeFamilyMember (String parentUserName, String childUserName);
    Family getFamily (String userName);



}

package com.techelevator.dao;

import com.techelevator.model.Family;
import com.techelevator.model.User;

public interface FamilyDao {
    Family createFamily(String userName, Family family) throws Exception;
    Family updateFamily(int familyId, String userName, Family family) throws Exception;
    Family addFamilyMember(Integer familyId, String principalName, User userToAdd) throws Exception;
    Family removeFamilyMember(Integer familyId, String principalName, User userToRemove) throws Exception;
    Family getFamily (String userName);



}

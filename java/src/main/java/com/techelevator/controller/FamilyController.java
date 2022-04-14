package com.techelevator.controller;

import com.techelevator.dao.FamilyDao;
import com.techelevator.model.Family;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
public class FamilyController {
    private final FamilyDao familyDao;

    public FamilyController(FamilyDao familyDao) {
        this.familyDao = familyDao;
    }


    //    Family createFamily(String userName, Family family) throws Exception;
//    Family updateFamily(int familyId, String userName, Family family) throws Exception;
//    Family addFamilyMember (String parentUserName, String childUserName) throws Exception;
//    Family removeFamilyMember (String parentUserName, String childUserName);
//    Family getFamily (String userName);
//
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping (value = "myfamily")
    @PreAuthorize("hasRole('ROLE_PARENT')")
    public Family createFamily(Principal principal,@Valid @RequestBody Family family) throws Exception {
       return familyDao.createFamily(principal.getName(),family);
    }

    @PutMapping (path = "myfamily/{userName}")
   public Family addFamilyMember (@Valid Principal principal,  @PathVariable String userName) throws Exception {
        return familyDao.addFamilyMember(principal.getName(),userName);

   }








}

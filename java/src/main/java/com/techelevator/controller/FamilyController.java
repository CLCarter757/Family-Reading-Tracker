package com.techelevator.controller;

import com.techelevator.dao.FamilyDao;
import com.techelevator.model.Family;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class FamilyController {
    private final FamilyDao familyDao;

    public FamilyController(FamilyDao familyDao) {
        this.familyDao = familyDao;
    }



//    @GetMapping(path = "/myfamily")
//    Family getFamily (@Valid Principal principal){
//        return familyDao.getFamily(principal.getName());
//    };
    @GetMapping(path = "/myfamily")
    public List<User> getFamilyMembers (@Valid Principal principal){
        return familyDao.getFamilyMembers(principal.getName());
    };

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping (path = "/myfamily")
    @PreAuthorize("hasRole('ROLE_PARENT')")
    public Family createFamily(@Valid Principal principal, @RequestBody Family family) throws Exception {
       return familyDao.createFamily(principal.getName(),family);
    }

    @PutMapping (path = "/myfamily/{familyId}")
    @PreAuthorize("hasRole('ROLE_PARENT')")
    public Family addFamilyMember (@Valid Principal principal,  @PathVariable Integer familyId, @RequestBody User userToAdd) throws Exception {
        return familyDao.addFamilyMember(familyId,principal.getName(),userToAdd);

   }

    @DeleteMapping (path = "/myfamily/{familyId}")
    public Family removeFamilyMember (@Valid Principal principal,  @PathVariable Integer familyId, @RequestBody User userToRemove) throws Exception {
        return familyDao.removeFamilyMember(familyId,principal.getName(),userToRemove);


    }
}

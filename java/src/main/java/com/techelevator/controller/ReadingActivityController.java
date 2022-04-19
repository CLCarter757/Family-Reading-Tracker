package com.techelevator.controller;

import com.techelevator.dao.ReadingActivityDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Family;
import com.techelevator.model.ReadingActivity;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin

public class ReadingActivityController {
    private final ReadingActivityDao readingActivityDao;
    private final UserDao userDao;

    public ReadingActivityController(ReadingActivityDao readingActivityDao, UserDao userDao) {
        this.readingActivityDao = readingActivityDao;
        this.userDao = userDao;
    }

    @GetMapping(path = "users/{id}/activities")
    List<ReadingActivity> allActivitiesByUserId(@Valid @PathVariable Long id){
        return readingActivityDao.getAllReadingActivityByUserId(id);
    }

    @GetMapping(path = "users/{id}/activities/{recordId}")
    ReadingActivity getReadingActivity(@PathVariable Long id,@PathVariable int recordId) throws Exception {
        return readingActivityDao.getReadingActivityByRecordId(id,recordId);

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "users/{id}/activities")
    ReadingActivity addReadingActivity (@Valid @PathVariable Long id ,Principal principal,@RequestBody ReadingActivity activity) throws Exception {
        ReadingActivity activityCreated=null;
        User user=userDao.getUserById(id);
        if(user.getUsername().equals(principal.getName())){
            activityCreated=readingActivityDao.addReadingActivity(activity);
        }
        return activityCreated;
    }
    @PutMapping(path = "users/{id}/activities/{recordId}")
    boolean updateReadingActivity (@Valid @PathVariable Long id ,Principal principal,@PathVariable int recordId,@RequestBody ReadingActivity activity){
       boolean isActivityUpdated=false;
        User user=userDao.getUserById(id);
        if(user.getUsername().equals(principal.getName())){
            isActivityUpdated=readingActivityDao.updateReadingActivity(activity);
        }
        return isActivityUpdated;
    }
    @DeleteMapping(path = "users/{id}/activities/{recordId}")
    boolean removeReadingActivity(@ Valid @PathVariable Long id, @PathVariable int recordId,Principal principal){
        boolean isActivityRemoved=false;
        User user=userDao.getUserById(id);
        if(user.getUsername().equals(principal.getName())){
            isActivityRemoved=readingActivityDao.removeReadingActivity(recordId);
        }
        return isActivityRemoved;
    }
}

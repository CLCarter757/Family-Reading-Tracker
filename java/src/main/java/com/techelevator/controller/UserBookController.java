package com.techelevator.controller;

import com.techelevator.model.LoginDTO;
import com.techelevator.model.UserBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class UserBookController {

    @RequestMapping(value = "/mybooks", method = RequestMethod.POST)
    public boolean addBook(@Valid @RequestBody UserBook userBook, Principal principal) {
        return false;
    }

    @RequestMapping(value = "/mybooks", method = RequestMethod.GET)
    public List<UserBook> listMyBooks(Principal principal) {
        return null;
    }

    @RequestMapping(value = "/mybooks/{isbn}", method = RequestMethod.GET)
    public UserBook getMyBook(Principal principal) {
        return null;
    }

    @RequestMapping(value = "/mybooks/{isbn}", method = RequestMethod.DELETE)
    public boolean deleteMyBook(Principal principal) {
        return false;
    }

    @RequestMapping(value = "/mybooks/{isbn}", method = RequestMethod.PUT)
    public UserBook updateMyBook(@Valid @RequestBody UserBook userBook, Principal principal) {
        return null;
    }






















}

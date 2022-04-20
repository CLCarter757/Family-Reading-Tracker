package com.techelevator.controller;

import com.techelevator.dao.UserBookDao;
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
    private final UserBookDao userBookDao;

    public UserBookController(UserBookDao userBookDao) {
        this.userBookDao = userBookDao;
    }


    @RequestMapping(value = "/mybooks", method = RequestMethod.POST)
    public Integer addBook(@Valid @RequestBody UserBook userBook, Principal principal) {
        return userBookDao.addBook(userBook, principal.getName());
    }

    @RequestMapping(value = "/mybooks", method = RequestMethod.GET)
    public List<UserBook> listMyBooks(Principal principal) {
        return userBookDao.getUserBookList(principal.getName());
    }

    @RequestMapping(value = "/myfamily/books", method = RequestMethod.GET)
    public List<UserBook> listFamilyBooks(Principal principal) {
        return userBookDao.getFamilyBooks(principal.getName());
    }

    @RequestMapping(value = "/mybooks/{UserBookId}", method = RequestMethod.GET)
    public UserBook getMyBook(Principal principal, @PathVariable Integer UserBookId) {

        return userBookDao.getBookByBookId(UserBookId);
    }







    @RequestMapping(value = "/mybooks/{isbn}", method = RequestMethod.DELETE)
    public boolean deleteMyBook(Principal principal, @PathVariable String isbn) throws Exception {
        return userBookDao.deleteMyBook(principal.getName(), isbn);
    }

    @RequestMapping(value = "/mybooks/{isbn}", method = RequestMethod.PUT)
    public UserBook updateMyBook(@Valid @RequestBody UserBook userBook, Principal principal, @PathVariable String isbn) {
        return userBookDao.updateMyBook(userBook, principal.getName(), isbn);
    }






















}

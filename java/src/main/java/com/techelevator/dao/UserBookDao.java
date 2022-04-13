package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.Family;
import com.techelevator.model.UserBook;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

public interface UserBookDao {

    Integer addBook(UserBook book, String username);

    List<UserBook> getUserBookList(String username);

    UserBook getMyBook(String username, Integer isbn);

    boolean deleteMyBook(String username, Integer isbn) throws Exception;

    UserBook updateMyBook(UserBook book, String username, Integer isbn);

}

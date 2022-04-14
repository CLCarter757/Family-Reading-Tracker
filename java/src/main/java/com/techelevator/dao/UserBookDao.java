package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.Family;
import com.techelevator.model.UserBook;

import java.util.List;

public interface UserBookDao {


    Integer addBook(String username, UserBook book);

    Book getBook(int isbn);

    List<UserBook> getAllBooksByUsername(String username);

    List<Book> getAllBooksByFamilyId(Family family);

    UserBook getBookByUserId(String username, int isbn);

    Book getBookByIsbn(int isbn);
}

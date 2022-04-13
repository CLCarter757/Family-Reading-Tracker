package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.Family;
import com.techelevator.model.UserBook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcBookDao implements BookDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcBookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book addBook(String username, Book book) {
        String sql = "INSERT INTO book (isbn, title) " +
                     "VALUES (?, ?);";

        //SqlRowSet results = jdbcTemplate.queryForObject(sql);

        return null;
    }

    @Override
    public Book getBook(int isbn) {
        Book book = null;

        String sql = "SELECT isbn, title " +
                     "FROM book " +
                     "WHERE isbn = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, isbn);

        if(results.next()) {
            book = mapRowToBook(results);
        } else {
            //throw new BookNotFoundException();
        }

        return book;
    }

    @Override
    public List<UserBook> getAllBooksByUsername(String username) {
        List<UserBook> books = null;

        String sql = "SELECT user_id, first_name.users AS first_name, last_name.users AS last_name, isbn, title.book AS title, minutes, completed " +
                     "FROM user_book " +
                     "JOIN users USING (user_id) " +
                     "JOIN book USING (isbn) " +
                     "WHERE user_id = (SELECT user_id FROM users WHERE username = ?);";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

        while(results.next()) {
            UserBook book = mapRowToUserBook(results);
            books.add(book);
        }

        return books;
    }

    @Override
    public List<Book> getAllBooksByFamilyId(Family family) {
        List<Book> books = null;

        String sql = "SELECT user_id, first_name.users AS first_name, last_name.users AS last_name, isbn, title.book AS title, minutes, completed " +
                     "FROM user_book " +
                     "JOIN users USING (user_id) " +
                     "JOIN book USING (isbn) " +
                     "WHERE user_id = (SELECT user_id FROM users WHERE username = ?);";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()) {
            Book book = mapRowToBook(results);
            books.add(book);
        }

        return books;
    }

    @Override
    public UserBook getBookByUserId(String username, int isbn) {
        return null;
    }

    @Override
    public Book getBookByIsbn(int isbn) {
        return null;
    }

    private Book mapRowToBook(SqlRowSet results){
        Book book = new Book();
        List<String> authors = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        book.setIsbn(results.getInt("isbn"));
        book.setTitle(results.getString("title"));
//        authors.add(results.getString("authors"));
//        book.setAuthors(authors);
//        genres.add(results.getString("genres"));
//        book.setGenres(genres);
        return book;
    };

    private UserBook mapRowToUserBook(SqlRowSet results){
        UserBook userbook = new UserBook();
        userbook.setUserId(results.getInt("user_id"));
        userbook.setFirstName(results.getString("first_name"));
        userbook.setLastName(results.getString("last_name"));
        userbook.setIsbn(results.getInt("isbn"));
        userbook.setTitle(results.getString("title"));
        userbook.setMinutes(results.getInt("minutes"));
        userbook.setCompleted(results.getBoolean("completed"));
        return userbook;
    };

}

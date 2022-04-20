package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.Family;
import com.techelevator.model.UserBook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserBookDao implements UserBookDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcUserBookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer addBook(UserBook book, String username) {

        String sql = "INSERT INTO user_book (user_id, isbn, authors, title, book_cover_url, description) " +
                "VALUES ((SELECT user_id FROM users WHERE username = ?), ?, ?, ?, ?, ?) " +
                "RETURNING user_book_id;";

        Integer userBookId = jdbcTemplate.queryForObject(sql, Integer.class, book.getUsername(), book.getIsbn(),
                book.getAuthors(), book.getTitle(), book.getBookCoverUrl(), book.getDescription());

        return userBookId;
    }

    @Override
    public List<UserBook> getUserBookList(String username) {
        List<UserBook> books = new ArrayList<>();
        String sql = "SELECT user_book_id, user_id, isbn, title, minutes, completed, favorited, rating, " +
                "authors, description, book_cover_url, users.username AS username " +
                "FROM user_book " +
                "JOIN users USING (user_id) " +
                "WHERE users.username = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

        while(results.next()) {
            UserBook book = mapRowToUserBook(results);
            books.add(book);
        }
        return books;
    }

    @Override
    public List<UserBook> getFamilyBooks(String username) {
        String sqlFamilyId = "SELECT family_id " +
                            "FROM users " +
                            "WHERE username = ?;";

        Integer familyId = jdbcTemplate.queryForObject(sqlFamilyId, Integer.class, username);

        List<UserBook> books = new ArrayList<>();
        String sql = "SELECT user_book_id, user_id, isbn, title, minutes, completed, favorited, rating, " +
                "authors, description, book_cover_url, users.username AS username " +
                "FROM user_book " +
                "JOIN users USING (user_id) " +
                "WHERE users.family_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, familyId);

        while(results.next()) {
            UserBook book = mapRowToUserBook(results);
            books.add(book);
        }
        return books;
    }

    @Override
    public UserBook getBookByBookId(int userBookId) {
        UserBook book = new UserBook();
        String sql = "SELECT user_book_id, user_id, isbn, title, minutes, completed, " +
                "authors, description, book_cover_url, favorited, rating " +
                "FROM user_book " +
                "WHERE user_book_id=?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userBookId);

        if (results.next()) {
            book = mapRowToUserBook(results);
        }
        return book;

    }

    @Override
    public UserBook getMyBook(String username, String isbn) {
        UserBook book = new UserBook();
        String sql = "SELECT user_book_id, user_id, isbn, title, minutes, completed, " +
                "authors, description, book_cover_url, favorited, rating " +
                "FROM user_book " +
                "WHERE user_id = (SELECT user_id FROM users WHERE username = ?) " +
                "AND isbn = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username, isbn);

        if (results.next()) {
            book = mapRowToUserBook(results);
        }
        return book;
    }

    @Override
    public boolean deleteMyBook(String username, String isbn) throws Exception {
        String sql = "DELETE FROM user_book " +
                "WHERE isbn = ? AND user_id = (SELECT user_id FROM users WHERE username = ?);";

        try {
            jdbcTemplate.update(sql, isbn, username);
            return true;
        } catch (Exception e) {
            throw new Exception("error! book not deleted");
        }
    }

    @Override
    public UserBook updateMyBook(UserBook book, String username, String isbn) {
        String sql = "UPDATE user_book " +
                "SET minutes = ?, " +
                "completed = ?, " +
                "favorited = ?, " +
                "rating = ? " +
                "WHERE isbn = ? AND user_id = (SELECT user_id FROM users WHERE username = ?);";

            jdbcTemplate.update(sql, book.getMinutes(), book.isCompleted(), book.isFavorited(), book.getRating(), isbn, username);
            return getMyBook(username, isbn);
    }



    /*
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

     */

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
    }

    private UserBook mapRowToUserBook(SqlRowSet results){
        UserBook userbook = new UserBook();
        userbook.setAuthors(results.getString("authors"));
        userbook.setUserBookId(results.getInt("user_book_id"));
        userbook.setUserId(results.getInt("user_id"));
        userbook.setIsbn(results.getString("isbn"));
        userbook.setTitle(results.getString("title"));
        userbook.setMinutes(results.getInt("minutes"));
        userbook.setCompleted(results.getBoolean("completed"));
        userbook.setFavorited(results.getBoolean("favorited"));
        userbook.setBookCoverUrl(results.getString("book_cover_url"));
        userbook.setDescription(results.getString("description"));
        userbook.setRating(results.getInt("rating"));
        return userbook;
    }

}

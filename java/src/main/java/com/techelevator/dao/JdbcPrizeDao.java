package com.techelevator.dao;

import com.techelevator.model.Prize;
import com.techelevator.model.ReadingActivity;
import com.techelevator.model.User;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcPrizeDao implements PrizeDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcPrizeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Prize> listPrizesByFamily(String username) {
        List<Prize> prizes = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM prizes " +
                "WHERE family_id = (SELECT family_id FROM users WHERE username = ?);";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while(results.next()) {
            Prize prize = mapRowToPrize(results);
            List<User> winners = calculateWinners(prize, username);
            prize.setWinners(winners);
            prizes.add(prize);
        }
        return prizes;
    }

    @Override
    public Prize getPrizeById(String username, Integer prizeId) throws Exception {
        String sql = "SELECT * " +
                "FROM prizes " +
                "WHERE prizeId = ? AND familyId = (SELECT familyId FROM users WHERE username = ?)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, prizeId, username);
        if (results.next()) {
            Prize prize = mapRowToPrize(results);
            List<User> winners = calculateWinners(prize, username);
            prize.setWinners(winners);
            return prize;
        }
        throw new Exception("could not find prize");
    }


    @Override
    public Prize createPrize(String username, Prize prize) throws Exception {
        String sql = "INSERT INTO prizes (family_id, prize_name, description, milestone, user_group, max_prizes, start_date, end_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING prize_id;";
        Integer prizeId = jdbcTemplate.queryForObject(sql, Integer.class, prize.getFamilyId(), prize.getName(), prize.getDescription(),
        prize.getMilestone(), prize.getUserGroup(), prize.getMaxPrizes(), prize.getStartDate(), prize.getEndDate());
        return getPrizeById(username, prizeId);
    }

    @Override
    public Prize updatePrize(String username, Integer prizeId, Prize prize) throws Exception {
        String sql = "UPDATE prizes " +
                "SET prize_name = ?, description = ?" +
                "WHERE prize_id = ?";
        jdbcTemplate.update(sql, prize.getName(), prize.getDescription(), prizeId);
        return getPrizeById(username, prizeId);
    }

    @Override
    public boolean deletePrize(String username, Integer prizeId) throws Exception {
        String sql = "DELETE FROM prizes " +
                "WHERE prize_id = ? AND family_id = (SELECT family_id FROM users WHERE username = ?);";
        try {
            jdbcTemplate.update(sql, prizeId, username);
            return true;
        } catch (Exception e) {
            throw new Exception("prize not found, not deleted.");
        }
    }

    private Prize mapRowToPrize(SqlRowSet results) {
        Prize prize = new Prize();
        prize.setPrizeId(results.getInt("prize_id"));
        prize.setFamilyId(results.getInt("family_id"));
        prize.setName(results.getString("prize_name"));
        prize.setDescription(results.getString("description"));
        prize.setMilestone(results.getInt("milestone"));
        prize.setUserGroup(results.getString("user_group"));
        prize.setMaxPrizes(results.getInt("max_prizes"));

        Date startDate = results.getDate("start_date");
        if (startDate != null) {
            prize.setStartDate(startDate.toLocalDate());
        }
        Date endDate = results.getDate("end_date");
        if (endDate != null) {
            prize.setEndDate(endDate.toLocalDate());
        }
        return prize;
    }

    private List<User> calculateWinners(Prize prize, String username) {
        List<User> winners = new ArrayList<>();
        JdbcFamilyDao jdbcFamilyDao = new JdbcFamilyDao(jdbcTemplate);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(jdbcTemplate);
        List<User> familyMembers = jdbcFamilyDao.getFamily(username).getFamilyMembers();
        Map<Long, Long> familyMap = new HashMap<>();
        for (User user : familyMembers) {
            familyMap.put(user.getId(), 0L);
        }
        //get list of ReadingActivity for whole family
        //include reading activity between start and end dates
        List<ReadingActivity> familyActivities= new ArrayList<>();
        String sql = "SELECT * " +
                "FROM reading_activity " +
                "WHERE reader IN (SELECT user_id FROM users WHERE family_id = ?) AND " +
                "date >= ? AND date <= ? " +
                "ORDER BY date";

        SqlRowSet results= jdbcTemplate.queryForRowSet(sql, prize.getFamilyId(), prize.getStartDate(),
                prize.getEndDate());
        while(results.next()){
            familyActivities.add(mapToReadingActivity(results));
        }
        int i = 0;
        while (winners.size() < prize.getMaxPrizes() && familyActivities.size() <= i+1) {
            Long time = familyActivities.get(i).getTime();
            Long userId = (long)(familyActivities.get(i).getReader());
            time += familyMap.get(userId);

            if (time >= prize.getMilestone()) {
                if (!winners.contains(jdbcUserDao.getUserById(userId))) {
                    winners.add(jdbcUserDao.getUserById(userId));
                }
            }
            i += 1;
        }

        String userSql = "SELECT users.* " +
                "FROM user_prizes " +
                "JOIN users USING (user_id) " +
                "WHERE prize_id = ?;";
//        user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
//        username varchar(50) NOT NULL UNIQUE,
//        first_name varchar(50),
//        last_name varchar(50),
//        password_hash varchar(200) NOT NULL,
//        role varchar(50) NOT NULL,
//
//        family_id int,





        //check what user ids are in database
        //compare to winners list
        //if winner not in database, add them


        //MAYBE MAKES MORE SENSE TO
        //CHECK FOR WINNERS WHENEVER ADDING A READING ACTIVITY
        //ADD THAT USER TO USER_PRIZE TABLE IF THEY WIN
        //THIS FUNCTION JUST PULLS FROM USER_PRIZE TABLE AND ADDS WINNERS TO LIST

        //sum minutes read per user
        //iterate over reading activity, earliest to latest
        //if the user minutes is greater than milestone, add to winners list
        //if maxPrizes == winners list size, stop iterating
        //return winners list
        return winners;
    }

    private ReadingActivity mapToReadingActivity(SqlRowSet result){
        ReadingActivity readingActivity= new ReadingActivity();

        readingActivity.setRecordId(result.getInt("record_id"));
        readingActivity.setUserBookId(result.getInt("user_book_id"));
        readingActivity.setDateCreated((result.getDate("date")).toLocalDate());
        readingActivity.setReader(result.getInt("reader"));
        readingActivity.setFormat(result.getString("format"));
        readingActivity.setTime(result.getLong("minutes"));
        readingActivity.setNotes(result.getString("notes"));
        return readingActivity;

    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setAuthorities(rs.getString("role"));
        user.setFamilyRole(rs.getString("role"));
        user.setFamilyId(rs.getInt("family_id"));

        return user;
    }

}

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
                "WHERE prize_id = ? AND family_id = (SELECT family_id FROM users WHERE username = ?)";
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
            if ((user.getFamilyRole().equals("ROLE_PARENT") && (prize.getUserGroup().equals("all") || prize.getUserGroup().equals("parents")))
            || (user.getFamilyRole().equals("ROLE_CHILD") && (prize.getUserGroup().equals("all") || prize.getUserGroup().equals("children"))))
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
        //winners.size() < prize.getMaxPrizes() && familyActivities.size() < i
        while (true) {
            if (familyActivities.size() == 0) {
                break;
            }
            Long time = familyActivities.get(i).getTime();
            Long userId = (long)(familyActivities.get(i).getReader());
            time += familyMap.get(userId);

            if (time >= prize.getMilestone()) {
                if (!winners.contains(jdbcUserDao.getUserById(userId))) {
                    winners.add(jdbcUserDao.getUserById(userId));
                }
            }
            i += 1;
            if (i == familyActivities.size()) {
                break;
            }
            if (winners.size() == prize.getMaxPrizes()) {
                break;
            }
        }

//        List<User> winnersInDb = new ArrayList<>();
//        String userSql = "SELECT users.* " +
//                "FROM user_prizes " +
//                "JOIN users USING (user_id) " +
//                "WHERE prize_id = ?;";
//        SqlRowSet resultsWinnerDb = jdbcTemplate.queryForRowSet(userSql, prize.getPrizeId());
//        while(results.next()) {
//            User user = mapRowToUser(results);
//            winnersInDb.add(user);
//       }
        //***do we even need the user_prizes table?
        //what is the point, if we can just calculate the winners based on the reading activity
        //and the prize tables?
        //user_prize table seems like extra, useless data we don't need to store***
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

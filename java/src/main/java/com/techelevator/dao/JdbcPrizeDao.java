package com.techelevator.dao;

import com.techelevator.model.Prize;
import com.techelevator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
            List<User> winners = calculateWinners(prize);
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
            List<User> winners = calculateWinners(prize);
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

    private List<User> calculateWinners(Prize prize) {
        List<User> winners = new ArrayList<>();
        //get list of ReadingActivity for whole family
        //include reading activity between start and end dates
        //sum minutes read per user
        //iterate over reading activity, earliest to latest
        //if the user minutes is greater than milestone, add to winners list
        //if maxPrizes == winners list size, stop iterating
        //return winners list
        return winners;
    }

}

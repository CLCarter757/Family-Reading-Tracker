package com.techelevator.dao;

import com.techelevator.model.ReadingActivity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
@Component

public class JdbcReadingActivity implements ReadingActivityDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcReadingActivity(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReadingActivity getReadingActivityByRecordId(Long userId,int recordId) throws Exception {

       try {
           ReadingActivity expected = null;
           String sql = "SELECT * FROM reading_activity JOIN users " +
                   "ON user_id=reader WHERE record_id=? AND user_id=?";
           SqlRowSet result = jdbcTemplate.queryForRowSet(sql, recordId, userId);
           if (result.next()) {
               expected = mapToReadingActivity(result);
           }

           return expected;
       }catch(Exception e){
           throw new Exception("Not Found");


           }
    }

    @Override
    public ReadingActivity addReadingActivity(ReadingActivity activity) throws Exception {
        ReadingActivity createdReadingActivity=null;
        boolean isUserBookTableUpdated=jdbcTemplate.update("UPDATE  user_book SET minutes=minutes+ (?) WHERE user_book_id=?",activity.getTime(),activity.getUserBookId())==1;

        Integer recordId=null;
        if(isUserBookTableUpdated){
        String sql="INSERT INTO reading_activity (user_book_id,date,reader,format,minutes,notes) " +
                "VALUES (?,?,?,?,?,?) returning record_id";

      //  Date date = Date.valueOf(LocalDate.now());
        Date date = Date.valueOf(activity.getDateCreated());
         recordId = jdbcTemplate.queryForObject(sql,Integer.class,activity.getUserBookId(),date,activity.getReader(),
                activity.getFormat(),activity.getTime(),activity.getNotes());}

        return getReadingActivityByRecordId((long) activity.getReader(),recordId);
    }

    @Override
    public List<ReadingActivity> getAllReadingActivityByUserId(Long userId) {
        List<ReadingActivity> allActivities=new ArrayList<>();
        String sql="SELECT * FROM reading_activity WHERE reader=?";
        SqlRowSet results= jdbcTemplate.queryForRowSet(sql,userId);
        while(results.next()){
            allActivities.add(mapToReadingActivity(results));
        }
        return allActivities;
    }

    @Override
    public boolean updateReadingActivity(ReadingActivity activity) {

        String sql="UPDATE reading_activity SET format=?, minutes=?,notes=? WHERE record_id=?";
        return jdbcTemplate.update(sql,activity.getFormat(),activity.getTime(),activity.getNotes(),activity.getRecordId())==1;


    }

    @Override
    public boolean removeReadingActivity(int recordId) {

        String sql="DELETE FROM reading_activity WHERE record_id=?";
        return jdbcTemplate.update(sql,recordId)==1;

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
}

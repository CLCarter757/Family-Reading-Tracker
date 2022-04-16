package com.techelevator.dao;

import com.techelevator.model.ReadingActivity;

import java.util.List;

public interface ReadingActivityDao {
    ReadingActivity getReadingActivityByRecordId (Long userId,int recordId) throws Exception;
    ReadingActivity addReadingActivity (ReadingActivity readingActivity) throws Exception;
    List<ReadingActivity> getAllReadingActivityByUserId (Long userId);
    boolean updateReadingActivity (ReadingActivity activity);
    boolean removeReadingActivity (int recordId);

}

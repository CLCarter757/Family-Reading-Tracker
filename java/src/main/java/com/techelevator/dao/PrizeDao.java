package com.techelevator.dao;

import com.techelevator.model.Prize;

import java.util.List;

public interface PrizeDao {

    List<Prize> listPrizesByFamily(String username);

    Prize getPrizeById(String username, Integer prizeId) throws Exception;

    Prize createPrize(String username, Prize prize) throws Exception;

    Prize updatePrize(String username, Integer prizeId, Prize prize) throws Exception;

    boolean deletePrize(String username, Integer prizeId) throws Exception;



}

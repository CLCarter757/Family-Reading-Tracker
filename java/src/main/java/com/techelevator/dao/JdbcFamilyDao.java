package com.techelevator.dao;

import com.techelevator.model.Family;
import com.techelevator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component

public class JdbcFamilyDao implements FamilyDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcFamilyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Family createFamily(String userName,Family family) throws Exception {
        String sql="SELECT family_id FROM users WHERE username=?";
        Integer checkFamilyId = jdbcTemplate.queryForObject(sql, Integer.class, userName);
        if (checkFamilyId == null) {
            String sqlForFamilyId="INSERT INTO family_account (family_name) VALUES (?) RETURNING family_id";
            Integer familyId= jdbcTemplate.queryForObject(sqlForFamilyId,Integer.class,family.getFamilyName());
            family.setFamilyId(familyId);
            String sqlUpdateUser="UPDATE users SET family_id=?  WHERE username=?";
            jdbcTemplate.update(sqlUpdateUser, familyId, userName);
            return getFamily(userName);
        }
        throw new Exception("error");

    }


    @Override
    public Family updateFamily(int familyId, String userName, Family family) throws Exception {
        String sql="SELECT family_id FROM users WHERE username=? AND role='USER_PARENT'";
        Integer checkFamilyId = jdbcTemplate.queryForObject(sql,Integer.class,userName);
        if(checkFamilyId!=null){

            String sqlForFamilyId="UPDATE family_account SET family_name= ?  WHERE family_id =? RETURNING family_id";
            Integer idFromDB= jdbcTemplate.queryForObject(sqlForFamilyId,Integer.class,family.getFamilyName(),familyId);
            family.setFamilyId(idFromDB);
            return family;
        }

        throw new Exception("error");
    }

    @Override
    public Family addFamilyMember(Integer familyId, String principalName, User userToAdd) throws Exception {
        String sql="SELECT family_id FROM users WHERE username=?";
        Integer checkFamilyId = jdbcTemplate.queryForObject(sql,Integer.class,principalName);
        if(checkFamilyId!=null && familyId.equals(checkFamilyId)){

            String sqlForFamilyId="UPDATE users SET family_id=?  WHERE username=?";
             jdbcTemplate.update(sqlForFamilyId,checkFamilyId,userToAdd.getUsername());


            return getFamily(principalName);
        }
        throw new Exception("error");
    }

    @Override
    public Family removeFamilyMember(Integer familyId, String principalName, User userToRemove) throws Exception {
        String sql="SELECT family_id FROM users WHERE username=?";
        Integer checkFamilyId = jdbcTemplate.queryForObject(sql,Integer.class,principalName);
        if(checkFamilyId!=null && familyId.equals(checkFamilyId)){

            String sqlForFamilyId="UPDATE users SET family_id=?  WHERE username=?";
            jdbcTemplate.update(sqlForFamilyId,null,userToRemove.getUsername());


            return getFamily(principalName);
        }
        throw new Exception("error");

    }

    @Override
    public Family getFamily(String userName) {
        String sql="SELECT family_id FROM users WHERE username=? ";
        Integer checkFamilyId = jdbcTemplate.queryForObject(sql,Integer.class,userName);
        String sqlForFamilyName="SELECT family_name FROM family_account WHERE family_id=? ";
        String checkFamilyName = jdbcTemplate.queryForObject(sqlForFamilyName,String.class,checkFamilyId);


        String userSql = "select * from users WHERE family_id=?";
        SqlRowSet results= jdbcTemplate.queryForRowSet(userSql,checkFamilyId);
        List<User> listUsers=new ArrayList<>();
        while(results.next()){
            User user = mapRowToUser(results);
            listUsers.add(user);
        }
        Family family =new Family();
        family.setFamilyId(checkFamilyId);
        family.setFamilyName(checkFamilyName);
        family.setFamilyMembers(listUsers);


        return family;
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

package com.dc.mapper;

import com.dc.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface UserDao {

    public List<User> login(@Param("user_account") String user_account, @Param("user_password") String user_password) throws SQLException;

    public int register(@Param("user_account") String user_account, @Param("user_password") String user_password) throws SQLException;

    public List<User> isExistAccount(@Param("user_account") String user_account) throws SQLException;

    public List<User> isExistId(@Param("user_id") int user_id) throws SQLException;

    public List<User> userList() throws SQLException;

    public User selectUserId(@Param("user_id") int user_id) throws SQLException;

    public int insertUserByCsv(@Param("user_id") String user_id,@Param("user_account") String user_account) throws SQLException;
}
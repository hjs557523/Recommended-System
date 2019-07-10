package com.dc.service;

import com.dc.base.pojo.BaseModel;

/**
 * 定义用户接口
 */
public interface UserService {
    public BaseModel login(String user_account, String user_password);

    public BaseModel register(String user_account, String user_password);

    public BaseModel isexist(String user_account);

    public BaseModel isExistId(int user_id);

    public BaseModel userList();

    public BaseModel insertUserByCsv();
}


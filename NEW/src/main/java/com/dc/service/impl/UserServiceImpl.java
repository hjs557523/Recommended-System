package com.dc.service.impl;

import com.dc.base.pojo.BaseModel;
import com.dc.mapper.UserDao;
import com.dc.pojo.User;
import com.dc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public BaseModel insertUserByCsv() {
        BaseModel baseModel = new BaseModel();
        File csv = new File("D:\\Documents\\Desktop\\666\\人生地图\\user.csv");  // CSV文件路径
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(csv));
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(in, "utf-8"));

            String line = "";
            String everyLine = "";
            List<String> allString = new ArrayList<String>();


            while ((line = br.readLine()) != null)  //读取到的内容给line变量
            {
                everyLine = line;
                try {
                    userDao.insertUserByCsv(everyLine.split(",")[0].substring(0, everyLine.split(",")[0].length() - 2), everyLine.split(",")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                allString.add(everyLine);
            }

            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("csv表格中所有行数：" + allString.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baseModel;
    }

    public BaseModel userList() {
        BaseModel baseModel = new BaseModel();
        try {
            List<User> users = userDao.userList();
            baseModel.setResultCode(0);//如果为0则表示成功
            baseModel.setMessage("查询用户列表成功！");
            baseModel.setData(users);
            return baseModel;
        } catch (Exception e) {
            e.printStackTrace();
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("数据错误！");
            return baseModel;
        }
    }

    public BaseModel isExistId(int user_id) {
        BaseModel baseModel = new BaseModel();
        try {
            if (user_id <= 0) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (userDao.isExistId(user_id).size() <= 0) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("用户不存在！");
                return baseModel;
            }
            baseModel.setResultCode(0);//如果为0则表示成功
            baseModel.setMessage("用户存在！");
            return baseModel;
        } catch (Exception e) {
            e.printStackTrace();
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("数据错误！");
            return baseModel;
        }
    }

    public BaseModel register(String user_account, String user_password) {
        BaseModel baseModel = new BaseModel();
        try {
            if (user_account == null) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (user_account.equals("")) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (user_password == null) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (user_password.equals("")) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (isexist(user_account).getResultCode() == 2) {
                baseModel.setResultCode(2);//如果为0则表示成功
                baseModel.setMessage("用户已存在！");
                return baseModel;
            } else if (isexist(user_account).getResultCode() == 1) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("数据异常！");
                return baseModel;
            } else {
                int num = userDao.register(user_account, user_password);
                if (num <= 0) {
                    baseModel.setResultCode(1);//如果为0则表示成功
                    baseModel.setMessage("注册失败！");
                    return baseModel;
                }
                baseModel.setResultCode(0);//如果为0则表示成功
                baseModel.setMessage("注册成功！");
                return baseModel;
            }
        } catch (Exception e) {
            e.printStackTrace();
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("数据异常！");
            return baseModel;
        }
    }

    public BaseModel isexist(String user_account) {
        BaseModel baseModel = new BaseModel();
        try {
            if (user_account == null) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (user_account.equals("")) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            List<User> users = userDao.isExistAccount(user_account);
            if (users.size() > 0) {
                baseModel.setResultCode(2);//如果为0则表示成功
                baseModel.setMessage("用户已存在！");
                return baseModel;
            }
            baseModel.setResultCode(0);//如果为0则表示成功
            baseModel.setMessage("用户不存在！");
            return baseModel;
        } catch (Exception e) {
            e.printStackTrace();
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("数据异常！");
            return baseModel;
        }
    }

    public BaseModel login(String user_account, String user_password) {
        BaseModel baseModel = new BaseModel();
        try {
            if (user_account == null) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (user_account.equals("")) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (user_password == null) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (user_password.equals("")) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            List<User> users = userDao.login(user_account, user_password);
            if (users.size() <= 0) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("账号密码错误或不存在！");
                return baseModel;
            }
            baseModel.setResultCode(0);//如果为0则表示成功
            baseModel.setMessage("登录成功！");
            baseModel.setData(users);
            return baseModel;
        } catch (Exception e) {
            e.printStackTrace();
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("数据异常！");
            return baseModel;
        }

    }
}

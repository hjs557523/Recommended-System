package com.dc.base.contants;

/**
 * @author Danpil
 * @Description 常量类
 * @date 2018-11-14 15:42
 */
public class BaseContants {
    public static final String LOGIN_USER = "login_user";//session中的用户key
    public static final String LOGIN_PERMISSION = "login_permission";//session中的用户权限
    public static final String SESSION_TOKEN="session_token";//session中保存的token

    public static final String WEBAPP_ROOT = "webapp.root";//监听器写到内存中的前端页面路径
    public static final String PROJECT_NAME = "projectName";//监听器写到内存中的项目名称
    public static final String CLASSES = "classes";//监听器写到内存中的后端代码编译路径


    /**
     * @title:<h3> 操作类型常量 <h3>
     * @author: Danpil
     * @date: 2018-11-19 15:54
     * @params
     * @return
     **/
    public interface OPERATION_TYPE {
        String ADD = "新增";
        String UPDATE = "修改";
        String DELETE = "删除";
        String IMPORT = "导入";
        String EXPORT = "导出";
        String SEARCH = "查询";
    }

    /**
     * @title:<h3> 字段类型 <h3>
     * @author: Danpil
     * @date: 2018-11-22 14:09
     * @params
     * @return
     **/
    public interface FIELD_TYPE {
        String VARCHAR = "string";
        String INT = "INT";
        String DATE = "DATE";
        String DATETIMR = "DATETIME";


    }
}

package com.dc.base.em;

public enum RoleMenuEnum {

    NO_2(2,"Swagger","查询")

    ,NO_203(203,"角色管理","查询,新增,修改,删除")

    , NO_204(204,"用户管理","查询,新增,修改,删除,导入,导出")

    , NO_102(102,"登录日志","查询")

    , NO_103(103,"操作日志","查询")

    ;


    int code;
    String name;
    String value;
     RoleMenuEnum(int code,String name,String value){
         this.code=code;
        this.name=name;
        this.value=value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

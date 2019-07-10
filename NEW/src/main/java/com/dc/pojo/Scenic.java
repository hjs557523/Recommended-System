package com.dc.pojo;

import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Scenic implements Serializable {

    @ApiModelProperty(value = "用户id")
    private int scenic_id;

    @ApiModelProperty(value = "用户名称")
    private String scenic_name;

    public int getScenic_id() {
        return scenic_id;
    }

    public void setScenic_id(int scenic_id) {
        this.scenic_id = scenic_id;
    }

    public String getScenic_name() {
        return scenic_name;
    }

    public void setScenic_name(String scenic_name) {
        this.scenic_name = scenic_name;
    }
}

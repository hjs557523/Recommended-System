package com.dc.base.pojo;

import com.wordnik.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BaseModel {
    @ApiModelProperty("定义返回状态码,‘0’为程序执行成功")
    private Integer resultCode = 0;//定义返回状态码
    @ApiModelProperty("定义返回的错误消息")
    private String message;//定义一个消息
    @ApiModelProperty("定义返回的数据集合")
    private Object data;//数据集
    @ApiModelProperty("定义分页参数")
    private QueryParams queryParams = new QueryParams();
    @ApiModelProperty("文件上传对象")
    private MultipartFile[] tempMFile;
    @ApiModelProperty(value = "用于存放上传文件的id")
    private String filesArray;// 用于存放文件上传的路径

    @ApiModelProperty("aop保存参数")
    private String aop_mesg="";
    @ApiModelProperty("无权限的按钮dom选择器")
    private String permission_btns;
    @ApiModelProperty("高级查询条件")
    private List<AdvancedQuery> listAdvancedQuery;
    @ApiModelProperty("表单防重token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<AdvancedQuery> getListAdvancedQuery() {
        return listAdvancedQuery;
    }

    public void setListAdvancedQuery(List<AdvancedQuery> listAdvancedQuery) {
        this.listAdvancedQuery = listAdvancedQuery;
    }

    public String getPermission_btns() {
        return permission_btns;
    }

    public void setPermission_btns(String permission_btns) {
        this.permission_btns = permission_btns;
    }

    public String getAop_mesg() {
        return aop_mesg;
    }

    public void setAop_mesg(String aop_mesg) {
        this.aop_mesg = aop_mesg;
    }

    public String getFilesArray() {
        return filesArray;
    }

    public void setFilesArray(String filesArray) {
        this.filesArray = filesArray;
    }

    public MultipartFile[] getTempMFile() {
        return tempMFile;
    }

    public void setTempMFile(MultipartFile[] tempMFile) {
        this.tempMFile = tempMFile;
    }

    public QueryParams getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(QueryParams queryParams) {
        this.queryParams = queryParams;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BaseModel{");
        sb.append("resultCode=").append(resultCode);
        sb.append(", message='").append(message).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}

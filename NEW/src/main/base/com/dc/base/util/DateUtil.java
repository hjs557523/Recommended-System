package com.dc.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 黄继升 16041321
 * @Description: 时间类操作工具类
 * @date Created in 2019/7/7 14:38
 * @Modified By:
 */
public class DateUtil {

    /**
     * 转化前端传来的字符串日期数据为Date类
     * @param formDate 需要处理的字符串日期数据
     * @return 转换后的Date类，若处理失败则返回null
     */
    public static Date transFormDateToDate(String formDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date result = null;
        try {
            result = simpleDateFormat.parse(formDate);

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
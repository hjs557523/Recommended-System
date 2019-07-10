package com.dc.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 黄继升 16041321
 * @Description: 字符串工具类
 * @date Created in 2019/7/7 14:38
 * @Modified By:
 */
public class StringUtil {
    /**
     * 判断字符串是否为无效值（null或空值）
     * @param var 需要进行判断的字符串
     * @return 若为空则返回true，否则返回false
     */
    public static boolean isEmpty(String var){
        if (var == null || "".equals(var)){
            return true;
        }else {
            return false;
        }
    }

}

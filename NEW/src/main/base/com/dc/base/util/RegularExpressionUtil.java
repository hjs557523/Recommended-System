package com.dc.base.util;

import java.util.regex.Pattern;

/**
 * @author 黄继升 16041321
 * @Description: 时间类操作工具类
 * @date Created in 2019/7/7 14:38
 * @Modified By:
 */

public class RegularExpressionUtil {
    //验证特殊字符
    public static String SPECIAL_CODE = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    /**
     * @return boolean
     * @title:<h3> 正则表达式验证 <h3>
     * @author: 黄继升 16041321
     * @date: 2018-11-22 13:46
     * @params [regular, str]
     **/
    public static boolean check(String regular, String str) throws Exception {
        return Pattern.matches(regular, str);
    }
}

package com.dc.base.util;

import java.io.*;
import java.util.Properties;

/**
 * @author 黄继升 16041321
 * @Description: 时间类操作工具类
 * @date Created in 2019/7/7 14:38
 * @Modified By:
 */

public class PythonUtil {

    public static String loadProps() {
        Properties props = new Properties();
        InputStream in = null;
        try {
            //in = PropertyUtil.class.getClassLoader().getResourceAsStream("param.properties");
            in = PythonUtil.class.getResourceAsStream("/python.properties");
            props.load(in);
            return props.getProperty("ap");
        } catch (FileNotFoundException e) {
            return "-1";
        } catch (IOException e) {
            return "-1";
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                return "-1";
            }
        }

    }


    //根据用户ID获取推荐景点ID
    public static String getScenicIdByPython(String userId, String num, String ap) {
        String line = "";
        try {
            Process pr = Runtime.getRuntime().exec("python " + ap + " " + userId + " " + num);
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(pr.getInputStream()));
            line = in.readLine();
            in.close();
            pr.waitFor();
            return line.substring(1, line.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println(getScenicIdByPython("4", "10", "D:\\Documents\\Desktop\\短学期\\短学期项目\\Python算法\\untitled0.py"));
    }
}

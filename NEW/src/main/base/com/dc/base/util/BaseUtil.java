package com.dc.base.util;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄继升 16041321
 * @Description: 时间类操作工具类
 * @date Created in 2019/7/7 14:38
 * @Modified By:
 */

public class BaseUtil {
    /**
     * @param map
     * @param entity
     * @return
     * @throws Exception
     * @Description 利用反射机制，将map对象的值存入实体类中
     * @author 黄继升 16041321
     * @date 2016年10月6日
     */
    public static void mapToEntity(Map<String, Object> map, Object entity) throws Exception {
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = entity.getClass().getDeclaredFields();
        // 遍历所有属性
        for (int j = 0; j < fields.length; j++) {
            // 获取属性的名字
            Field field = fields[j];
            String fieldName = field.getName();
            String mapValue = map.get(fieldName) + "";
            setFieldValue(field, mapValue, entity);
        }

    }

    /**
     * <h3>利用反射，根据字段设置实体类字段的值</h3>
     *
     * @param field
     * @param mapValue
     * @param entity
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @author 黄继升 16041321
     * @date 2018年1月7日 上午11:14:20
     */
    private static void setFieldValue(Field field, String mapValue, Object entity)
            throws NoSuchMethodException, SecurityException {
        if (mapValue == null || "".equals(mapValue) || "null".equals(mapValue)) {// 如果实体类的属性值在map中不存在，则返回继续
            return;
        }
        // 获取属性的名字
        String fieldName = field.getName();
        // 获得相应属性的getXXX和setXXX方法名称
        String setName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        // 获取属性的类型
        String type = field.getGenericType().toString();
        if (field.getModifiers() != 0 && field.getModifiers() != 2) {// 非成员变量（0为无修饰符，2为priavte）
            // static:9,private:2,public:1
            return;
        }
        // 获取相应的方法
        Method setMethod = entity.getClass().getMethod(setName, new Class[]{field.getType()});

        // 关键。。。可访问私有变量
        // field.setAccessible(true);
        try {
            if (type.equals("class java.lang.String")) {
                setMethod.invoke(entity, new Object[]{mapValue});
            } else if (type.equals("char")) {
                char val = mapValue.charAt(0);
                setMethod.invoke(entity, new Object[]{val});
            } else if (type.equals("class java.lang.Integer") || type.equals("int")) {
                setMethod.invoke(entity, new Object[]{(int) Double.parseDouble(mapValue)});
            } else if (type.equals("class java.lang.Double") || type.equals("double")) {
                setMethod.invoke(entity, new Object[]{Double.parseDouble(mapValue)});
            } else if (type.equals("class java.util.Date")) {
                String ymd = "yyyy-MM-dd HH:mm:ss.SSS";
                SimpleDateFormat sdf = new SimpleDateFormat(ymd.substring(0, mapValue.length()));
                Date date = sdf.parse(mapValue);
                setMethod.invoke(entity, new Object[]{date});
            } else if (type.equals("class java.sql.Date")) {
                java.sql.Date valueOf = java.sql.Date.valueOf(mapValue);
                setMethod.invoke(entity, new Object[]{valueOf});
            } else if (type.equals("class java.lang.Float") || type.equals("float")) {
                setMethod.invoke(entity, new Object[]{Float.parseFloat(mapValue)});
            } else if (type.equals("class java.lang.Long") || type.equals("long")) {
                setMethod.invoke(entity, new Object[]{Long.parseLong(mapValue)});
            } else if (type.equals("class java.lang.Short")) {
                setMethod.invoke(entity, new Object[]{Short.parseShort(mapValue)});
            } else if (type.equals("class java.lang.Boolean") || type.equals("boolean")) {
                setMethod.invoke(entity, new Object[]{Boolean.parseBoolean(mapValue)});
            } else if (type.equals("class java.lang.Byte") || type.equals("byte")) {
                setMethod.invoke(entity, new Object[]{Byte.parseByte(mapValue)});
            }
            /* Object value = getMethod.invoke(entity, new Object[] {}); */
        } catch (Exception e) {
            System.err.println(fieldName + "类型转换出错！");
            e.printStackTrace();
        }
    }

    /**
     * <h3>利用反射根据key获得字段类型</h3>
     *
     * @param entity
     * @param key
     * @return
     * @author 黄继升 16041321
     * @date 2018年1月7日 上午12:14:19
     */
    private static String getFieldType(Object entity, String key) {
        String type = null;
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = entity.getClass().getDeclaredFields();
        // 遍历所有属性
        for (int j = 0; j < fields.length; j++) {
            // 获取属性的名字
            Field field = fields[j];
            String fieldName = field.getName();
            if (key.equals(fieldName)) {
                return field.getGenericType().toString();
            }
        }
        return type;
    }

    /**
     * <h3>根据反射获得实体类所有的字段类型</h3>
     *
     * @param entity
     * @return
     * @author 黄继升 16041321
     * @date 2018年1月7日 下午3:00:11
     */
    private static Map<String, String> getFieldTypeMap(Object entity) {
        Map<String, String> mapFieldType = new HashMap<String, String>();
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = entity.getClass().getDeclaredFields();
        // 遍历所有属性
        for (int j = 0; j < fields.length; j++) {
            // 获取属性的名字
            Field field = fields[j];
            if (field.getModifiers() != 0 && field.getModifiers() != 2 && field.getModifiers() != 4) {// 非成员变量（0为无修饰符，2为priavte）
                // static:9,private:2,public:1
                continue;
            }
            String fieldName = field.getName();
            mapFieldType.put(fieldName, field.getGenericType().toString());
        }
        return mapFieldType;
    }

    /**
     * <h3>利用反射获得实体类的值</h3>
     *
     * @param entity
     * @param key
     * @return
     * @throws Exception
     * @author 黄继升 16041321
     * @date 2018年2月24日 下午9:05:31
     */
    public static Object getFieldValue(Object entity, String key) throws Exception {
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = entity.getClass().getDeclaredFields();
        // 遍历所有属性
        for (int j = 0; j < fields.length; j++) {
            // 获取属性的名字
            Field field = fields[j];
            String fieldName = field.getName();
            if (key.equals(fieldName)) {
                if (field.getModifiers() != 0 && field.getModifiers() != 2) {// 非成员变量（0为无修饰符，2为priavte）
                    // static:9,private:2,public:1，public static final：25
                    continue;
                }
                String getName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Object value = entity.getClass().getMethod(getName, new Class[]{}).invoke(entity, new Class[]{});
                return value;
            }
        }
        return null;
    }

    /**
     * <h3>利用反射，根据字段名设置字段的值</h3>
     *
     * @param fieldName
     * @param value
     * @param entity
     * @author 黄继升 16041321
     * @date 2018年1月7日 上午11:17:29
     */
    public static void setFieldValue(String fieldName, String value, Object entity) {
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = entity.getClass().getDeclaredFields();
        // 遍历所有属性
        for (int j = 0; j < fields.length; j++) {
            // 获取属性的名字
            Field tfield = fields[j];
            if (fieldName.equals(tfield.getName())) {
                try {
                    setFieldValue(tfield, value, entity);
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param obj1
     * @param obj2
     * @throws Exception
     * @Description 复制对象
     * @author 黄继升 16041321
     * @date 2016年1月5日
     */
    public static void copyObj1ToObj2(Object obj1, Object obj2) throws Exception {
        if (obj1 == null || obj2 == null) {
            return;
        }
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = obj1.getClass().getDeclaredFields();
        Field[] fields2 = obj2.getClass().getDeclaredFields();
        Map<String, Integer> mapFieldName = new HashMap<String, Integer>();
        for (int i = 0; i < fields2.length; i++) {// 获得对象2的所有属性
            String fieldName = fields2[i].getName();
            mapFieldName.put(fieldName, 0);
        }
        // 遍历所有属性
        for (int j = 0; j < fields.length; j++) {
            // 获取属性的名字
            Field field = fields[j];
            String fieldName = field.getName();
            if ("serialVersionUID".equals(fieldName)) {
                continue;
            } else if (mapFieldName.get(fieldName) == null) {// 如果不是对象1和对象2同时存在的属性，则忽略
                continue;
            } else if (field.getModifiers() != 0 && field.getModifiers() != 2) {// 非成员变量（0为无修饰符，2为priavte）
                continue;
            }
            // 获得相应属性的getXXX和setXXX方法名称
            String setName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            String getName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Object value = obj1.getClass().getMethod(getName, new Class[]{}).invoke(obj1, new Class[]{});
            if (value != null) {
                obj2.getClass().getMethod(setName, new Class[]{field.getType()}).invoke(obj2, value);
            }

        }
    }

    /**
     * @return java.lang.String
     * @title:<h3>获取ip地址 <h3>
     * @author: 黄继升 16041321
     * @date: 2018-9-27 12:14
     * @params [request]
     **/
    public static String getIp(HttpServletRequest request) {
        //SAPI过滤器也会对request对象进行再包装，附加一些WLS要用的头信息
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            //这种方法在大部分情况下都是有效的。但是在通过了Apache，Squid等反向代理软件就不能获取到客户端的真实IP地址了。
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;


    }
}

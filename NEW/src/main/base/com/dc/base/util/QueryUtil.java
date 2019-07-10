package com.dc.base.util;

import com.dc.base.contants.BaseContants;
import com.dc.base.em.EnumQueryType;
import com.dc.base.exception.BusinessException;
import com.dc.base.pojo.AdvancedQuery;
import com.dc.base.pojo.BaseModel;
import com.dc.base.pojo.QueryParams;
import org.apache.commons.jexl2.UnifiedJEXL;

import javax.management.Query;
import java.util.List;

/**
 * @author 黄继升 16041321
 * @Description: 时间类操作工具类
 * @date Created in 2019/7/7 14:38
 * @Modified By:
 */


public class QueryUtil {
    public static void convertAdvancedQuery(BaseModel baseModel) throws Exception {
        QueryParams queryParams = convertAdvancedQuery(baseModel.getListAdvancedQuery());
        queryParams.setCurr_page(baseModel.getQueryParams().getCurr_page());
        queryParams.setPage_size(baseModel.getQueryParams().getPage_size());
        baseModel.setQueryParams(queryParams);
    }

    /**
     * @return com.dc.base.pojo.QueryParams
     * @title:<h3> 转化高级查询条件 <h3>
     * @author: 黄继升 16041321
     * @date: 2018-11-22 11:08
     * @params [listAdvancedQuery]
     **/
    public static QueryParams convertAdvancedQuery(List<AdvancedQuery> listAdvancedQuery) throws Exception {
        QueryParams queryParams = new QueryParams();
        StringBuilder where = new StringBuilder("");
        if (listAdvancedQuery != null && listAdvancedQuery.size() > 0) {
            for (int i = 0; i < listAdvancedQuery.size(); i++) {//解析高级查询条件
                where.append(" ");
                where.append(convertAdvancedQueryItem(listAdvancedQuery.get(i)));//and user_name='zhangsan'
            }
        }
        //去掉查询条件的第一个and或者or
        if (where.toString().trim().indexOf("AND") == 0) {
            queryParams.setWhere("(" + where.toString().trim().substring(3) + ")");
        } else if (where.toString().trim().indexOf("OR") == 0) {
            queryParams.setWhere("(" + where.toString().trim().substring(3) + ")");
        } else {
            queryParams.setWhere(where.toString().trim());
        }

        return queryParams;
    }

    /**
     * @return java.lang.String
     * @title:<h3> 转化sql语句返回 <h3>
     * @author: 黄继升 16041321
     * @date: 2018-11-22 11:17
     * @params [advancedQuery]
     **/
    private static String convertAdvancedQueryItem(AdvancedQuery advancedQuery) throws Exception {
        StringBuilder where = new StringBuilder("");
//and user_name='zhangsan'
        //如果没有查询的字段或字段的值，则不需要组装查询条件
        if (advancedQuery.getFieldName() == null || "".equals(advancedQuery.getFieldName())
                || advancedQuery.getFieldValue() == null || "".equals(advancedQuery.getFieldValue())) {
            return "";
        }
        //设置默认字段类型为varchar
        if (advancedQuery.getFieldType() == null || "".equals(advancedQuery.getFieldType())) {
            //设置默认字段类型为varchar
            advancedQuery.setFieldType(BaseContants.FIELD_TYPE.VARCHAR);
        }

        //验证逻辑运算符和关系运算符是否符合配置的参数
        if (advancedQuery.getLogicalOperator() != null
                && EnumQueryType.getLogicalByTag(advancedQuery.getLogicalOperator()) == null) {
            throw new BusinessException("高级查询条件异常：逻辑运算符异常");
        } else {
            where.append(EnumQueryType.getLogicalByTag(advancedQuery.getLogicalOperator()));//逻辑运算符（and/or）
        }
        where.append(" ");
        //验证字段
        if (RegularExpressionUtil.check(RegularExpressionUtil.SPECIAL_CODE, advancedQuery.getFieldName())) {
            throw new BusinessException("高级查询字段不能含有特殊字符");
        }
        where.append(advancedQuery.getFieldName());//字段
        where.append(" ");
        //设置默认关系运算符为等于
        if (advancedQuery.getTempOperator() == null || "".equals(advancedQuery.getTempOperator())) {
            if (BaseContants.FIELD_TYPE.VARCHAR.equals(advancedQuery.getFieldType())) {//varchar的默认关系运算符为like
                advancedQuery.setTempOperator(EnumQueryType.RELATION_LIKE.getTag());
            } else {
                advancedQuery.setTempOperator(EnumQueryType.RELATION_QUERY.getTag());
            }
        } else if (advancedQuery.getTempOperator() != null
                && EnumQueryType.getLogicalByTag(advancedQuery.getTempOperator()) == null) {
            throw new BusinessException("高级查询条件异常：关系运算符异常");
        }
        //将临时关系运算符转化为实际关系运算符
        advancedQuery.setRelationOperator(EnumQueryType.getLogicalByTag(advancedQuery.getTempOperator()));

        //组装条件
        if (EnumQueryType.RELATION_QUERY.getTag().equals(advancedQuery.getTempOperator())) {//如果是等于
            //如果是时间类型，则进行时间段查询
            //2018-01-01(2018-01-01 00:00:00-2018-01-01 23.59-59)
            if (BaseContants.FIELD_TYPE.DATE.equals(advancedQuery.getFieldType())) {//date
                where.append(">=");//关系运算符（大于、等于...）
                where.append("'");
                where.append(advancedQuery.getFieldValue() + " 00:00:00");//字段值
                where.append("'");
                where.append(" and ");
                where.append("<=");
                where.append("'");
                where.append(advancedQuery.getFieldValue() + " 23:59:59");//字段值
                where.append("'");
            } else {
                where.append(advancedQuery.getRelationOperator());//关系运算符（大于、等于...）
                where.append("'");
                where.append(advancedQuery.getFieldValue());//字段值
                where.append("'");
            }

        } else if (EnumQueryType.RELATION_GREATER.getTag().equals(advancedQuery.getTempOperator())
                || EnumQueryType.RELATION_LESS.getTag().equals(advancedQuery.getTempOperator())
                || EnumQueryType.RELATION_NOTGREATER.getTag().equals(advancedQuery.getTempOperator())
                || EnumQueryType.RELATION_NOTLESS.getTag().equals(advancedQuery.getTempOperator())
                || EnumQueryType.RELATION_NOT_QUERY.getTag().equals(advancedQuery.getTempOperator())) {//如果是大于、小于、大于等于、小于等于、不等于
            where.append(advancedQuery.getRelationOperator());//关系运算符（大于、等于...）
            where.append("'");
            where.append(advancedQuery.getFieldValue());//字段值
            where.append("'");
        } else if (EnumQueryType.RELATION_LIKE.getTag().equals(advancedQuery.getTempOperator())) {//如果是包含
            where.append(advancedQuery.getRelationOperator());
            where.append("'%");
            where.append(advancedQuery.getFieldValue());//字段值
            where.append("%'");
        } else if (EnumQueryType.RELATION_LIKESTAR.getTag().equals(advancedQuery.getTempOperator())) {//如果是以...开始
            where.append(advancedQuery.getRelationOperator());
            where.append("'");
            where.append(advancedQuery.getFieldValue());//字段值
            where.append("%'");
        } else if (EnumQueryType.RELATION_LIKEEND.getTag().equals(advancedQuery.getTempOperator())) {//如果是以...结尾
            where.append(advancedQuery.getRelationOperator());
            where.append("'%");
            where.append(advancedQuery.getFieldValue());//字段值
            where.append("'");
        } else {
            where.append(advancedQuery.getRelationOperator());//关系运算符（大于、等于...）
            where.append("'");
            where.append(advancedQuery.getFieldValue());//字段值
            where.append("'");
        }

        return where.toString();
    }

}

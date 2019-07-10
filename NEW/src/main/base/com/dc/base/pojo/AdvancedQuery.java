package com.dc.base.pojo;


import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 
 * @ClassName AdvancedQuery
 * @Description 封装高级查询条件�?
 * @author Danpil
 * @date 2015-8-19
 */
public class AdvancedQuery {
	@ApiModelProperty(value = "字段名称")
	private String fieldName;// 字段名称
	@ApiModelProperty(value = "关系运算符（>、<、=、like、>=、<=")
	private String relationOperator;// 关系运算符（>、<、=、like、likeStart、likeEnd、>=、<=
	@ApiModelProperty(value = "关系运算符的临时运算符，用来转义符号、like_start、like_end")
	private String tempOperator;// 临时关系运算符
	@ApiModelProperty(value = "")
	private String fieldValue;// 字段值
	@ApiModelProperty(value = "字段类别(如果是时间类型需要传date)")
	private String fieldType = "string";// 字段类别(如果是时间类型需要传date)
	@ApiModelProperty(value = "逻辑运算符（and、or)")
	private String logicalOperator = "AND";// 逻辑运算符（and、or)
	@ApiModelProperty(value = "排序(升序、降序)")
	private String sort;// 排序(升序、降序)

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getRelationOperator() {
		return relationOperator;
	}

	public void setRelationOperator(String relationOperator) {
		this.relationOperator = relationOperator;
	}

	public String getLogicalOperator() {
		return logicalOperator;
	}

	public void setLogicalOperator(String logicalOperator) {
		this.logicalOperator = logicalOperator;
	}

	public String getTempOperator() {
		return tempOperator;
	}

	public void setTempOperator(String tempOperator) {
		this.tempOperator = tempOperator;
	}

}

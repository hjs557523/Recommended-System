package com.dc.base.em;

/**
 *
 * <h2>高级查询运算符枚举类</h2>
 *
 * @ClassName: EnumQueryType
 * @author Danpil
 * @date 2018年1月11日 下午4:02:34
 */
public enum EnumQueryType {
	LOGICAL_AND("AND", "AND", "且")
	, LOGICAL_OR("OR", "OR", "或")

	,RELATION_LIKE("LIKE", "like", "包含")
	, RELATION_LIKESTAR("LIKE_START", "like", "以...开始")
	, RELATION_LIKEEND("LIKE_END", "like", "以...结尾")

	,RELATION_GREATER("GREATER", ">", "大于")
	, RELATION_LESS("LESS", "<", "小于")
	, RELATION_NOTGREATER("NOT_GREATER", "<=","小于等于")
	, RELATION_NOTLESS("NOT_LESS", ">=", "大于等于")

	,RELATION_QUERY("QUERY", "=", "等于")
	, RELATION_NOT_QUERY("NOT_QUERY", "!=", "不等于")

	,RELATION_IN("IN","in","IN")

	,PLACEHOLDER("PLACEHOLDER","","占位")

	,RELATION_IS_NULL("IS_NULL", "is null", "为空")
	, RELATION_IS_NOT_NULL("IS_NOT_NULL", "is not null", "不为空")

	;

	String tag;
	String logical;
	String info;

	private EnumQueryType(String tag, String logical, String info) {
		this.tag = tag;
		this.logical = logical;
	}

	/**
	 *
	 * <h3>根据查询运算符别名获得实际运算符</h3>
	 *
	 * @param tag
	 * @return
	 * @author Danpil
	 * @date 2018年1月11日 下午4:01:27
	 */
	public static String getLogicalByTag(String tag) {
		for (EnumQueryType em : EnumQueryType.values()) {
			if (em.getTag().equals(tag)) {
				return em.getLogical();
			}
		}
		return null;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getLogical() {
		return logical;
	}

	public void setLogical(String logical) {
		this.logical = logical;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}

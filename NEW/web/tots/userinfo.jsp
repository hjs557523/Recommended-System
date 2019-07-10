<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>订票 - 旅游推荐系统</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <%--<script type="text/javascript" src="http://g.alicdn.com/sj/dpl/1.5.1/js/sui.min.js"></script>--%>
    <link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/main.css">
    <script src="../js/jquery-3.3.1.min.js"></script>
    <script src="../js/jquery.cookie.js"></script>
    <script src="../js/searchTicket.js"></script>
    <%--jQuery顺序要在jQuery-ui前面--%>
    <script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>

    <script>
        $(function () {
            $("#datepicker").datepicker();
        });
    </script>

</head>
<body>

<div class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <a href="../index.html" class="navbar-brand">
            </a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="../index.html">查看热门推荐</a></li>
            <li><a href="/tots/searchTicket.jsp">车票查询</a></li>
            <li><a href="/tots/searchTicket.jsp">返回</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right" id="userInfo"></ul>
    </div>
</div>

<div class="form-container">
    <h1 style="text-align: center">订单查询</h1>
</div>

<div class="search">
    <form id="form-msg" class="sui-form form-horizontal"  action="/getMyTicket" method="post">
        <div id="navfirst" >
            <input type="submit" class="input-middle" value="查询自己的票" style="margin-top: 25px">
        </div>
    </form>
</div>
<div class="contain">
    <div class="traintable">
        <table class="sui-table table-primary">
            <thead>
            <tr>
                <th>姓名</th>
                <th>身份证号</th>
                <th>列车号</th>
                <th>出发站</th>
                <th>终点站</th>
                <th>出发时间</th>
                <th>座位类型</th>
                <th>票价</th>
                <th>&nbsp;&nbsp;&nbsp;&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.userticketList}" var="elem">
                <tr>
                    <td>${elem.userName}</td>
                    <td>${elem.identityNum}</td>
                    <td>${elem.trainNum}</td>
                    <td>${elem.startStation}</td>
                    <td>${elem.endStation}</td>
                    <td>${elem.startTime}</td>
                    <td>${elem.seatName}</td>
                    <td>${elem.price}</td>
                    <td>
                        <c:if test="${elem.isEffect eq 1}">
                            <form id="form-msg" class="sui-form form-horizontal"  action="/refundTicket" method="post">
                                <div id="navfirst" >
                                    <input type="hidden" name="orderId" value="${elem.orderId}" hidden="hidden"/>
                                    <input type="submit" class="input-middle" value="退票" style="margin-top: 25px">
                                </div>
                            </form>
                        </c:if>
                        <c:if test="${elem.isEffect ne 1}">
                            已退票
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
<script type="text/javascript">
    $('#demo1 input').datepicker({size:"small"});
</script>
<style type="text/css">
    * { padding:0; margin:0;}

    .Navigation{
        width: 100%;
        height: 80px;
        background-color:#4b6cd8;
        overflow:hidden;
    }
    img{
        width:100px;
        height:80px;
    }

    p.project_name{
        font-style: normal;
        font-size: 30px;
        color: #ffffff;
        float: left;
        margin-top:30px;
    }

    #menu {
        font:50px verdana, arial, sans-serif; /* 设置文字大小和字体样式 */
        margin-left:400px;
    }

    #menu, #menu li {
        list-style:none; /* 将默认的列表符号去掉 */
    }

    #menu li {
        float:left;
    }

    #menu li a {
        display:block;
        padding:8px 30px;
        color:#ffffff;
        text-decoration:none;
        margin-top: 0px;
        font-size: 30px;
    }

    .search{
        margin:20px 10%;
        width:80%;
        text-align:center;
        background-color:#eef1f8;
        height:60px;

    }
    body{
        margin:0;
        padding:0;
    }
    #navfirst{

    }
    #menu {
        font:15px verdana, arial, sans-serif; /* 设置文字大小和字体样式 */

    }
    #menu, #menu li {
        list-style:none; /* 将默认的列表符号去掉 */
        margin-top:20px;
    }

    #menu li {
        float:left;
    }
    .contain{
        margin:20px 10%;
        width:80%;
        text-align:center;
        background-color:#eef1f8;
        height:600px;
    }
    .traintable{
        width:80%;
        margin:40px 10%;
        text-align:center;

    }
</style>
</html>


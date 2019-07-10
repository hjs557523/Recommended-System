<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>订票系统 - 旅游推荐系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
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
            <li><a href="/tots/advancedsearch.jsp">车票高级查询</a></li>
            <li><a href="/tots/userinfo.jsp">个人中心</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right" id="userInfo"></ul>
    </div>
</div>
<div class="form-container">
    <h1 style="text-align: center">车票购买</h1>
</div>

<div class="contain1">
    <div class="traintable">
        <table class="sui-table table-primary">
            <thead>
            <tr>
                <th>车次</th>
                <th>起点</th>
                <th>起始时间</th>
                <th>终点</th>
                <th>到达时间</th>
                <th>&nbsp;&nbsp;&nbsp;&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td >${sessionScope.tarTat.trainInfo.trainNum}</td>
                <td >${sessionScope.tarTat.trainInfo.chufazhan}</td>
                <td >${sessionScope.tarTat.trainInfo.departureTime}</td>
                <td >${sessionScope.tarTat.trainInfo.mudizhan}</td>
                <td >${sessionScope.tarTat.trainInfo.arriveTime}</td>
                <td ></td>
            </tr>
           </tbody>

        </table>
    </div>
</div>
<div class="contain">
    <div class="traintable">
        <form action="/buyOneTicket" method="post">
        <table class="sui-table table-primary">
            <thead>
            <tr>
                <th>票型</th>
                <th>余票</th>
                <th>金额</th>
                <th>选择</th>
            </tr>
            <c:forEach items="${sessionScope.seatFormList}" var="elemTicket">
                <c:if test="${elemTicket.ticketNum ne 0}">
                    <tr>
                        <td>${elemTicket.type}</td>
                        <td>${elemTicket.ticketNum}</td>
                        <td>${elemTicket.cost}</td>
                        <td><input type="radio" name="tarLevel" value="${elemTicket.typeRowName}" /></td>
                    </tr>
                </c:if>
            </c:forEach>
            <tr>
                <td align="right" colspan="4">
                    <input  type="submit" id="bookPiao" value="订票" />
                    &nbsp;&nbsp;
                    <input type="button" name="Submit6" value="返回" onClick="doCheck('back')">
                </td>
            </tr>
            </thead>
        </table>
        </form>
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
        height:400px;
    }
    .contain1{
        margin:20px 10%;
        width:80%;
        text-align:center;
        background-color:#eef1f8;
        height:150px;
    }
    .traintable{
        width:80%;
        margin:40px 10%;
        text-align:center;

    }
</style>
</html>
<script  language="javascript">
   function doCheck(cmd){
      var  frm = frmDataList;      
       
      if (cmd == "back"){         
        frm.action="main.jsp";
        frm.submit();
        return;
      }
      //---以下操作（编辑，删除，查看）需要选择用户，才能进行操作。以下调用js函数来判断，单选按钮的名字必须定义为recordID
      var sel = getSelectedItem(frm);
      if (sel==null){
            alert("请选择您要订的票!");
            return;
      }      
      if (cmd == "book"){         
        frm.action="BookServlet?checi="+sel;
        frm.submit();
      }
   }
   
</script>
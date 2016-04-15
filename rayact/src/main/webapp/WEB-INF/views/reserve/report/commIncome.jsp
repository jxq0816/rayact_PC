<%--
  Created by IntelliJ IDEA.
  User: DDT
  Date: 2016/4/5
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=7862256"/>
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.js"></script>
    <title></title>
    <style>
        table tr td {
            height: 60px;
        }
    </style>
</head>
<body style="padding: 0;margin: 0">
<table width="100%" height="80%" border="1" style="text-align: center;font-size:40px;background-color: #ebedf1;margin: 0;padding: 0">
    <tr>
        <td width="1000px"  >商品</td>
        <c:forEach items="${vs}" var="v">
            <td width="1000px" >${v.name}</td>
        </c:forEach>
    </tr>
    <c:forEach items="${rtn}" var="aa">
        <tr>
            <td  >${aa.commName}</td>
        </tr>
    </c:forEach>
</table>
<span>总计：${param.startDate}~${param.endDate}&nbsp;&nbsp;&nbsp;<b class="sum"></b></span>
<script>
    $(function(){
        var c = 0;
        $(".c").each(function(){
            var ct = parseFloat($(this).html());
            c += ct ;
        })
        $(".sum").html(c);
    });
</script>
</body>
</html>

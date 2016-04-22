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
        body{
            background-color: #ebedf1;
        }
    </style>
</head>
<body style="padding: 0;margin: 0">
<table width="100%"  border="1" style="text-align: center;font-size:40px;background-color: #ebedf1;margin: 0;padding: 0">
        <tr>
            <td width="1000px"  >场地</td>
            <td width="1000px"  >全场租用次数</td>
            <td width="1000px"  >全场收入</td>
            <td width="1000px"  >半场租用次数</td>
            <td width="1000px"  >半场收入</td></tr>
    <c:forEach items="${rtn}" var="aa">
        <tr>
            <td  >${aa.field_name}</td>
            <td class="c"  >${aa.fullnum}</td>
            <td class="f"  ><fmt:formatNumber value="${aa.fullprice}" pattern="0.00" maxFractionDigits="2" type="number"></fmt:formatNumber></td>
            <td class="b"  >${aa.halfnum}</td>
            <td class="a"  ><fmt:formatNumber value="${aa.halfprice}" pattern="0.00" maxFractionDigits="2" type="number"></fmt:formatNumber></td>
        </tr>
    </c:forEach>
    <c:forEach items="${rtnTicket}" var="t">
        <td >次卡收入</td>
        <td class="a" colspan="4"><fmt:formatNumber value="${t.price}" pattern="0.00" maxFractionDigits="2" type="number"></fmt:formatNumber></td>
    </c:forEach>
</table>
<div style="text-align: center;font-size:40px;background-color: #ebedf1;margin: auto">总计：&nbsp;<b class="sum" style="font-size:80px;color: #b93434"></b></div>
<script>
    $(function(){
        var a = 0;
        $(".f").each(function(){
            var ft = parseFloat($(this).html());
            a += ft ;
        })
        var f = 0;
        $(".a").each(function(){
            var at = parseFloat($(this).html());
            a += at ;
        })
        $(".sum").html(a.toFixed(2)+"￥");
    });
</script>
</body>
</html>

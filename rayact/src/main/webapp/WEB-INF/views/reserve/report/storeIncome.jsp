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
            <td width="1000px"  >会员</td>
            <td width="1000px"  >充值金额</td>
            <td width="1000px"  >充值类型</td>
            <td width="1000px"  >时间</td>
        </tr>
    <c:forEach items="${rtn}" var="aa">
        <tr>
            <td  >${aa.name}</td>
            <td class="c"  >${aa.transaction_volume}</td>
            <td class="f"  >
                <c:if test="${aa.pay_type == '2'}">现金</c:if>
                <c:if test="${aa.pay_type == '3'}">银行卡</c:if>
                <c:if test="${aa.pay_type == '4'}">微信</c:if>
                <c:if test="${aa.pay_type == '5'}">支付宝</c:if>
                <c:if test="${aa.pay_type == '6'}">其它</c:if>
            </td>
            <td class="b"  >${aa.storeTime}</td>
        </tr>
    </c:forEach>
</table>
<div style="text-align: center;font-size:40px;background-color: #ebedf1;margin: auto">总计：&nbsp;<b class="sum" style="font-size:80px;color: #b93434"></b></div>
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

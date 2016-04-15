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
            <td width="1000px"  >场馆</td>
            <td width="1000px"  >商品收入</td>
            <td width="1000px"  >场地收入</td>
            <td width="1000px"  >违约金收入</td>
            <td width="1000px"  >总收入</td></tr>
    <c:forEach items="${rtn}" var="aa">
        <tr>
            <td  >${aa.venue_name}</td>
            <td class="c"  >${aa.commodity_in}</td>
            <td class="f"  >${aa.field_in}</td>
            <td class="b"  >${aa.break_in}</td>
            <td class="a"  >${aa.all_in}</td>
        </tr>
    </c:forEach>
        <tr>
            <td  >总计</td>
            <td class="ca"  >${aa.commodity_in}</td>
            <td class="fa"  >${aa.field_in}</td>
            <td class="ba"  >${aa.break_in}</td>
            <td class="aa"  >${aa.all_in}</td>
        </tr>
</table>
<script>
    $(function(){
        var c = 0;
        $(".c").each(function(){
            var ct = parseFloat($(this).html());
            c += ct ;
        })
        $(".ca").html(c);
        var f = 0;
        $(".f").each(function(){
            var ft = parseFloat($(this).html());
            f += ft ;
        })
        $(".fa").html(f);
        var b = 0;
        $(".b").each(function(){
            var bt = parseFloat($(this).html());
            b += bt ;
        })
        $(".ba").html(b);
        var a = 0;
        $(".a").each(function(){
            var at = parseFloat($(this).html());
            a += at ;
        })
        $(".aa").html(a);
    });
</script>
</body>
</html>

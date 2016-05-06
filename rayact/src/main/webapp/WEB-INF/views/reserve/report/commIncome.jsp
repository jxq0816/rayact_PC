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
<script>
    var ssum = 0;
</script>
<table width="100%"  border="1" style="text-align: center;font-size:40px;background-color: #ebedf1;margin: 0;padding: 0">
    <tr>
        <td width="1000px"  >商品</td>
        <c:forEach items="${vs}" var="v">
            <td width="1000px" >${v.name}</td>
        </c:forEach>
    </tr>
    <c:forEach items="${rtn}" var="aa">
        <tr>
            <td  >${aa.commName}</td>
        <script>
            var tmpData = eval('(${aa.data})');
        </script>
        <c:forEach items="${vs}" var="v">
            <script>
                var tmpv = "${v.name}";
                var length = tmpData.length;
                var flag = false;
                for(var i = 0 ; i < length ; i++){
                    if(tmpv==tmpData[i].name){
                        document.write("<td>"+tmpData[i].num +"件&nbsp"+tmpData[i].income+"元</td>");
                        ssum += tmpData[i].income;
                        flag = true;
                    }
                }
                if(!flag){
                    document.write("<td>0件&nbsp0元</td>");
                }
            </script>
        </c:forEach>
        </tr>
    </c:forEach>
    <tr>
        <td>总计</td>
        <script>
            var allData = eval('(${all})');
        </script>
        <c:forEach items="${vs}" var="v">
            <script>
                var tmpv = "${v.name}";
                var length = allData.length;
                var flag = false;
                for(var j = 0 ; j <length ; j++){
                    console.log("1111111"+allData[j].venue_name);
                    if(tmpv==allData[j].venue_name){
                        document.write("<td>"+allData[j].commodity_in +"元</td>");
                        flag = true;
                    }
                }
                if(!flag){
                    document.write("<td>0元</td>");
                }
            </script>
        </c:forEach>
    </tr>
</table>
</body>
</html>

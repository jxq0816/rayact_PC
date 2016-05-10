<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地预定</title>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css"/>
</head>
<body>
<table class="table-chang">
    <thead>
    <%--时刻--%>
    <tr>
        <th>时间</th>
        <c:forEach items="${venueFieldPriceList}" var="field" varStatus="status">
            <th><span>${field.fieldName}</span></th>
        </c:forEach>
    </tr>
    <%--时刻--%>
    </thead>
    <tbody>
    <%-- 遍历所有全场的场地开始--%>
    <c:forEach items="${times}" var="t">
        <tr>
                <%-- 纵坐标：时间--%>
            <th><span>${t}</span></th>
                <%-- 横坐标：场地名称--%>
            <c:forEach items="${venueFieldPriceList}" var="field" varStatus="status">
                <c:set var="status" value="0"/>
                <c:set var="itemId" value="0"/>

                <%--遍历单个场地的时间、价格组成的Jason 获得状态--%>
                <c:forEach items="${field.timePriceList}" var="tp">
                    <%--场地jason的时间与横坐标的时间一致 --%>
                    <j:if test="${t eq tp.time}">
                        <%--设置单个场地 时间T 的状态--%>
                        <c:set var="status" value="${tp.status}"/>
                        <c:set var="price" value="${tp.price}"/>
                        <c:set var="username" value="${tp.userName}"/>
                        <c:set var="itemId" value="${tp.consItem.id}"/>
                        <c:set var="checkId" value="${tp.check.id}"/>
                        <%-- A场地 B时间 的状态 结束--%>
                    </j:if>
                </c:forEach>


                <%--设置全场的class--%>
                <j:if test="${'0' eq status}">
                    <c:set var="midClass" value="reserveTd access"/>
                </j:if>
                <j:if test="${'1' eq status}">
                    <c:set var="midClass" value="reserveTd"/>
                </j:if>
                <j:if test="${'4' eq status}">
                    <c:set var="midClass" value="reserveTd red"/>
                </j:if>
                <j:if test="${'00' eq status}">
                    <c:set var="midClass" value="reserveTd access"/>
                </j:if>
                <j:if test="${'01' eq status}">
                    <c:set var="midClass" value="reserveTd access"/>
                </j:if>
                <%--设置全场的class end--%>

                <%-- 场地 B时间 的状态展示--%>

                <%-- 如果有半场 显示为midClass--%>
                <td style="color: #000;" status="${status}"
                    data-item="${itemId}"
                    class="${midClass}"
                    data-price="${price}"
                    data-field="${field.fieldId}"
                    data-time="${t}" title="${username}">
                        ${price}
                </td>
                <%-- A场地 B时间 的状态展示 结束--%>
            </c:forEach>
                <%-- 横坐标：时间 结束--%>
        </tr>
        <%-- 纵坐标：场地 结束--%>
    </c:forEach>
    <%-- 遍历所有全场 场地结束--%>
    </tbody>
</table>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
<script>
    document.write("<script type='text/javascript' src='${ctxStatic}/modules/reserve/js/reserve_app_field.js?t=" + Math.random() + "'><\/script>");
</script>

</body>
</html>

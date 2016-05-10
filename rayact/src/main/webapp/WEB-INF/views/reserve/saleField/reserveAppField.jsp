<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地预定</title>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=7862256"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/jquery/smartMenu.css"/>
    <script type="text/javascript"
            src="${ctxStatic}/jquery/jquery-smartMenu-min.js"></script>
    <script type="text/javascript">var ctx = '${ctx}', consDate = '${consDate.time}', venueId = '${reserveVenue.id}';</script>
</head>
<body>
<table class="table-chang">
    <thead>
    <%--时刻--%>
    <tr>
        <th>时间</th>
        <c:forEach items="${times}" var="t">
            <th style="background-color: #fff;">${t}</th>
        </c:forEach>
    </tr>
    <%--时刻--%>
    </thead>
    <tbody>
    <%-- 遍历所有全场的场地开始--%>
    <c:forEach items="${venueFieldPriceList}" var="fullField" varStatus="status">
        <tr>
                <%-- 纵坐标：场地名称--%>
            <th><span>${fullField.fieldName}</span></th>
                <%-- 横坐标：时间--%>


            <c:forEach items="${times}" var="t">
                <c:set var="status" value="0"/>
                <c:set var="itemId" value="0"/>

                <%--遍历单个场地的时间、价格组成的Jason 获得状态--%>
                <c:forEach items="${fullField.timePriceList}" var="tp">
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
                    <c:set var="midClass" value="reserveTd normal"/>
                </j:if>
                <j:if test="${'01' eq status}">
                    <c:set var="midClass" value="reserveTd abnormal"/>
                </j:if>
                <j:if test="${'01' eq fullStatus}">
                    <c:set var="midClass" value="fullFieldHasAbnormal"/>
                </j:if>
                <%--设置全场的class end--%>

                <%-- 场地 B时间 的状态展示--%>

                <%-- 如果有半场 显示为midClass--%>
                <td style="color: #000;" status="${status}"
                    data-item="${itemId}"
                    data-check="${checkId}"
                    class="${midClass}"
                    data-price="${price}"
                    data-field="${fullField.fieldId}"
                    data-isHalfCourt="0"
                    data-time="${t}" title="${username}">
                        ${username}
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
</body>

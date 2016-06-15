<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<meta name="viewport"
      content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<html>
<head>
    <title>场地预定</title>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/app_field.css"/>
    <link href="${ctxStatic}/cleanzone/js/bootstrap/dist/css/bootstrap.css" rel="stylesheet"/>
</head>
<body>
<c:set var="width" value="${(fn:length(venueFieldPriceList)+1)*60}px"></c:set>
<div style="OVERFLOW-X: scroll;width:${width}" align=center>
    <table class="table-chang">
        <thead style="position:fixed;left:0px;top:0px;">
        <%--时刻--%>
        <tr>
            <th class="time_cell"></th>
            <c:forEach items="${venueFieldPriceList}" var="field" varStatus="status">
                <th class="field_cell"><span>${field.fieldName}</span></th>
            </c:forEach>
        </tr>
        <%--时刻--%>
        </thead>

        <tbody>
        <div style="margin-top:20px;">
            <%-- 遍历所有全场的场地开始--%>
            <c:forEach items="${times}" var="t">
                <tr>
                        <%-- 纵坐标：时间--%>
                    <td class="time_cell">
                        <span>
                                ${fn:substring(t, 0, 5)}
                        </span>
                    </td>
                        <%-- 横坐标：场地名称--%>
                    <c:forEach items="${venueFieldPriceList}" var="field" varStatus="status">
                        <c:set var="status" value="0"/>
                        <%--遍历单个场地的时间、价格组成的Jason 获得状态--%>
                        <c:forEach items="${field.timePriceList}" var="tp">
                            <%--场地jason的时间与横坐标的时间一致 --%>
                            <j:if test="${t eq tp.time}">
                                <%--设置单个场地 时间T 的状态--%>
                                <c:set var="status" value="${tp.status}"/>
                                <c:set var="price" value="${tp.price}"/>
                                <%-- A场地 B时间 的状态 结束--%>
                            </j:if>
                        </c:forEach>


                        <%--设置全场的class--%>
                        <j:if test="${'0' eq status}">
                            <c:set var="midClass" value="reserveTd access"/>
                        </j:if>
                        <j:if test="${!('0' eq status)}">
                            <c:set var="midClass" value="reserveTd unavailable"/>
                        </j:if>
                        <%--设置全场的class end--%>

                        <%-- 场地 B时间 的状态展示--%>

                        <%-- 如果有半场 显示为midClass--%>
                        <td status="${status}"
                            class="${midClass}"
                            data-price="${price}"
                            data-field-id="${field.fieldId}"
                            data-field-name="${field.fieldName}"
                            data-consDate="${consDate}"
                            data-consMobile="${consMobile}"
                            data-time="${t}"
                        >
                            <j:if test="${'0' eq status}">
                                ¥ ${price}
                            </j:if>
                            <j:if test="${!('0' eq status)}">
                                <span>已预订</span>
                            </j:if>

                        </td>
                        <%-- A场地 B时间 的状态展示 结束--%>
                    </c:forEach>
                        <%-- 横坐标：时间 结束--%>
                </tr>
                <%-- 纵坐标：场地 结束--%>
            </c:forEach>
        </div>
        <%-- 遍历所有全场 场地结束--%>
        </tbody>

    </table>
</div>
<div class="row" style="display: none">
    <div id="unPayed" class="row">
    </div>
    <form id="orderForm">
        <input name="consDate" value="${consDate}" type="hidden">
        <input name="reserveVenueId" value="${venueId}" type="hidden">
        <%--<span id="reserve_submit"><a class="btn btn-success col-sm-10" style="height: 80px;width:100%;font-size: 50px;line-height: 80px;" onclick="filedSelectJson()">提交</a></span>--%>
    </form>
</div>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${ctxStatic}/common/jeesite.js"></script>
<script type="text/javascript" src="${ctxStatic}/json/jquery.serializejson.js"></script>
<script>
    document.write("<script type='text/javascript' src='${ctxStatic}/modules/reserve/js/reserve_app_field.js?t=" + Math.random() + "'><\/script>");
</script>

</body>
</html>

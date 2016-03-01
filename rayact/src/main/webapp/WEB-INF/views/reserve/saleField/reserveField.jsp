<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地预定</title>
    <meta name="decorator" content="main"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=7862256"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/jquery/smartMenu.css"/>
    <script type="text/javascript"
            src="${ctxStatic}/jquery/jquery-smartMenu-min.js"></script>
    <script type="text/javascript">var ctx = '${ctx}', consDate = '${consDate.time}', venueId = '${reserveVenue.id}';</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="reserveField"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="tab-tit-first">
        <ul>
            <c:forEach items="${reserveVenueList}" var="venue" varStatus="status">
                <li <j:if test="${venue.id eq reserveVenue.id}">class="on"</j:if>><a
                        href="${ctx}/reserve/field/main?venueId=${venue.id}&t=${consDate.time}">${venue.name}</a></li>
            </c:forEach>
        </ul>
    </div>

    <div class="tab-tit">
        <a name="order"></a>
        <ul>
            <c:forEach items="${timeSlot}" var="slot" varStatus="status">
                <li
                        <j:if test="${consDate.time eq slot.value}">class="on"</j:if> >
                    <a href="${ctx}/reserve/field/main?venueId=${reserveVenue.id}&t=${slot.value}">${slot.key}</a></li>
            </c:forEach>
        </ul>
    </div>
    <div class="content" style="margin:40px">
        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>场地名称</th>
                    <th>可预订</th>
                    <th>已选场</th>
                    <th>已占用</th>
                    <th>已付款</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${venueFieldPriceList}" var="field" varStatus="status">
                    <tr>
                        <td>${field.fieldName}</td>
                        <td><a class="btn btn-primary btn-xs"
                               href="javascript:availableTime('${field.fieldId}','${consDateFormat}')">
                            详情</a></td>
                        <td><a class="btn btn-primary btn-xs"
                               href="${ctx}/reserve/reserveField/form?id=${reserveField.id}">
                            详情</a></td>
                        <td><a class="btn btn-primary btn-xs"
                               href="${ctx}/reserve/reserveField/form?id=${reserveField.id}">
                            详情</a></td>
                        <td><a class="btn btn-primary btn-xs"
                               href="${ctx}/reserve/reserveField/form?id=${reserveField.id}">
                            详情</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@include file="../include/modal.jsp" %>
<!--end dialog-->
<script>
    document.write("<script type='text/javascript' src='${ctxStatic}/modules/reserve/js/reserve_field.js?t=" + Math.random() + "'><\/script>");

</script>
</body>
</html>

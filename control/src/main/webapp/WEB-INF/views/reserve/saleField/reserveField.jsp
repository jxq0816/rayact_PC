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
    <script type="text/javascript">var ctx = '${ctx}', consDate = '${consDate.time}',venueId = '${reserveVenue.id}';</script>
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
        <ul class="table-ul">
            <li style="margin-left: 0px;"><span class="green-bg-color"></span>可预订</li>
            <li><span class="blue-bg-color"></span>已选场次</li>
            <li><span class="grey-bg-color"></span>已占用</li>
            <li><span class="red-bg-color"></span>已付款</li>
        </ul>
    </div>

    <div class="tab-tit">
        <a name="order"></a>
        <ul>
            <c:forEach items="${timeSlot}" var="slot" varStatus="status">
                <li
                        <j:if test="${consDate.time eq slot.value}">class="on"</j:if> ><a
                        href="${ctx}/reserve/field/main?venueId=${reserveVenue.id}&t=${slot.value}">${slot.key}</a></li>
            </c:forEach>
        </ul>
    </div>
    <div class="sy-tab-cont">

        <div class="table-left">

            <table class="table-chang" style="width:auto;">
                <thead>
                <tr>
                    <th></th>
                    <c:forEach items="${venueFieldPriceList}" var="file" varStatus="status">
                        <th><span>${file.fieldName}</span></th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${times}" var="t">
                    <tr>
                        <th style="background-color: #fff;">${t}</th>
                        <c:forEach items="${venueFieldPriceList}" var="file">
                            <c:set var="status" value="0"/>
                            <c:set var="itemId" value="0"/>
                            <c:set var="halfCourt" value="0"/>
                            <c:forEach items="${file.timePriceList}" var="tp">
                                <j:if test="${tp.time eq t}">
                                    <c:set var="status" value="${tp.status}"/>
                                    <c:set var="itemId" value="${tp.consItem.id}"/>
                                    <j:if test="${'1' eq tp.consItem.halfCourt}">
                                        <c:set var="halfCourt" value="1"/>
                                    </j:if>
                                </j:if>
                            </c:forEach>
                            <td status="${status}" data-item="${itemId}"
                                class="reserveTd <j:if test="${'0' eq status}">access</j:if> <j:ifelse test="${'4' eq status}"><j:then>red</j:then><j:else><j:if test="${'1' eq halfCourt}">unpayed</j:if></j:else></j:ifelse>"
                                data-field="${file.fieldId}"
                                data-time="${t}">
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <script type="text/javascript">
            $(".table-left-l ul").css("padding-top", parseInt($(".table-chang thead").eq(0).height()) + 3 + "px");
        </script>

    </div>
</div>
<%@include file="../include/modal.jsp" %>
<!--end dialog-->
<script>
    document.write("<script type='text/javascript' src='${ctxStatic}/modules/reserve/js/reserve_field.js?t=" + Math.random() + "'><\/script>");
</script>
</body>
</html>

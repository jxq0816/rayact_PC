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
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="tab-tit-first">

                    <ul>
                        <div id="venues">
                            <%-- 场馆--%>
                            <c:forEach items="${reserveVenueList}" var="venue" varStatus="status">
                                <li <j:if test="${venue.id eq reserveVenue.id}">class="on"</j:if>><a
                                        href="${ctx}/reserve/field/main?venueId=${venue.id}&t=${consDate.time}">${venue.name}</a>
                                </li>
                            </c:forEach>
                        </div>
                        <%-- 场馆--%>
                    </ul>
                    <ul class="table-ul">
                        <li style="margin-left: 0px;"><span class="green-bg-color"></span>可预订</li>
                        <li><span class="blue-bg-color"></span>已选场次</li>
                        <li><span class="grey-bg-color"></span>已占用</li>
                        <li><span class="red-bg-color"></span>已付款</li>
                    </ul>
                </div>
                <%-- 周几，日期--%>
                <div class="tab-tit">
                    <a name="order"></a>
                    <ul>
                        <div id="timeSlotDiv">
                            <c:forEach items="${timeSlot}" var="slot" varStatus="status">
                                <li
                                    &lt;%&ndash;  <j:if test="${consDate.time eq slot.value}">class="on"</j:if> ><a
                                      href="${ctx}/reserve/field/main?venueId=${reserveVenue.id}&t=${slot.value}">${slot.key}</a>&ndash;%&gt;

                                        <j:if test="${consDate.time eq slot.value}">class="on"</j:if> ><a
                                        href="javascript:filedStatus('${reserveVenue.id}','${slot.value}')">${slot.key}</a>
                                </li>
                            </c:forEach>
                        </div>
                    </ul>
                </div>
                <%-- 周几，日期 结束--%>

                <div class="sy-tab-cont">

                    <div class="table-left">

                        <table class="table-chang" style="width:auto;">
                            <thead>
                            <%--时刻--%>
                            <tr>
                                <th>上午</th>
                                <c:forEach items="${timesAM}" var="t">
                                    <th style="background-color: #fff;">${t}</th>
                                </c:forEach>
                            </tr>
                            <%--时刻--%>
                            </thead>
                            <tbody>

                            <c:forEach items="${venueFieldPriceListAM}" var="file" varStatus="status">
                                <tr>
                                        <%-- 纵坐标：场地--%>
                                    <th><span>${file.fieldName}</span></th>


                                        <%-- 横坐标：时间--%>
                                    <c:forEach items="${timesAM}" var="t">
                                        <c:set var="status" value="0"/>
                                        <c:set var="itemId" value="0"/>
                                        <c:set var="halfCourt" value="0"/>
                                        <c:set var="username" value=""/>
                                        <%-- A场地 B时间 的状态--%>
                                        <c:forEach items="${file.timePriceList}" var="tp">
                                            <j:if test="${t eq tp.time}">
                                                <c:set var="status" value="${tp.status}"/>
                                                <c:set var="price" value="${tp.price}"/>
                                                <c:set var="username" value="${tp.userName}"/>
                                                <c:set var="itemId" value="${tp.consItem.id}"/>
                                                <j:if test="${'1' eq tp.consItem.halfCourt}">
                                                    <c:set var="halfCourt" value="1"/>
                                                </j:if>
                                            </j:if>
                                        </c:forEach>
                                        <%-- A场地 B时间 的状态 结束--%>
                                        <td style="color: #000;" status="${status}" data-item="${itemId}"
                                            class="reserveTd <j:if test="${'0' eq status}">access</j:if> <j:ifelse test="${'4' eq status}"><j:then>red</j:then><j:else><j:if test="${'1' eq halfCourt}">unpayed</j:if></j:else></j:ifelse>"
                                            data-price="${price}"
                                            data-field="${file.fieldId}"
                                            data-time="${t}">
                                                ${username}
                                        </td>
                                    </c:forEach>
                                        <%-- 横坐标：时间 结束--%>
                                </tr>
                                <%-- 纵坐标：场地 结束--%>
                            </c:forEach>

                            </tbody>
                        </table>

                        <table class="table-chang" style="width:auto;">
                            <thead>
                            <%--时刻--%>
                            <tr>
                                <th>下午</th>
                                <c:forEach items="${timesPM}" var="t">
                                    <th style="background-color: #fff;">${t}</th>
                                </c:forEach>
                            </tr>
                            <%--时刻--%>
                            </thead>
                            <tbody>

                            <c:forEach items="${venueFieldPriceListPM}" var="file" varStatus="status">
                                <tr>
                                        <%-- 纵坐标：场地--%>
                                    <th><span>${file.fieldName}</span></th>


                                        <%-- 横坐标：时间--%>
                                    <c:forEach items="${timesPM}" var="t">
                                        <c:set var="status" value="0"/>
                                        <c:set var="itemId" value="0"/>
                                        <c:set var="halfCourt" value="0"/>
                                        <c:set var="username" value=""/>
                                        <%-- A场地 B时间 的状态--%>
                                        <c:forEach items="${file.timePriceList}" var="tp">
                                            <j:if test="${t eq tp.time}">
                                                <c:set var="status" value="${tp.status}"/>
                                                <c:set var="price" value="${tp.price}"/>
                                                <c:set var="username" value="${tp.userName}"/>
                                                <c:set var="itemId" value="${tp.consItem.id}"/>
                                                <j:if test="${'1' eq tp.consItem.halfCourt}">
                                                    <c:set var="halfCourt" value="1"/>
                                                </j:if>
                                            </j:if>
                                        </c:forEach>
                                        <%-- A场地 B时间 的状态 结束--%>
                                        <td style="color: #000;" status="${status}" data-item="${itemId}"
                                            class="reserveTd <j:if test="${'0' eq status}">access</j:if> <j:ifelse test="${'4' eq status}"><j:then>red</j:then><j:else><j:if test="${'1' eq halfCourt}">unpayed</j:if></j:else></j:ifelse>"
                                            data-price="${price}"
                                            data-field="${file.fieldId}"
                                            data-time="${t}">
                                                ${username}
                                        </td>
                                    </c:forEach>
                                        <%-- 横坐标：时间 结束--%>
                                </tr>
                                <%-- 纵坐标：场地 结束--%>
                            </c:forEach>

                            </tbody>
                        </table>

                        <table class="table-chang" style="width:auto;">
                            <thead>
                            <%--时刻--%>
                            <tr>
                                <th>晚上</th>
                                <c:forEach items="${timesEvening}" var="t">
                                    <th style="background-color: #fff;">${t}</th>
                                </c:forEach>
                            </tr>
                            <%--时刻--%>
                            </thead>
                            <tbody>

                            <c:forEach items="${venueFieldPriceListEvening}" var="file" varStatus="status">
                                <tr>
                                        <%-- 纵坐标：场地--%>
                                    <th><span>${file.fieldName}</span></th>


                                        <%-- 横坐标：时间--%>
                                    <c:forEach items="${timesEvening}" var="t">
                                        <c:set var="status" value="0"/>
                                        <c:set var="itemId" value="0"/>
                                        <c:set var="halfCourt" value="0"/>
                                        <c:set var="username" value=""/>
                                        <%-- A场地 B时间 的状态--%>
                                        <c:forEach items="${file.timePriceList}" var="tp">
                                            <j:if test="${t eq tp.time}">
                                                <c:set var="status" value="${tp.status}"/>
                                                <c:set var="price" value="${tp.price}"/>
                                                <c:set var="username" value="${tp.userName}"/>
                                                <c:set var="itemId" value="${tp.consItem.id}"/>
                                                <j:if test="${'1' eq tp.consItem.halfCourt}">
                                                    <c:set var="halfCourt" value="1"/>
                                                </j:if>
                                            </j:if>
                                        </c:forEach>
                                        <%-- A场地 B时间 的状态 结束--%>
                                        <td style="color: #000;" status="${status}" data-item="${itemId}"
                                            class="reserveTd <j:if test="${'0' eq status}">access</j:if> <j:ifelse test="${'4' eq status}"><j:then>red</j:then><j:else><j:if test="${'1' eq halfCourt}">unpayed</j:if></j:else></j:ifelse>"
                                            data-price="${price}"
                                            data-field="${file.fieldId}"
                                            data-time="${t}"
                                        >
                                                <span id="">${username}</span>
                                        </td>
                                    </c:forEach>
                                        <%-- 横坐标：时间 结束--%>
                                </tr>
                                <%-- 纵坐标：场地 结束--%>
                            </c:forEach>

                            </tbody>
                        </table>
                    </div>

                    <script type="text/javascript">
                        $(".table-left-l ul").css("padding-top", parseInt($(".table-chang thead").eq(0).height()) + 3 + "px");
                    </script>

                </div>
            </div>
        </div>

    </div>
</div>
<%@include file="../include/modal.jsp" %>
<!--end dialog-->
<script>
    document.write("<script type='text/javascript' src='${ctxStatic}/modules/reserve/js/reserve_field.js?t=" + Math.random() + "'><\/script>");

    $("#form-primary").draggable({
        handle: ".modal-header"
    });

    $("#myModal").draggable({
        handle: ".modal-header"
    });
</script>

</body>
</html>

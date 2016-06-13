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
    <script type="text/javascript">
        var ctx = '${ctx}', consDate = '${consDate.time}', venueId = '${reserveVenue.id}';
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="reserveFieldStatus"></jsp:param>
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
                                        href="${ctx}/reserve/field/status?venueId=${venue.id}&t=${consDate.time}">${venue.name}</a>
                                </li>
                            </c:forEach>
                        </div>
                        <%-- 场馆--%>
                    </ul>
                    <ul class="table-ul">
                        <li style="margin-left: 0px;"><span class="green-bg-color"></span>可预订</li>
                        <%-- <li><span class="blue-bg-color"></span>已选场次</li>--%>
                        <li><span class="grey-bg-color"></span>已占用</li>
                        <li><span class="ticketOccupation"></span>场次票占用</li>
                        <li><span class="red-bg-color"></span>已付款</li>
                        <li><span class="normal"></span>空场审核通过</li>
                        <li><span class="fullFieldHasAbnormal"></span>空场审核未通过</li>

                    </ul>
                </div>
                <%-- 周几，日期--%>
                <div class="row">
                    <div class="col-lg-7">
                        <div class="tab-tit">
                            <ul>
                                <div id="timeSlotDiv">
                                    <c:forEach items="${timeSlot}" var="slot" varStatus="status">
                                        <li
                                                <j:if test="${consDate.time eq slot.value}">class="on"</j:if> >
                                            <a href="${ctx}/reserve/field/status?venueId=${reserveVenue.id}&t=${slot.value}">${slot.key}</a>
                                        </li>
                                    </c:forEach>
                                </div>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-5">
                        <div class="tab-time">
                            <form:form id="searchForm"
                                       action="${ctx}/reserve/field/status"
                                       method="post">
                                <div class="row">
                                    <div class="col-lg-4">
                                        <input name="venueId" type="hidden" value="${reserveVenue.id}"/>
                                    </div>
                                    <div class="col-lg-2">
                                        <label for="consDate" class="control-label">预订日期:</label>
                                    </div>
                                    <div class="col-lg-4">
                                        <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${consDate}"/>"
                                               id="consDate" name="consDate" type="text"
                                               class="input-small form-control Wdate col-lg-1"
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                    </div>
                                    <div class="col-lg-2">
                                        <input id="btnSubmit" class="btn btn-primary" type="submit"
                                               value="查询"/>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>

                <%-- 周几，日期 结束--%>

                <div class="sy-tab-cont">

                    <div class="table-left">
                        <%@include file="reserveDayField.jsp" %>
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
<%@include file="../include/checkEmptyFormModal.jsp" %>
<!--end dialog-->
<script>
    document.write("<script type='text/javascript' src='${ctxStatic}/modules/reserve/js/reserve_field_status.js?t=" + Math.random() + "'><\/script>");
</script>
</body>
</html>

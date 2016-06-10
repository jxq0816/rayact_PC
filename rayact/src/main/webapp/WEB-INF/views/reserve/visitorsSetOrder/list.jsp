<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title></title>
    <meta name="decorator" content="main"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=7862256"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="vistorsSet"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="block-flat">
        <div class="tab-tit-first">
            <ul>
                <li <j:if test="${empty projectId}">class="on"</j:if>><a
                        href="${ctx}/reserve/reserveVenueOrder/list">所有</a>
                </li>
                <c:forEach items="${projects}" var="project" varStatus="status">
                    <li <j:if test="${project.id eq projectId}">class="on"</j:if>><a
                            href="${ctx}/reserve/reserveVenueOrder/list?project.id=${project.id}">${project.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <%-- <div class="cl-mcont">--%>

        <div class="row">
            <div class="col-md-12">
                <c:forEach items="${visitorsSets}" var="vs">
                <div class="col-sm-4 col-md-4 col-lg-2">

                    <div class="header">
                        <h3 class="visible-lg">${vs.reserveVenue.name}</h3>
                        <h3 class="visible-lg">${vs.name}</h3>
                    </div>
                    <div class="content">
                        <p><b>${vs.project.name}</b><b>(${vs.price}<i class="fa fa-cny"></i>)</b></p>
                        <p>
                            <button type="button" onclick="addToCart('${vs.id}','${vs.reserveVenue.id}')"
                                    class="btn btn-default btn-twitter bg">
                                <i class="fa fa-shopping-cart"></i></button>
                        </p>
                    </div>
                </div>
            </c:forEach>
            </div>
        </div>
    </div>
    <%-- </div>--%>
</div>
<%--场次票售卖模态--%>
<button id="timeTicketBtn" style="display: none" class="btn btn-primary btn-large" href="#timeTicketDialogModal" data-toggle="modal">场次票售卖
</button>
<div class="modal fade" id="timeTicketDialogModal"  style="display: none;" aria-hidden="true">
    <div class="modal-dialog" style="width: 80%">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="settlementModalLabel">场次票售卖</h4>
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="reserve_top_line">

                </div>
                <div class="modal-body form-horizontal" id="reserveForm">
                    <!--结账模态-->
                    <!--end 结账模态-->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="closeBtn" class="btn btn-default btn-flat md-close"
                        data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="saveBtn" class="btn btn-primary btn-flat">确认</button>
            </div>
        </div>
    </div>
</div>
<%--结账模态--%>
<script src="${ctxStatic}/modules/reserve/js/time_ticket_form.js" type="text/javascript"></script>

</body>
</html>
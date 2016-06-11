<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="row">
    <div class="control-label col-lg-2">场馆：</div>
    <div class="col-lg-2 form-control-static">
        ${ticketList[0].reserveVenue.name}
    </div>
    <div class="control-label col-lg-2">场地：</div>
    <div class="col-lg-2 form-control-static">
        ${ticketList[0].reserveField.name}
    </div>
</div>
<c:forEach items="${ticketList}" var="ticket">
    <div class="row">
        <div class="control-label col-lg-2">时间：</div>
        <div class="col-lg-2 form-control-static">
                ${ticket.startTime}-${ticket.endTime}
        </div>
        <div class="control-label col-lg-2">场次票个数：</div>
        <div class="col-lg-2  form-control-static">
                ${ticket.collectCount}
        </div>
        <div class="control-label col-lg-2">场次票单价：</div>
        <div class="col-lg-2  form-control-static">
                ${ticket.orderPrice}
        </div>
    </div>
</c:forEach>
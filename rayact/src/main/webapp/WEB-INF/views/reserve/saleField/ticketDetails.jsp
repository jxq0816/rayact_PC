<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
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
    </div>
</c:forEach>
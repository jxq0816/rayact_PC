<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div style="text-align: center">四得体育</div>
<table border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>姓名：${venueCons.member.name}</td>
    </tr>
    <tr>
        <td>场馆：${venueCons.reserveVenue.name}</td>
    </tr>
    <tr>
        <td>收银员：${venueCons.updateBy.name}</td>
    </tr>
    <tr>
        <td><fmt:formatDate value="${venueCons.updateDate}" type="both"/></td>
    </tr>
    <c:forEach items="${venueCons.venueConsList}" var="item">
        <tr>
            <td>场地：${item.reserveField.name}</td>
            <td>预订时间：${item.startTime}-${item.endTime}</td>
            <td>费用：${item.orderPrice}</td>
        </tr>
    </c:forEach>
    <c:forEach items="${venueCons.tutorOrderList}" var="item">
        <tr>
            <td>教练：${item.tutor.name}</td>
            <td>价格：${item.orderPrice*2} 元/小时</td>
            <td>费用：${item.totalPrice}</td>
        </tr>
    </c:forEach>
    <tr>
        <td>应收：${venueCons.shouldPrice}</td>
        <td>优惠：${venueCons.discountPrice}</td>
        <td>实收：${venueCons.consPrice}</td>
    </tr>

</table>
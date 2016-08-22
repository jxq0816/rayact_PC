<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/jquery.jqprint-0.3.js"></script>
<script language="javascript">
    function printSettlement() {
        $("#printSettlement").jqprint();
    }
</script>
<div id="printSettlement" class="row" style="display: none">
    <div style="text-align: center">${companyName}</div>
    <table class="no-border">
        <tbody class="no-border-y">
        <tr>
            <td style="border-bottom:0;">姓名：${venueCons.userName}</td>
        </tr>
        <tr>
            <td style="border-bottom:0;">场馆：${venueCons.reserveVenue.name}</td>
        </tr>
        <tr>
            <td style="border-bottom:0;">收银员：${venueCons.updateBy.name}</td>
        </tr>
        <tr>
            <td style="border-bottom:0;"><fmt:formatDate value="${venueCons.updateDate}" type="both"/></td>
        </tr>
        <c:forEach items="${venueCons.venueConsList}" var="item">
            <tr>
                <td class="no-border-bottom">场地：${item.reserveField.name}</td>
                <td class="no-border">预订时间：${item.startTime}-${item.endTime}</td>
                <td class="no-border">费用：${item.orderPrice}</td>
            </tr>
        </c:forEach>
        <c:forEach items="${venueCons.tutorOrderList}" var="item">
            <tr>
                <td class="no-border">教练：${item.tutor.name}</td>
                <td class="no-border">价格：${item.orderPrice*2} 元/小时</td>
                <td class="no-border">费用：${item.totalPrice}</td>
            </tr>
        </c:forEach>
        <tr>
            <td class="no-border">应收：${venueCons.shouldPrice}</td>
            <td class="no-border">优惠：${venueCons.discountPrice}</td>
            <td class="no-border">实收：${venueCons.consPrice}</td>
        </tr>
        <tr>
            <td class="no-border">会员余额：${venueCons.member.remainder}</td>
        </tr>
        </tbody>
    </table>
</div>
<div class="row">
    <div class="col-lg-10">
    </div>
    <div class="col-lg-2">
        <input type="button" class="btn btn-primary" onclick="printSettlement()" value="打印小票"/>
    </div>
</div>
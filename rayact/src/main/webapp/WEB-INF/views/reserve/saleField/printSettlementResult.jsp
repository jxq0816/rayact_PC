<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/jquery.jqprint-0.3.js"></script>
<script language="javascript">
    function printSettlement() {
        $("#printSettlement").jqprint();
    }
</script>
<div style="display: none">
    <div id="printSettlement" style="font-size: 10px">
        <div style="text-align: center;font-size:18px;margin-bottom: 10px">${companyName}</div>
        收银员：${venueCons.updateBy.name}
        <br/>
        <fmt:formatDate value="${venueCons.updateDate}" type="both"/>
        <br/>
        ====================
        <br/>
        场馆：${venueCons.reserveVenue.name}
        <br/>
        <c:forEach items="${venueCons.venueConsList}" var="item">
            场地：${item.reserveField.name}<br/>
            时段：${item.startTime}-${item.endTime}<br/>
            场地费用：${item.orderPrice}<br/>
        </c:forEach>

        <c:forEach items="${venueCons.tutorOrderList}" var="item">
            ====================<br/>
            教练：${item.tutor.name}<br/>
            价格：${item.orderPrice*2} 元/小时<br/>
            教练费用：${item.totalPrice}<br/>
        </c:forEach>
        ====================
        <br/>
        应收：${venueCons.shouldPrice}<br/>
        优惠：${venueCons.discountPrice}<br/>
        实收：${venueCons.consPrice}<br/>
        会员余额：${venueCons.member.remainder} <br/>

        ====================
        <br/>
        ${venueCons.userName}
        <br/>
        <div style="text-align: center;font-size:18px">谢谢惠顾</div>
    </div>
</div>
<div class="row">
    <div class="col-lg-10">
    </div>
    <div class="col-lg-2">
        <input type="button" class="btn btn-primary" onclick="printSettlement()" value="打印小票"/>
    </div>
</div>
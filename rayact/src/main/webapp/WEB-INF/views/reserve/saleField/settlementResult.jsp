<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/jquery.jqprint-0.3.js"></script>
<script language="javascript">
    function printSettlement() {
        $("#printSettlement").jqprint();
    }
</script>
<div id="printSettlement" class="row">
    <div style="text-align: center">四得体育</div>
    <div class="row" style="font-size: 15px">
        <div class="row">
            <div class="col-lg-4">姓名：${venueCons.member.name}</div>
        </div>
        <div class="row">
            <div class="col-lg-4">场馆：${venueCons.reserveVenue.name}</div>
        </div>
        <div class="row">
            <div class="col-lg-4">收银员：${venueCons.updateBy.name}</div>
        </div>
        <div class="row">
            <div class="col-lg-4"><fmt:formatDate value="${venueCons.updateDate}" type="both"/></div>
        </div>
        <div class="row">
            <div class="col-lg-2">场地</div>
            <div class="col-lg-2">预订时间</div>
            <div class="col-lg-2">费用</div>
        </div>
        <c:forEach items="${venueCons.venueConsList}" var="item">
            <div class="row">
                <div class="col-lg-2">${item.reserveField.name}</div>
                <div class="col-lg-2">${item.startTime}-${item.endTime}</div>
                <div class="col-lg-2">${item.orderPrice}</div>
            </div>
        </c:forEach>
       <j:if test="${fn:length(venueCons.tutorOrderList)!=0}">
           <div class="row">
               <div class="col-lg-2">教练</div>
               <div class="col-lg-2">价格</div>
               <div class="col-lg-2">费用</div>
           </div>
       </j:if>
        <c:forEach items="${venueCons.tutorOrderList}" var="item">
            <div class="row">
                <div class="col-lg-2">${item.tutor.name}</div>
                <div class="col-lg-2">${item.orderPrice*2} 元/小时</div>
                <div class="col-lg-2">${item.totalPrice}</div>
            </div>
        </c:forEach>
        <div class="row">
            <div class="col-lg-2">应收：${venueCons.shouldPrice}元</div>
            <div class="col-lg-2">优惠：${venueCons.discountPrice}元</div>
            <div class="col-lg-2">实收：${venueCons.consPrice}元</div>
        </div>
        </table>
    </div>
</div>
<div class="row">
    <div class="col-lg-10">
    </div>
    <div class="col-lg-2">
        <input type="button" class="btn btn-primary" onclick="printSettlement()" value="打印小票"/>
    </div>
</div>
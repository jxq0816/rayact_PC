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
        收银员：${sellReport.sellDetailList[0].updateBy.name}
        <br/>
        <fmt:formatDate value="${sellReport.sellDetailList[0].updateDate}" type="both"/>
        <br/>
        ====================
        <br/>
        <c:forEach items="${sellReport.sellDetailList}" var="sellDetail">
            商品名称：${sellDetail.reserveCommodity.name} <br>
            单价：${sellDetail.price}<br>
            数量：${sellDetail.num}<br>
            合计：${sellDetail.detailSum}<br>
        </c:forEach>
        总计：${sellReport.totalSum}元<br>
        ====================<br>
        ${sellReport.sellDetailList[0].reserveMember.name}<br>
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
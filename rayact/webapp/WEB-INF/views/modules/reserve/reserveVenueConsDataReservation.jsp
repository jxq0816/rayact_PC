<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>场地预定管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {

        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/reserve/reserveVenueConsData/order">场地预定管理</a></li>
</ul>
<form:form id="searchForm" modelAttribute="reserveVenueConsData" action="${ctx}/reserve/reserveVenueConsData/reservation" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>订单号或订单人：</label>
            <form:input path="searchValue" htmlEscape="false" maxlength="30" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>订单编号</th>
        <th>预定人</th>
        <th>预定日期</th>
        <th>场馆</th>
        <th>订单金额</th>
        <th>实收金额</th>
        <th>优惠金额</th>
        <th>创建时间</th>
        <th>备注</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="reserveVenueConsData">
        <tr>
            <td>${reserveVenueConsData.orderNo}</td>
            <td>${reserveVenueConsData.userName}</td>
            <td><fmt:formatDate value="${reserveVenueConsData.consDate}" pattern="yyyy-MM-dd"/></td>
            <td>${reserveVenueConsData.reserveVenue.name}</td>
            <td>${reserveVenueConsData.orderPrice}</td>
            <td>${reserveVenueConsData.collectPrice}</td>
            <td>${reserveVenueConsData.discountPrice}</td>
            <td><fmt:formatDate value="${reserveVenueConsData.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>
                    ${reserveVenueConsData.remarks}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
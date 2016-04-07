<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>门户</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value=""></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-lg-6" align="center">
                <a href="${ctx}/reserve/storedCardMember/list"><img src="${ctxStatic}/modules/reserve/images/member_incharge.png"/></a>
        </div>
        <div class="col-lg-6" align="center">
            <a type="button" href="${ctx}/reserve/field/main"><img src="${ctxStatic}/modules/reserve/images/sale_field.png" /></a>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-6" align="center">
            <a type="button" href="${ctx}/reserve/reserveVenueOrder/list"><img src="${ctxStatic}/modules/reserve/images/sale_time_filed.png" /></a>
        </div>
        <div class="col-lg-6" align="center">
            <a type="button" href="${ctx}/reserve/commodity/onShelfList"><img src="${ctxStatic}/modules/reserve/images/sale_commodity.png" /></a>
        </div>
    </div>
</div>

<script src="${ctxStatic}/echart/echarts-all.js"></script>

<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.js"></script>
<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.pie.js"></script>
<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.resize.js"></script>
<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.labels.js"></script>
</body>
</html>

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
    <div class="cl-mcont">

        <!--当天得会员充值;场地售卖;商品售卖-->

        <div class="row">
            <c:forEach items="${fns:getAuthByUser(fns:getUser())}" var="auth">
                <div class="col-lg-12">
                        <%-- <li class="">--%>
                    <span style="margin-left:30px;margin-bottom:10px">${auth.name}</span>
                    <ul class="list-group">
                        <div class="col-lg-12 col-sm-12">
                            <c:forEach items="${auth.authorityList}" var="a">
                                <a href="${ctx}${a.href}">
                                    <div class="col-lg-2 col-sm-3" >
                                        <li class="list-group-item" style="text-align:center"><span style="color:#3c3e43;">${a.name}</span></li>
                                    </div>
                                </a>
                            </c:forEach>
                        </div>
                    </ul>

                    <ul class="sub-menu">

                    </ul>
                        <%--</li>--%>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/main.js"></script>

<script src="${ctxStatic}/echart/echarts-all.js"></script>

<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.js"></script>
<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.pie.js"></script>
<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.resize.js"></script>
<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.labels.js"></script>
</body>
</html>

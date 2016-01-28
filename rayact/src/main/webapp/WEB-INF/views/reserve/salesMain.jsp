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
        <div class="col-sm-4 col-md-4 col-lg-2">
            <div class="block-flat">
                <div class="header">
                    <h3 class="visible-sm visible-md">会员充值</h3>

                    <h3 class="visible-lg">会员充值</h3>
                </div>
                <div class="content">
                    <p>
                        <a type="button" href="${ctx}/reserve/storedCardMember/list"
                           class="btn btn-default btn-cart btn-twitter bg"><i
                                class="fa fa-shopping-cart"></i></a>
                    </p>
                </div>
            </div>
        </div>
        <div class="col-sm-4 col-md-4 col-lg-2">
            <div class="block-flat">
                <div class="header">
                    <h3 class="visible-sm visible-md">场地售卖</h3>

                    <h3 class="visible-lg">场地售卖</h3>
                </div>
                <div class="content">
                    <p>
                        <a type="button" href="${ctx}/reserve/field/main"
                           class="btn btn-default btn-cart btn-twitter bg"><i
                                class="fa fa-shopping-cart"></i></a>
                    </p>
                </div>
            </div>
        </div>

        <div class="col-sm-4 col-md-4 col-lg-2">
            <div class="block-flat">
                <div class="header">
                    <h3 class="visible-sm visible-md">人次售卖</h3>

                    <h3 class="visible-lg">人次售卖</h3>
                </div>
                <div class="content">
                    <p>
                        <a type="button" href="${ctx}/reserve/reserveVenueOrder/list"
                           class="btn btn-default btn-cart btn-twitter bg"><i
                                class="fa fa-shopping-cart"></i></a>
                    </p>
                </div>
            </div>
        </div>
        <div class="col-sm-4 col-md-4 col-lg-2">
            <div class="block-flat">
                <div class="header">
                    <h3 class="visible-sm visible-md">商品售卖</h3>

                    <h3 class="visible-lg">商品售卖</h3>
                </div>
                <div class="content">
                    <p>
                        <a type="button" href=""
                           class="btn btn-default btn-cart btn-twitter bg"><i
                                class="fa fa-shopping-cart"></i></a>
                    </p>
                </div>
            </div>
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

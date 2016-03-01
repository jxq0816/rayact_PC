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
        <div class="row dash-cols">
            <div class="col-sm-6 col-md-6 col-lg-3">
                <div class="block">
                    <div class="header">
                        <i class="fa fa-comment"></i><span style="font-size: 20px">会员充值</span>
                    </div>
                    <div class="content no-padding">
                        <div class="fact-data text-center">
                            <h3>当天</h3>
                            <h2><a href="javascript:queryByDayForMemberIncome()">${rechargeOfDay}</a></h2>
                        </div>
                        <div class="fact-data text-center">
                            <h3>当月</h3>
                            <h2><a href="javascript:queryByMonthForMemberIncome()">${rechargeOfMonth}</a></h2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-3">
                <div class="block">
                    <div class="header">
                        <i class="fa fa-bug"></i><span style="font-size: 20px">场地售卖</span>
                    </div>
                    <div class="content no-padding">
                        <div class="fact-data text-center">
                            <h3>当天</h3>
                            <h2><a href="${ctx}/reserve/saleVenueReport/list?search=1">${fieldTodayPrice}</a></h2>
                        </div>
                        <div class="fact-data text-center">
                            <h3>当月</h3>
                            <h2><a href="${ctx}/reserve/saleVenueReport/list?search=2">${fieldMonthPrice}</a></h2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-3">
                <div class="block">
                    <div class="header">
                        <i class="fa fa-comment"></i><span style="font-size: 20px">商品售卖</span>
                    </div>
                    <div class="content no-padding">
                        <div class="fact-data text-center">
                            <h3>当天</h3>

                            <h2><a href="javascript:queryByDayForCommoditySell()">${sellOfToday}</a></h2>
                        </div>
                        <div class="fact-data text-center">
                            <h3>当月</h3>

                            <h2><a href="javascript:queryByMonthForCommoditySell()">${sellOfMonth}</a></h2>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 col-md-6 col-lg-3">
                <div class="block">
                    <div class="header">
                        <i class="fa fa-comment"></i><span style="font-size: 20px">新增会员</span>
                    </div>
                    <div class="content no-padding">
                        <div class="fact-data text-center">
                            <h3>当月</h3>
                            <h2>${registerNum}</h2>
                        </div>
                        <div class="fact-data text-center">
                            <h3>总数</h3>
                            <h2>${memberNum}</h2>
                        </div>
                    </div>
                </div>
            </div>
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

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

        <!--当天得会员充值;场地售卖;商品售卖-->
        <div class="row dash-cols">
            <div class="col-sm-6 col-md-6 col-lg-4">
                <div class="block">
                    <div class="header">
                        <h2><i class="fa fa-comment"></i>会员充值</h2>
                    </div>
                    <div class="content no-padding">
                        <div class="fact-data text-center">
                            <h3>当天</h3>

                            <h2>${rechargeOfDay}</h2>
                        </div>
                        <div class="fact-data text-center">
                            <h3>当月</h3>

                            <h2>${rechargeOfMonth}</h2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-4">
                <div class="block">
                    <div class="header">
                        <h2><i class="fa fa-bug"></i>场地售卖</h2>
                    </div>
                    <div class="content no-padding">
                        <div class="fact-data text-center">
                            <h3>当天</h3>

                            <h2>${fieldTodayPrice}</h2>
                        </div>
                        <div class="fact-data text-center">
                            <h3>当月</h3>

                            <h2>${fieldMonthPrice}</h2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-4">
                <div class="block">
                    <div class="header">
                        <h2><i class="fa fa-comment"></i>商品售卖</h2>
                    </div>
                    <div class="content no-padding">
                        <div class="fact-data text-center">
                            <h3>当天</h3>

                            <h2>${sellOfToday}</h2>
                        </div>
                        <div class="fact-data text-center">
                            <h3>当月</h3>

                            <h2>${sellOfMonth}</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--报表-->
        <div class="row dash-cols">

            <div class="col-sm-6 col-md-6">
                <div class="block">
                    <div class="header no-border">
                        <h2>会员充值</h2>
                    </div>
                    <div class="content">
                        <div id="member_statistics" style="height:280px;"></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 col-md-6">
                <div class="block">
                    <div class="header no-border">
                        <h2>场地售卖</h2>
                    </div>
                    <div class="content">
                        <div id="field_statistics" style="height:280px;"></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 col-md-6">
                <div class="block">
                    <div class="header no-border">
                        <h2>项目占比</h2>
                    </div>
                    <div class="content">
                        <div id="project_statistics" style="height:280px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        //initialize the javascript
        App.dashBoard();
        //App.charts();
        //introJs().setOption('showBullets', false).start();
        //场地售卖
        var fieldChart = echarts.init(document.getElementById('field_statistics'));
        fieldoption = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['场地售卖']
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: [${fieldChartMapX}]
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '场地售卖',
                    type: 'line',
                    stack: '总量',
                    data: [${fieldChartMapY}]
                }
            ]
        };
        fieldChart.setOption(fieldoption);
        //会员
        var memberChart = echarts.init(document.getElementById('member_statistics'));
        memeberoption = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['会员充值']
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: [${memberChartMapX}]
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '会员充值',
                    type: 'line',
                    stack: '总量',
                    data: [${memberChartMapY}]
                }
            ]
        };
        memberChart.setOption(memeberoption);

        //项目占比
        var projectChart = echarts.init(document.getElementById('project_statistics'));
        projectOption = {
            title : {
                text: '项目销售占比',
                subtext: '',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'vertical',
                x : 'left',
                data:[${projectFieldNameList}]
            },
            calculable : true,
            series : [
                {
                    name:'项目销售占比',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:${projectFieldChart}
                }
            ]
        };
        projectChart.setOption(projectOption);
    });
</script>
<script src="${ctxStatic}/echart/echarts-all.js"></script>

<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.js"></script>
<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.pie.js"></script>
<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.resize.js"></script>
<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.labels.js"></script>
</body>
</html>

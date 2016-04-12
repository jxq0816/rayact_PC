<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>门户</title>
</head>
<body>
<c:if test="${param.alone != 'true'}">
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="dataListener"></jsp:param>
</jsp:include>
</c:if>
<div class="container-fluid" id="pcont">
    <div class="cl-mcont" style="padding-left: 20px">

        <!--当天得会员充值;场地售卖;商品售卖-->
        <div class="row dash-cols">
            <div class="col-sm-6 col-md-6 col-lg-3">
                <div class="block">
                    <div class="header"style="background:#5e3bb9;color: #fff;text-align: center">
                        <i class="fa"></i><span style="font-size: 20px">会员充值</span>
                    </div>
                    <div class="content no-padding" style="background:#5e3bb9;color: #fff">
                        <div class="fact-data text-center">
                            <h3>当天</h3>
                            <h2><a style="color:#fff " href="javascript:queryByDayForMemberIncome()">${rechargeOfDay}</a></h2>
                        </div>
                        <div class="fact-data text-center">
                            <h3>当月</h3>
                            <h2><a style="color:#fff " href="javascript:queryByMonthForMemberIncome()">${rechargeOfMonth}</a></h2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-3">
                <div class="block">
                    <div class="header" style="background:#00a400;color: #fff;text-align: center">
                        <i class="fa"></i><span style="font-size: 20px">场地售卖</span>
                    </div>
                    <div class="content no-padding" style="background:#00a400;color: #fff">
                        <div class="fact-data text-center">
                            <h3>当天</h3>
                            <h2><a style="color:#fff " href="${ctx}/reserve/saleVenueReport/list?alone=${alone}&search=1">${fieldTodayPrice}</a></h2>
                        </div>
                        <div class="fact-data text-center">
                            <h3>当月</h3>
                            <h2><a style="color:#fff " href="${ctx}/reserve/saleVenueReport/list?alone=${alone}&search=2">${fieldMonthPrice}</a></h2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-3">
                <div class="block">
                    <div class="header" style="background:#004bc0;color: #fff;text-align: center">
                        <i class="fa"></i><span style="font-size: 20px">商品售卖</span>
                    </div>
                    <div class="content no-padding" style="background:#004bc0;color: #fff">
                        <div class="fact-data text-center">
                            <h3>当天</h3>

                            <h2><a href="javascript:queryByDayForCommoditySell()" style="color:#fff ">${sellOfToday}</a></h2>
                        </div>
                        <div class="fact-data text-center">
                            <h3>当月</h3>

                            <h2><a href="javascript:queryByMonthForCommoditySell()" style="color:#fff ">${sellOfMonth}</a></h2>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 col-md-6 col-lg-3">
                <div class="block">
                    <div class="header" style="background:#8644ac;color: #fff;text-align: center">
                        <i class="fa"></i><span style="font-size: 20px">新增会员</span>
                    </div>
                    <div class="content no-padding" style="background:#8644ac;color: #fff">
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
        <!--报表-->
        <div class="row dash-cols">

            <div class="col-sm-6 col-md-6">
                <div class="block">
                    <div class="content" style="padding: 30px">
                        <div class="header no-border" style="text-align: right;">
                            <form:form id="searchForm" name="searchForm" modelAttribute="reserveCardStatements"
                                       action="${ctx}/reserve/reserveSellReport/listChart"
                                       method="post" class="form-horizontal form-inline">
                                开始时间：<input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveCardStatements.startDate}"/>"
                                name="startDate" id="startDate" type="text"
                                class="input-medium form-control Wdate "
                                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                结束时间：<input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveCardStatements.endDate}"/>"
                                name="endDate" id="endDate" type="text"
                                class="input-medium form-control Wdate "
                                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                <input id="btnSubmit" class="btn btn-primary" type="button"
                                       value="查询"/>
                            </form:form>
                        </div>
                        <div id="all_report" style="height:400px"></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 col-md-6">
                <div class="block">
                    <div class="content">
                        <div id="field_statistics" style="height:280px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('all_report'));
    myChart.on('click', function (param) {
        //alert(json.stringify(param));//这里根据param填写你的跳转逻辑
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        location.href = "${ctx}/reserve/reserveSellReport/list?alone=${alone}&startDate="+startDate+"&endDate="+endDate;
    });
    var drawChart = function(){
        $.post('${ctx}/reserve/reserveSellReport/listChart',$('#searchForm').serialize(),function(data){
            var vs = data.reserveVenueList;
            var vschart = new Array();
            for(var i = 0 ; i < vs.length ; i++){
                vschart.push(vs[i].name);
            }
            var rtn = data.rtn;
            var rtnchart = new Array();
            for(var j = 0 ; j < rtn.length ; j++){
                var tmp = {};
                tmp.name = rtn[j].venue_name;
                tmp.value = parseFloat(rtn[j].commodity_in)+parseFloat(rtn[j].field_in)+parseFloat(rtn[j].store_in)-parseFloat(rtn[j].store_cost)-parseFloat(rtn[j].time_cost);
                rtnchart.push(tmp);
            }
            // 指定图表的配置项和数据
            option = {
                title : {
                    text: '场馆收入统计',
                    subtext: '',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: vschart
                },
                series : [
                    {
                        name: '收入来源',
                        type: 'pie',
                        radius : '80%',
                        center: ['50%', '60%'],
                        data:rtnchart,
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        });
    }
    $(function(){
        drawChart();
    });
    $("#btnSubmit").on("click",function(){
        drawChart();
    });
    $(document).ready(function () {
        //initialize the javascript
        App.dashBoard();
        //场地售卖
        var fieldChart = echarts.init(document.getElementById('field_statistics'));
        fieldOption = {
            title : {
                text: '场馆收入统计',
                subtext: ''
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:[${venueListJson}]
            },
            toolbox: {
                show : true,
                feature : {
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                ${seriesJson}
            ]
        };
        fieldChart.setOption(fieldOption);
    });
    var now;
    var nowMonth;
    $(document).ready(function () {
        now = new Date();
        nowMonth = now.getMonth();
        nowYear = now.getFullYear();
    })
    //格式化日期：yyyy-MM-dd
    function formatDate(date) {
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();

        if (month < 10) {
            month = "0" + month;
        }
        if (day < 10) {
            day = "0" + day;
        }
        return (year + "-" + month + "-" + day);
    }
    //获得某月的天数
    function getMonthDays(myMonth) {
        var monthStartDate = new Date(nowYear, myMonth, 1);
        var monthEndDate = new Date(nowYear, myMonth + 1, 1);
        var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
        return days;
    }
    //获得本月的开始日期
    function getMonthStartDate() {
        var monthStartDate = new Date(nowYear, nowMonth, 1);
        return formatDate(monthStartDate);
    }
    //获得本月的结束日期
    function getMonthEndDate() {
        var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
        return formatDate(monthEndDate);
    }

    function queryByDayForMemberIncome() {
        var startDate=formatDate(now);
        var endDate=formatDate(now);
        location.href=ctx + "/reserve/reserveCardStatements/memberIncomeReport?alone=${alone}&transactionType=1&startDate="+startDate+"&endDate="+endDate;
    }
    function queryByMonthForMemberIncome() {
        var startDate=getMonthStartDate();
        var endDate=getMonthEndDate();
        location.href=ctx + "/reserve/reserveCardStatements/memberIncomeReport?alone=${alone}&transactionType=1&startDate="+startDate+"&endDate="+endDate;
    }

    function queryByDayForCommoditySell() {
        var startDate=formatDate(now);
        var endDate=formatDate(now);
        location.href=ctx + "/reserve/reserveCommoditySell/commodityIncomeIntervalReport?alone=${alone}&startDate="+startDate+"&endDate="+endDate;
    }
    function queryByMonthForCommoditySell() {
        var startDate=getMonthStartDate();
        var endDate=getMonthEndDate();
        location.href=ctx + "/reserve/reserveCommoditySell/commodityIncomeIntervalReport?alone=${alone}&&startDate="+startDate+"&endDate="+endDate;
    }
</script>

<script src="${ctxStatic}/echart/echarts-all.js"></script>

<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.js"></script>
<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.pie.js"></script>
<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.resize.js"></script>
<script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.flot/jquery.flot.labels.js"></script>
</body>
</html>

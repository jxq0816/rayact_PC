<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=7862256"/>
    <title>商品收入统计</title>
</head>
<body>
<c:if test="${param.alone != 'true'}">
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="commodityIncomeRecord"></jsp:param>
</jsp:include>
</c:if>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>商品收入统计</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveCommodityIntervalReport"
                           action="${ctx}/reserve/reserveCommoditySell/commodityIncomeIntervalReport?alone=${alone}"
                           method="post" class="breadcrumb form-search">
                    <div class="row">
                        <div class="col-sm-12 col-md-12 col-lg-12">

                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>场馆：</td>
                                    <td>
                                        <sys:select cssClass="input-large" name="reserveVenue.id"
                                                    cssStyle="width:100%"
                                                    value="${reserveCommodityIntervalReport.reserveVenue.id}"
                                                    items="${reserveVenueList}" itemLabel="name" itemValue="id"
                                                    defaultLabel="----请选择-----"
                                                    defaultValue=""></sys:select>
                                    </td>
                                    <td>商品类型：</td>
                                    <td>
                                        <sys:select cssClass="input-large" name="reserveCommodityType.id"
                                                    cssStyle="width:100%"
                                                    value="${reserveCommodityIntervalReport.reserveCommodityType.id}"
                                                    items="${reserveCommodityTypeList}" itemLabel="name" itemValue="id"
                                                    defaultLabel="----请选择-----"
                                                    defaultValue=""></sys:select>
                                    </td>

                                    <td colspan="3">
                                        <div class="btn-group" id="payType">
                                            <label class="radio-inline">
                                                <input type="radio" class="icheck" value="1"  name="queryType"/>汇总
                                            </label>

                                            <label class="radio-inline">
                                                <input type="radio" class="icheck" checked="checked" value="2" name="queryType"/>明细
                                            </label>
                                        </div>
                                    </td>

                                        <%--<td>
                                            <div class="tab-tit-first">
                                                <ul>
                                                    <li id="today"><a href="javascript:queryToday()">当天</a></li>
                                                    <li id="month"><a href="javascript:queryMonth()">当月</a></li>
                                                    <li id="year"><a href="javascript:queryYear()">当年</a></li>
                                                    <li id="self"><a href="javascript:querySelf()">自定义</a></li>
                                                </ul>
                                            </div>
                                        </td>--%>
                                    <td>
                                        <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveCommodityIntervalReport.startDate}"/>"
                                               name="startDate" id="startDate" type="text"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                    </td>
                                    <td>
                                        <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveCommodityIntervalReport.endDate}"/>"
                                               name="endDate" id="endDate" type="text"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                    </td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit"
                                               value="查询"/>
                                        <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </form:form>
                <sys:msg content="${message}"/>


                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>日期</th>
                                <th>商品</th>
                                <th>储值卡收入</th>
                                <th>现金收入</th>
                                <th>银行卡收入</th>
                                <th>微信收入</th>
                                <th>（个人）微信收入</th>
                                <th>支付宝收入</th>
                                <th>（个人）支付宝收入</th>
                                <th>欠账</th>
                                <th>其它</th>
                                <th>合计</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${intervalReports}" var="intervalReport">
                                <c:forEach items="${intervalReport.dayReportList}" var="dayReport">
                                    <tr>
                                        <td>
                                            <fmt:formatDate value="${dayReport.day}" timeStyle="date"></fmt:formatDate>
                                        </td>

                                        <td>
                                                ${dayReport.reserveCommodity.name}
                                        </td>


                                        <td>
                                                ${dayReport.storedCardBill}
                                        </td>

                                        <td>
                                                ${dayReport.cashBill}
                                        </td>

                                        <td>
                                                ${dayReport.bankCardBill}
                                        </td>

                                        <td>
                                                ${dayReport.weiXinBill}
                                        </td>

                                        <td>
                                                ${dayReport.personalWeiXinBill}
                                        </td>

                                        <td>
                                                ${dayReport.aliPayBill}
                                        </td>

                                        <td>
                                                ${dayReport.personalAliPayBill}
                                        </td>

                                        <td>
                                                ${dayReport.dueBill}
                                        </td>

                                        <td>
                                                ${dayReport.otherBill}
                                        </td>

                                        <td>
                                                ${dayReport.bill}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctxStatic}/modules/reserve/js/commodity_income_record.js" type="text/javascript"></script>
<script type="text/javascript">
    $("#btnExport").click(function(){
        $("#searchForm").attr("action","${ctx}/reserve/reserveCommoditySell/commodityIncomeIntervalReportExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action","${ctx}/reserve/reserveCommoditySell/commodityIncomeIntervalReport");
    });
</script>
</body>
</html>

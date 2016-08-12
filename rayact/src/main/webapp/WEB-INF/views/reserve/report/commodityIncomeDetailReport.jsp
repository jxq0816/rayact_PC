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
                                <th>储值卡</th>
                                <c:set var="storedCardBill" value="0"></c:set>
                                <th>现金</th>
                                <c:set var="cashBill" value="0"></c:set>
                                <th>银行卡</th>
                                <c:set var="bankCardBill" value="0"></c:set>
                                <th>转账</th>
                                <c:set var="transferBill" value="0"></c:set>
                                <th>微信</th>
                                <c:set var="weiXinBill" value="0"></c:set>
                                <th>微信（个人）</th>
                                <c:set var="personalWeiXinBill" value="0"></c:set>
                                <th>支付宝</th>
                                <c:set var="aliPayBill" value="0"></c:set>
                                <th>支付宝（个人）</th>
                                <c:set var="personalAliPayBill" value="0"></c:set>
                                <th>优惠券</th>
                                <c:set var="otherBill" value="0"></c:set>
                                <th>合计</th>
                                <c:set var="bill" value="0"></c:set>
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
                                                    <c:set var="storedCardBill" value="${storedCardBill+dayReport.storedCardBill}"></c:set>
                                        </td>

                                        <td>
                                                ${dayReport.cashBill}
                                                    <c:set var="cashBill" value="${cashBill+dayReport.cashBill}"></c:set>
                                        </td>

                                        <td>
                                                ${dayReport.bankCardBill}
                                                    <c:set var="bankCardBill" value="${bankCardBill+dayReport.bankCardBill}"></c:set>
                                        </td>

                                        <td>
                                                ${dayReport.transferBill}
                                                <c:set var="transferBill" value="${transferBill+dayReport.transferBill}"></c:set>
                                        </td>

                                        <td>
                                                ${dayReport.weiXinBill}
                                                    <c:set var="weiXinBill" value="${weiXinBill+dayReport.weiXinBill}"></c:set>
                                        </td>

                                        <td>
                                                ${dayReport.personalWeiXinBill}
                                                    <c:set var="personalWeiXinBill" value="${personalWeiXinBill+dayReport.personalWeiXinBill}"></c:set>
                                        </td>

                                        <td>
                                                ${dayReport.aliPayBill}
                                                    <c:set var="aliPayBill" value="${aliPayBill+dayReport.aliPayBill}"></c:set>
                                        </td>

                                        <td>
                                                ${dayReport.personalAliPayBill}
                                                    <c:set var="personalAliPayBill" value="${personalAliPayBill+dayReport.personalAliPayBill}"></c:set>
                                        </td>

                                        <td>
                                                ${dayReport.otherBill}
                                                    <c:set var="otherBill" value="${otherBill+dayReport.otherBill}"></c:set>
                                        </td>

                                        <td>
                                                ${dayReport.bill}
                                                    <c:set var="bill" value="${bill+dayReport.bill}"></c:set>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                            <tr>
                                <td colspan="2">合计</td>
                                <td>${storedCardBill}</td>
                                <td>${cashBill}</td>
                                <td>${bankCardBill}</td>
                                <td>${transferBill}</td>
                                <td>${weiXinBill}</td>
                                <td>${personalWeiXinBill}</td>
                                <td>${aliPayBill}</td>
                                <td>${personalAliPayBill}</td>
                                <td>${otherBill}</td>
                                <td>${bill}</td>
                            </tr>
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

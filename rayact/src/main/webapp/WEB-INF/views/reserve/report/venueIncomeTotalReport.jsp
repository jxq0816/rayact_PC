<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>总收入统计</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="totalIncomeReport"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>总收入统计</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveVenue" action="${ctx}/reserve/reserveVenue/totalIncomeReport"
                           method="post" class="breadcrumb form-search">
                    <div class="row">
                        <div class="col-sm-7 col-md-7 col-lg-7">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>场馆:</td>
                                    <td>
                                        <sys:select cssClass="input-large" name="reserveVenue.id"
                                                    value="${intervalTotalReport.reserveVenue.id}"
                                                    items="${reserveVenueList}" itemLabel="name" itemValue="id"
                                                    defaultLabel="----请选择-----"
                                                    defaultValue=""></sys:select>
                                    </td>
                                    <td>
                                        <label class="radio-inline">
                                            <input type="radio" class="icheck" value="0"
                                                   <j:if test="${'0' eq intervalTotalReport.queryType}">checked="checked"</j:if> name="queryType"/>流水
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" class="icheck" value="1"
                                                   <j:if test="${'1' eq intervalTotalReport.queryType}">checked="checked"</j:if> name="queryType"/>收益
                                        </label>
                                    <td>
                                        <input name="startDate" id="startDate" type="text"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               value="<fmt:formatDate value="${intervalTotalReport.startDate}" type="date"></fmt:formatDate>"
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                        </td>
                                    <td>
                                        <input name="endDate" id="endDate" type="text"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               value="<fmt:formatDate value="${intervalTotalReport.endDate}" type="date"></fmt:formatDate>"
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                    </td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </form:form>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>场馆</th>
                                <th>收入类型</th>
                                <th>储值卡</th>
                                <th>现金</th>
                                <th>银行卡</th>
                                <th>微信</th>
                                <th>支付宝</th>
                                <th>优惠券</th>
                                <th>欠账</th>
                                <th>合计</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%-- 总统计 开始 --%>
                            <c:set var="billStoredCard" value="0"/>
                            <c:set var="billCash" value="0"/>
                            <c:set var="billBankCard" value="0"/>
                            <c:set var="billWeiXin" value="0"/>
                            <c:set var="billAliPay" value="0"/>
                            <c:set var="billOther" value="0"/>
                            <c:set var="billDue" value="0"/>
                            <c:set var="bill" value="0"/>
                            <c:forEach items="${totalIntervalReportList}" var="report">
                            <tr>
                                <td>
                                    ${report.reserveVenue.name}
                                </td>
                                <td>
                                    ${report.transactionType}
                                </td>
                                <td>
                                    ${report.storedCardBill}
                                        <c:set var="billStoredCard" value="${billStoredCard+report.storedCardBill}"/>
                                </td>

                                <td>
                                    ${report.cashBill}
                                        <c:set var="billCash" value="${billCash+report.cashBill}"/>
                                </td>
                                <td>
                                    ${report.bankCardBill}
                                        <c:set var="billBankCard" value="${billBankCard+report.cashBill}"/>
                                </td>

                                <td>
                                    ${report.weiXinBill}
                                        <c:set var="billWeiXin" value="${billWeiXin+report.weiXinBill}"/>
                                </td>
                                <td>
                                    ${report.aliPayBill}
                                        <c:set var="billAliPay" value="${billAliPay+report.aliPayBill}"/>
                                </td>
                                <td>
                                    ${report.otherBill}
                                        <c:set var="billOther" value="${billOther+report.otherBill}"/>
                                </td>
                                <td>
                                    ${report.dueBill}
                                        <c:set var="billDue" value="${billDue+report.dueBill}"/>
                                </td>
                                <td>
                                    ${report.bill}
                                        <c:set var="bill" value="${bill+report.bill}"/>
                                </td>
                            </tr>
                            </c:forEach>
                            <%-- 总统计 结束 --%>
                            <tr>
                                <td>
                                </td>
                                <td>
                                </td>
                                <td>
                                    ${billStoredCard}
                                </td>

                                <td>
                                    ${billCash}
                                </td>
                                <td>
                                    ${billBankCard}
                                </td>

                                <td>
                                    ${billWeiXin}
                                </td>
                                <td>
                                    ${billAliPay}
                                </td>
                                <td>
                                    ${billOther}
                                </td>
                                <td>
                                    ${billDue}
                                </td>
                                <td>
                                    ${bill}
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
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
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

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
                                    <td>卡号类型:</td>
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
                                <th>其它</th>
                                <th>欠账</th>
                                <th>合计</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%-- 总统计 开始 --%>
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
                                </td>

                                <td>
                                    ${report.cashBill}
                                </td>
                                <td>
                                    ${report.bankCardBill}
                                </td>

                                <td>
                                    ${report.weiXinBill}
                                </td>
                                <td>
                                    ${report.aliPayBill}
                                </td>
                                <td>
                                    ${report.otherBill}
                                </td>
                                <td>
                                    ${report.dueBill}
                                </td>
                                <td>
                                    ${report.bill}
                                </td>
                            </tr>
                            </c:forEach>
                            <%-- 总统计 结束 --%>

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
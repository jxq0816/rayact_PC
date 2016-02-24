<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场馆报表</title>
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
                    <h3>场馆收入总报表</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveVenue" action="${ctx}/reserve/reserveVenue/report"
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
                                                    value="reserveVenue"
                                                    items="${reserveVenueList}" itemLabel="name" itemValue="id"
                                                    defaultLabel="----请选择-----"
                                                    defaultValue=""></sys:select>
                                    </td>
                                    <td>
                                        <input name="startDate" id="startDate" type="text"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               value="<fmt:formatDate value="${intervalReport.startDate}" type="date"></fmt:formatDate>"
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                        </td>
                                    <td>
                                        <input name="endDate" id="endDate" type="text"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               value="<fmt:formatDate value="${intervalReport.endDate}" type="date"></fmt:formatDate>"
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
                    场馆：${venue.name}
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>时间</th>
                                <th>储值卡</th>
                                <th>现金</th>
                                <th>银行卡</th>
                                <th>微信</th>
                                <th>支付宝</th>
                                <th>其它</th>
                                <th>欠账</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%-- 总统计 开始 --%>
                            <tr>
                                <td>
                                    <fmt:formatDate value="${intervalReport.startDate}" type="date"></fmt:formatDate>
                                    至
                                    <fmt:formatDate value="${intervalReport.endDate}" type="date"></fmt:formatDate>
                                </td>
                                <td>
                                    ${intervalReport.storedCardBill}
                                </td>

                                <td>
                                    ${intervalReport.cashBill}
                                </td>
                                <td>
                                    ${intervalReport.bankCardBill}
                                </td>

                                <td>
                                    ${intervalReport.weiXinBill}
                                </td>
                                <td>
                                    ${intervalReport.aliPayBill}
                                </td>
                                <td>
                                    ${intervalReport.otherBill}
                                </td>
                                <td>
                                    ${intervalReport.dueBill}
                                </td>
                            </tr>
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
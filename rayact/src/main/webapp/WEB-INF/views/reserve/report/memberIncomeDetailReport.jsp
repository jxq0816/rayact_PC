<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=7862256"/>
    <title>会员充值统计</title>
</head>
<body>
<c:if test="${param.alone != 'true'}">
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="memberIncomeReport"></jsp:param>
</jsp:include>
</c:if>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>会员充值统计</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveCardStatements"
                           action="${ctx}/reserve/reserveCardStatements/memberIncomeReport?alone=${alone}"
                           method="post" class="breadcrumb form-search">
                    <div class="row">
                        <div class="col-sm-12 col-md-12 col-lg-12">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <td>场馆：</td>
                                <td>
                                    <sys:select cssClass="input-large" name="reserveVenue.id"
                                                cssStyle="width:100%"
                                                value="${reserveMemberIntervalReport.reserveVenue.id}"
                                                items="${reserveVenueList}" itemLabel="name" itemValue="id"
                                                defaultLabel="----请选择-----"
                                                defaultValue=""></sys:select>
                                </td>

                                <td>
                                    <div class="btn-group" id="payType">
                                        <label class="radio-inline">
                                            <input type="radio" class="icheck" value="1"
                                                   name="queryType"/>汇总
                                        </label>

                                        <label class="radio-inline">
                                            <input type="radio" class="icheck" checked="checked" value="2"
                                                   name="queryType"/>明细
                                        </label>
                                    </div>
                                </td>
                                <td>
                                    <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveMemberIntervalReport.startDate}"/>"
                                           name="startDate" id="startDate" type="text"
                                           maxlength="20"
                                           class="input-medium form-control Wdate "
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                </td>
                                <td>
                                    <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveMemberIntervalReport.endDate}"/>"
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
                                <th>现金</th>
                                <th>银行卡</th>
                                <th>转账</th>
                                <th>微信</th>
                                <th>微信（个人）</th>
                                <th>支付宝</th>
                                <th>支付宝（个人）</th>
                                <th>优惠券</th>
                                <th>合计</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${intervalReports}" var="intervalReport">
                                <tr>
                                    <td>
                                        <fmt:formatDate value="${reserveMemberIntervalReport.startDate}"
                                                        timeStyle="date"></fmt:formatDate>至
                                        <fmt:formatDate value="${reserveMemberIntervalReport.endDate}"
                                                        timeStyle="date"></fmt:formatDate>

                                    </td>

                                    <td>
                                            ${intervalReport.cashBill}
                                    </td>

                                    <td>
                                            ${intervalReport.bankCardBill}
                                    </td>


                                    <td>
                                            ${intervalReport.transferBill}
                                    </td>

                                    <td>
                                            ${intervalReport.weiXinBill}
                                    </td>

                                    <td>
                                            ${intervalReport.personalWeiXinBill}
                                    </td>

                                    <td>
                                            ${intervalReport.aliPayBill}
                                    </td>

                                    <td>
                                            ${intervalReport.personalAliPayBill}
                                    </td>

                                    <td>
                                            ${intervalReport.otherBill}
                                    </td>

                                    <td>
                                            ${intervalReport.bill}
                                    </td>
                                </tr>
                                <c:forEach items="${intervalReport.dayReports}" var="dayReport">
                                    <tr>
                                        <td>
                                            <fmt:formatDate value="${dayReport.day}"
                                                            timeStyle="date"></fmt:formatDate>
                                        </td>

                                        <td>
                                                ${dayReport.cashBill}
                                        </td>

                                        <td>
                                                ${dayReport.bankCardBill}
                                        </td>

                                        <td>
                                                ${dayReport.transferBill}
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
$("#searchForm").attr("action","${ctx}/reserve/reserveCardStatements/memberIncomeReportExport");
$("#searchForm").submit();
$("#searchForm").attr("action","${ctx}/reserve/reserveCardStatements/memberIncomeReport");
});
</script>
</body>
</html>

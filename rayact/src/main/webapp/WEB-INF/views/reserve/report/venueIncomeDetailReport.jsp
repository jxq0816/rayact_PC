<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地收入统计</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<c:if test="${param.alone != 'true'}">
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="venueIncomeReport"></jsp:param>
</jsp:include>
</c:if>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>场地收入统计</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveVenue" action="${ctx}/reserve/reserveVenue/report"
                           method="post" class="breadcrumb form-search">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                    <div class="row">
                        <div class="col-sm-12 col-md-12 col-lg-12">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>场馆:</td>
                                    <td>
                                        <sys:select cssClass="input-large" name="reserveVenue.id"
                                                    cssStyle="width:100%"
                                                    value="${venueProjectReport.reserveVenue.id}"
                                                    items="${reserveVenueList}" itemLabel="name" itemValue="id"
                                                    defaultLabel="----请选择-----"
                                                    defaultValue=""></sys:select>
                                    </td>
                                    <td>项目:</td>
                                    <td>

                                        <sys:select cssClass="input-large" name="reserveProject.id"
                                                    cssStyle="width:100%"
                                                    value="${venueProjectReport.reserveProject.id}"
                                                    items="${reserveProjectList}" itemLabel="name" itemValue="id"
                                                    defaultLabel="----请选择-----"
                                                    defaultValue=""></sys:select>
                                    </td>
                                    <td>类型：</td>
                                    <td>
                                        <div class="btn-group" id="payType">
                                            <label class="radio-inline">
                                                <input type="radio" class="icheck" value="1"
                                                       name="queryType"/>汇总
                                            </label>

                                            <label class="radio-inline">
                                                <input type="radio" class="icheck" value="2" checked="checked" name="queryType"/>明细
                                            </label>
                                        </div>
                                    </td>
                                    <td>
                                        <input name="startDate" id="startDate" type="text"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               value="<fmt:formatDate value="${incomeReport.startDate}" type="date"></fmt:formatDate>"
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                        </td>
                                    <td>
                                        <input name="endDate" id="endDate" type="text"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               value="<fmt:formatDate value="${incomeReport.endDate}" type="date"></fmt:formatDate>"
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                    </td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                                        <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
                                    </td>
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
                                <th>项目</th>
                                <th>场地</th>
                                <th>储值卡</th>
                                <th>现金</th>
                                <th>银行卡</th>
                                <th>微信</th>
                                <th>支付宝</th>
                                <th>其它</th>
                                <th>合计</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%-- 总统计 开始 --%>

                            <c:set var="fieldBillStoredCard" value="0"/>
                            <c:set var="fieldBillCash" value="0"/>
                            <c:set var="fieldBillBankCard" value="0"/>
                            <c:set var="fieldBillWeiXin" value="0"/>
                            <c:set var="fieldBillAliPay" value="0"/>
                            <c:set var="fieldBillOther" value="0"/>
                            <c:set var="fieldBillDue" value="0"/>
                            <c:set var="bill" value="0"/>
                            <c:forEach items="${incomeReport.projectIntervalReports}" var="projectReport">
                               <%-- 精确至场地--%>
                                <c:forEach items="${projectReport.fieldIntervalReports}" var="report">
                                <%-- 项目区间统计 开始 --%>
                                <tr>
                                    <td>
                                            ${report.reserveVenue.name}
                                    </td>
                                    <td>
                                            ${report.reserveProject.name}
                                    </td>
                                    <td>
                                            ${report.reserveField.name}
                                    </td>
                                    <td>
                                            ${report.fieldBillStoredCard}
                                                <c:set var="fieldBillStoredCard" value="${fieldBillStoredCard+report.fieldBillStoredCard}"/>
                                    </td>
                                    <td>
                                            ${report.fieldBillCash}
                                                <c:set var="fieldBillCash" value="${fieldBillCash+report.fieldBillCash}"/>
                                    </td>
                                    <td>
                                            ${report.fieldBillBankCard}
                                                <c:set var="fieldBillBankCard" value="${fieldBillBankCard+report.fieldBillBankCard}"/>
                                    </td>

                                    <td>
                                            ${report.fieldBillWeiXin}
                                                <c:set var="fieldBillWeiXin" value="${fieldBillWeiXin+report.fieldBillWeiXin}"/>
                                    </td>
                                    <td>
                                            ${report.fieldBillAliPay}
                                                <c:set var="fieldBillAliPay" value="${fieldBillAliPay+report.fieldBillAliPay}"/>
                                    </td>
                                    <td>
                                            ${report.fieldBillOther}
                                                <c:set var="fieldBillOther" value="${fieldBillOther+report.fieldBillOther}"/>
                                    </td>
                                    <td>
                                            ${report.bill}
                                                <c:set var="bill" value="${bill+report.bill}"/>
                                    </td>
                                </tr>
                                </c:forEach>
                            </c:forEach>
                            <tr>
                                <td>
                                </td>
                                <td>
                                </td>
                                <td>
                                    包场收入合计
                                </td>
                                <td>
                                    ${fieldBillStoredCard}
                                </td>

                                <td>
                                    ${fieldBillCash}
                                </td>
                                <td>
                                    ${fieldBillBankCard}
                                </td>

                                <td>
                                    ${fieldBillWeiXin}
                                </td>
                                <td>
                                    ${fieldBillAliPay}
                                </td>
                                <td>
                                    ${fieldBillOther}
                                </td>
                                <td>
                                    ${bill}
                                </td>
                            </tr>
                            <%--散客开始--%>
                            <c:set var="fieldBillStoredCard" value="0"/>
                            <c:set var="fieldBillCash" value="0"/>
                            <c:set var="fieldBillBankCard" value="0"/>
                            <c:set var="fieldBillWeiXin" value="0"/>
                            <c:set var="fieldBillAliPay" value="0"/>
                            <c:set var="fieldBillOther" value="0"/>
                            <c:set var="bill" value="0"/>
                            <c:forEach items="${venueProjectDividedReports}" var="divided">
                                <tr>
                                    <td>
                                            ${divided.reserveVenue.name}
                                    </td>
                                    <td>
                                            ${divided.reserveProject.name}
                                    </td>
                                    <td>
                                          场次票收入
                                    </td>
                                    <td>
                                            ${divided.fieldBillStoredCard}
                                                <c:set var="fieldBillStoredCard" value="${fieldBillStoredCard+divided.fieldBillStoredCard}"/>
                                    </td>
                                    <td>
                                            ${divided.fieldBillCash}
                                                <c:set var="fieldBillCash" value="${fieldBillCash+divided.fieldBillCash}"/>
                                    </td>
                                    <td>
                                            ${divided.fieldBillBankCard}
                                                <c:set var="fieldBillBankCard" value="${fieldBillBankCard+divided.fieldBillBankCard}"/>
                                    </td>

                                    <td>
                                            ${divided.fieldBillWeiXin}
                                                <c:set var="fieldBillWeiXin" value="${fieldBillWeiXin+divided.fieldBillWeiXin}"/>
                                    </td>
                                    <td>
                                            ${divided.fieldBillAliPay}
                                                <c:set var="fieldBillAliPay" value="${fieldBillAliPay+divided.fieldBillAliPay}"/>
                                    </td>
                                    <td>
                                            ${divided.fieldBillOther}
                                                <c:set var="fieldBillOther" value="${fieldBillOther+divided.fieldBillOther}"/>
                                    </td>
                                    <td>
                                            ${divided.bill}
                                                <c:set var="bill" value="${bill+divided.bill}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td>
                                </td>
                                <td>
                                </td>
                                <td>
                                    场次票收入合计
                                </td>
                                <td>
                                    ${fieldBillStoredCard}
                                </td>

                                <td>
                                    ${fieldBillCash}
                                </td>
                                <td>
                                    ${fieldBillBankCard}
                                </td>

                                <td>
                                    ${fieldBillWeiXin}
                                </td>
                                <td>
                                    ${fieldBillAliPay}
                                </td>
                                <td>
                                    ${fieldBillOther}
                                </td>
                                <td>
                                    ${bill}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                </td>
                                <td>
                                </td>
                                <td>
                                    总收入合计（包场收入+场次票收入）
                                </td>
                                <td>
                                    ${incomeReport.storedCardBill}

                                </td>

                                <td>
                                    ${incomeReport.cashBill}
                                </td>
                                <td>
                                    ${incomeReport.bankCardBill}
                                </td>

                                <td>
                                    ${incomeReport.weiXinBill}
                                </td>
                                <td>
                                    ${incomeReport.aliPayBill}
                                </td>
                                <td>
                                    ${incomeReport.otherBill}
                                </td>
                                <td>
                                    ${incomeReport.bill}
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
<script type="text/javascript">
    $("#btnExport").click(function(){
        $("#searchForm").attr("action","${ctx}/reserve/reserveVenue/reportExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action","${ctx}/reserve/reserveVenue/report");
    });
</script>
</body>
</html>
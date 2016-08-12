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
                <form:form id="searchForm" modelAttribute="reserveVenue"
                           action="${ctx}/reserve/reserveVenue/fieldReport"
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
                                                <input type="radio" class="icheck" value="2" checked="checked"
                                                       name="queryType"/>明细
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
                            <%-- 总统计 开始 --%>

                            <c:set var="storedCardBill" value="0"/>
                            <c:set var="cashBill" value="0"/>
                            <c:set var="bankCardBill" value="0"/>
                            <c:set var="transferBill" value="0"/>
                            <c:set var="weiXinBill" value="0"/>
                            <c:set var="personalWeiXinBill" value="0"/>
                            <c:set var="aliPayBill" value="0"/>
                            <c:set var="personalAliPayBill" value="0"/>
                            <c:set var="otherBill" value="0"/>
                            <c:set var="bill" value="0"/>
                            <%-- 先精确至项目--%>
                            <c:forEach items="${incomeReport.projectIntervalReports}" var="projectReport">
                                <%-- 再精确至场地--%>
                                <c:forEach items="${projectReport.fieldIntervalReports}" var="report">
                                    <%-- 场馆项目场地区间统计 开始 --%>
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
                                                ${report.storedCardBill}
                                            <c:set var="storedCardBill"
                                                   value="${storedCardBill+report.storedCardBill}"/>
                                        </td>
                                        <td>
                                                ${report.cashBill}
                                            <c:set var="cashBill" value="${cashBill+report.cashBill}"/>
                                        </td>
                                        <td>
                                                ${report.transferBill}
                                            <c:set var="transferBill" value="${transferBill+report.transferBill}"/>
                                        </td>

                                        <td>
                                                ${report.bankCardBill}
                                            <c:set var="bankCardBill" value="${bankCardBill+report.bankCardBill}"/>
                                        </td>

                                        <td>
                                                ${report.weiXinBill}
                                            <c:set var="weiXinBill" value="${weiXinBill+report.weiXinBill}"/>
                                        </td>

                                        <td>
                                                ${report.personalWeiXinBill}
                                            <c:set var="personalWeiXinBill"
                                                   value="${personalWeiXinBill+report.personalWeiXinBill}"/>
                                        </td>
                                        <td>
                                                ${report.aliPayBill}
                                            <c:set var="aliPayBill" value="${aliPayBill+report.aliPayBill}"/>
                                        </td>
                                        <td>
                                                ${report.personalAliPayBill}
                                            <c:set var="personalAliPayBill"
                                                   value="${personalAliPayBill+report.personalAliPayBill}"/>
                                        </td>
                                        <td>
                                                ${report.otherBill}
                                            <c:set var="otherBill" value="${otherBill+report.otherBill}"/>
                                        </td>
                                        <td>
                                                ${report.bill}
                                            <c:set var="bill" value="${bill+report.bill}"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                            <tr>
                            <tr>
                                <td colspan="2"></td>
                                <td>包场合计</td>
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
                            </tr>
                            <%--散客开始--%>
                            <c:set var="storedCardBill" value="0"/>
                            <c:set var="cashBill" value="0"/>
                            <c:set var="bankCardBill" value="0"/>
                            <c:set var="transferBill" value="0"/>
                            <c:set var="weiXinBill" value="0"/>
                            <c:set var="personalWeiXinBill" value="0"/>
                            <c:set var="aliPayBill" value="0"/>
                            <c:set var="personalAliPayBill" value="0"/>
                            <c:set var="otherBill" value="0"/>
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
                                            ${divided.storedCardBill}
                                        <c:set var="storedCardBill" value="${storedCardBill+divided.storedCardBill}"/>
                                    </td>
                                    <td>
                                            ${divided.cashBill}
                                        <c:set var="cashBill" value="${cashBill+divided.cashBill}"/>
                                    </td>
                                    <td>
                                            ${divided.bankCardBill}
                                        <c:set var="bankCardBill" value="${bankCardBill+divided.bankCardBill}"/>
                                    </td>
                                    <td>
                                            ${divided.transferBill}
                                        <c:set var="transferBill" value="${transferBill+divided.transferBill}"/>
                                    </td>

                                    <td>
                                            ${divided.weiXinBill}
                                        <c:set var="weiXinBill" value="${weiXinBill+divided.weiXinBill}"/>
                                    </td>
                                    <td>
                                            ${divided.personalWeiXinBill}
                                        <c:set var="personalWeiXinBill"
                                               value="${personalWeiXinBill+divided.personalWeiXinBill}"/>
                                    </td>
                                    <td>
                                            ${divided.aliPayBill}
                                        <c:set var="aliPayBill" value="${aliPayBill+divided.aliPayBill}"/>
                                    </td>
                                    <td>
                                            ${divided.personalAliPayBill}
                                        <c:set var="personalAliPayBill"
                                               value="${personalAliPayBill+divided.personalAliPayBill}"/>
                                    </td>
                                    <td>
                                            ${divided.otherBill}
                                        <c:set var="otherBill" value="${otherBill+divided.otherBill}"/>
                                    </td>
                                    <td>
                                            ${divided.bill}
                                        <c:set var="bill" value="${bill+divided.bill}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="2"></td>
                                <td>场次票合计</td>
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
                            <tr>
                                <td colspan="2"></td>
                                <td>合计</td>
                                <td>${incomeReport.storedCardBill}</td>
                                <td>${incomeReport.cashBill}</td>
                                <td>${incomeReport.bankCardBill}</td>
                                <td>${incomeReport.transferBill}</td>
                                <td>${incomeReport.weiXinBill}</td>
                                <td>${incomeReport.personalWeiXinBill}</td>
                                <td>${incomeReport.aliPayBill}</td>
                                <td>${incomeReport.personalAliPayBill}</td>
                                <td>${incomeReport.otherBill}</td>
                                <td>${incomeReport.bill}</td>
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
    $("#btnExport").click(function () {
        $("#searchForm").attr("action", "${ctx}/reserve/reserveVenue/reportExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action", "${ctx}/reserve/reserveVenue/fieldReport");
    });
</script>
</body>
</html>
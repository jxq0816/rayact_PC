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
                        <div class="col-sm-12 col-md-12 col-lg-12">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>场馆：</td>
                                    <td>
                                        <sys:select cssClass="input-large" name="reserveVenue.id"
                                                    cssStyle="width:100%"
                                                    value="${intervalTotalReport.reserveVenue.id}"
                                                    items="${reserveVenueList}" itemLabel="name" itemValue="id"
                                                    defaultLabel="----请选择-----"
                                                    defaultValue=""></sys:select>
                                    </td>
                                    <td >
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
                                        <input name="isChecked" id="isChecked" type="hidden"
                                               value="${isChecked}"/>
                                        <input name="checkStatus" id="checkStatus" type="hidden"
                                               value="${checkStatus}"/>
                                    </td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                                        <input id="btnCheck" class="btn btn-primary" type="button" value="审核通过"/>
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
                                <th>收入类型</th>
                                <j:if test="${'1' eq intervalTotalReport.queryType}">
                                    <th>储值卡</th>
                                </j:if>
                                <th>现金</th>
                                <th>银行卡</th>
                                <th>转账</th>
                                <th>微信</th>
                                <th>微信（个人）</th>
                                <th>支付宝</th>
                                <th>支付宝（个人）</th>
                                <j:if test="${'1' eq intervalTotalReport.queryType}">
                                    <th>优惠券</th>
                                    <th>欠账</th>
                                </j:if>
                                <th>合计</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%-- 总统计 开始 --%>
                            <j:if test="${'1' eq intervalTotalReport.queryType}">
                                <c:set var="storedCardBill" value="0"/>
                                <c:set var="otherBill" value="0"/>
                                <c:set var="dueBill" value="0"/>
                            </j:if>
                            <c:set var="cashBill" value="0"/>
                            <c:set var="bankCardBill" value="0"/>
                            <c:set var="transferBill" value="0"/>
                            <c:set var="weiXinBill" value="0"/>
                            <c:set var="personalWeiXinBill" value="0"/>
                            <c:set var="aliPaybill" value="0"/>
                            <c:set var="personalAliPayBill" value="0"/>

                            <c:forEach items="${totalIntervalReportList}" var="report">
                                <c:set var="sum" value="0"/>
                            <tr>
                                <td>
                                    ${report.reserveVenue.name}
                                </td>
                                <td>
                                    ${report.transactionType}
                                </td>
                                <j:if test="${'1' eq intervalTotalReport.queryType}">
                                    <td>
                                    ${report.storedCardBill}
                                        <c:set var="storedCardBill" value="${storedCardBill+report.storedCardBill}"/>
                                        <c:set var="sum" value="${sum+report.storedCardBill}"/>
                                    </td>
                                </j:if>

                                <td>
                                    ${report.cashBill}
                                        <c:set var="cashBill" value="${cashBill+report.cashBill}"/>
                                        <c:set var="sum" value="${sum+report.cashBill}"/>
                                </td>
                                <td>
                                    ${report.bankCardBill}
                                        <c:set var="bankCardBill" value="${bankCardBill+report.bankCardBill}"/>
                                        <c:set var="sum" value="${sum+report.bankCardBill}"/>
                                </td>

                                <td>
                                        ${report.transferBill}
                                    <c:set var="transferBill" value="${transferBill+report.transferBill}"/>
                                    <c:set var="sum" value="${sum+report.transferBill}"/>
                                </td>

                                <td>
                                    ${report.weiXinBill}
                                        <c:set var="weiXinBill" value="${weiXinBill+report.weiXinBill}"/>
                                        <c:set var="sum" value="${sum+report.weiXinBill}"/>
                                </td>
                                <td>
                                        ${report.personalWeiXinBill}
                                    <c:set var="personalWeiXinBill" value="${personalWeiXinBill+report.personalWeiXinBill}"/>
                                    <c:set var="sum" value="${sum+report.personalWeiXinBill}"/>
                                </td>
                                <td>
                                    ${report.aliPayBill}
                                        <c:set var="aliPaybill" value="${aliPaybill+report.aliPayBill}"/>
                                        <c:set var="sum" value="${sum+report.aliPayBill}"/>
                                </td>
                                <td>
                                        ${report.personalAliPayBill}
                                    <c:set var="personalAliPayBill" value="${personalAliPayBill+report.personalAliPayBill}"/>
                                    <c:set var="sum" value="${sum+report.personalAliPayBill}"/>
                                </td>
                                <j:if test="${'1' eq intervalTotalReport.queryType}">
                                    <td>
                                            ${report.otherBill}
                                        <c:set var="otherBill" value="${otherBill+report.otherBill}"/>
                                                <c:set var="sum" value="${sum+report.otherBill}"/>
                                    </td>
                                    <td>
                                            ${report.dueBill}
                                        <c:set var="dueBill" value="${dueBill+report.dueBill}"/>
                                                <c:set var="sum" value="${sum+report.dueBill}"/>
                                    </td>
                                </j:if>
                                <td>
                                        ${sum}
                                </td>

                            </tr>
                            </c:forEach>
                            <%-- 总统计 结束 --%>
                            <c:set var="totalSum" value="0"/>
                            <tr>
                                <td colspan="2">
                                    合计
                                </td>
                                <j:if test="${'1' eq intervalTotalReport.queryType}">
                                    <td>
                                            ${storedCardBill}
                                                <c:set var="totalSum" value="${totalSum+storedCardBill}"/>
                                    </td>
                                </j:if>


                                <td>
                                    ${cashBill}
                                        <c:set var="totalSum" value="${totalSum+cashBill}"/>
                                </td>
                                <td>
                                    ${bankCardBill}
                                        <c:set var="totalSum" value="${totalSum+bankCardBill}"/>
                                </td>

                                <td>
                                    ${transferBill}
                                    <c:set var="totalSum" value="${totalSum+transferBill}"/>
                                </td>

                                <td>
                                    ${weiXinBill}
                                        <c:set var="totalSum" value="${totalSum+weiXinBill}"/>
                                </td>

                                <td>
                                    ${personalWeiXinBill}
                                    <c:set var="totalSum" value="${totalSum+personalWeiXinBill}"/>
                                </td>
                                <td>
                                    ${aliPaybill}
                                        <c:set var="totalSum" value="${totalSum+aliPaybill}"/>
                                </td>
                                <td>
                                    ${personalAliPayBill}
                                    <c:set var="totalSum" value="${totalSum+personalAliPayBill}"/>
                                </td>
                                <j:if test="${'1' eq intervalTotalReport.queryType}">
                                    <td>
                                            ${otherBill}
                                                <c:set var="totalSum" value="${totalSum+otherBill}"/>
                                    </td>
                                    <td>
                                            ${dueBill}
                                                <c:set var="totalSum" value="${totalSum+dueBill}"/>
                                    </td>
                                </j:if>
                                <td>
                                    ${totalSum}
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
<div id="checkedMark" style="color:red;position: fixed;bottom:200px;right:200px;transform:rotate(45deg);
-ms-transform:rotate(45deg);font-size: 70px;
-moz-transform:rotate(45deg);
-webkit-transform:rotate(45deg);
-o-transform:rotate(45deg);background: rgba(0,0,0,0);border:1px #ff0900 solid">
    已审核
</div>
<script>
    $("#btnCheck").on('click',function(){
        if(confirm("确认该时间段内的数据核验完毕？")){
            $("#isChecked").val("true");
            $("#searchForm").submit();
        }
    });
    $(function(){
        var checkStatus = "${checkStatus}";
        if(checkStatus == "1"){//无水印，可审
            $("#checkedMark").hide();
            $("#btnCheck").show();
        }else if(checkStatus == "2"){//无水印，不可审
            $("#checkedMark").hide();
            $("#btnCheck").hide();
        }else if(checkStatus == "3"){//有水印，不可审
            $("#btnCheck").hide();
            $("#checkedMark").show();
        }
    });
</script>
</body>
</html>
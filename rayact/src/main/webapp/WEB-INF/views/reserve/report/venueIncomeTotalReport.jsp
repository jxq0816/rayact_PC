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
                                    <td>场馆:</td>
                                    <td>
                                        <sys:select cssClass="input-large" name="reserveVenue.id"
                                                    cssStyle="width:100%"
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
                                <th>微信</th>
                                <th>支付宝</th>
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
                                <c:set var="billStoredCard" value="0"/>
                                <c:set var="billOther" value="0"/>
                                <c:set var="billDue" value="0"/>
                            </j:if>
                            <c:set var="billCash" value="0"/>
                            <c:set var="billBankCard" value="0"/>
                            <c:set var="billWeiXin" value="0"/>
                            <c:set var="billAliPay" value="0"/>

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
                                        <c:set var="billStoredCard" value="${billStoredCard+report.storedCardBill}"/>
                                        <c:set var="sum" value="${sum+report.storedCardBill}"/>
                                    </td>
                                </j:if>

                                <td>
                                    ${report.cashBill}
                                        <c:set var="billCash" value="${billCash+report.cashBill}"/>
                                        <c:set var="sum" value="${sum+report.cashBill}"/>
                                </td>
                                <td>
                                    ${report.bankCardBill}
                                        <c:set var="billBankCard" value="${billBankCard+report.bankCardBill}"/>
                                        <c:set var="sum" value="${sum+report.bankCardBill}"/>
                                </td>

                                <td>
                                    ${report.weiXinBill}
                                        <c:set var="billWeiXin" value="${billWeiXin+report.weiXinBill}"/>
                                        <c:set var="sum" value="${sum+report.weiXinBill}"/>
                                </td>
                                <td>
                                    ${report.aliPayBill}
                                        <c:set var="billAliPay" value="${billAliPay+report.aliPayBill}"/>
                                        <c:set var="sum" value="${sum+report.aliPayBill}"/>
                                </td>
                                <j:if test="${'1' eq intervalTotalReport.queryType}">
                                    <td>
                                            ${report.otherBill}
                                        <c:set var="billOther" value="${billOther+report.otherBill}"/>
                                                <c:set var="sum" value="${sum+report.otherBill}"/>
                                    </td>
                                    <td>
                                            ${report.dueBill}
                                        <c:set var="billDue" value="${billDue+report.dueBill}"/>
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
                                <td>
                                </td>
                                <td>
                                </td>
                                <j:if test="${'1' eq intervalTotalReport.queryType}">
                                    <td>
                                            ${billStoredCard}
                                                <c:set var="totalSum" value="${totalSum+billStoredCard}"/>
                                    </td>
                                </j:if>
                                <td>
                                    ${billCash}
                                        <c:set var="totalSum" value="${totalSum+billCash}"/>
                                </td>
                                <td>
                                    ${billBankCard}
                                        <c:set var="totalSum" value="${totalSum+billBankCard}"/>
                                </td>

                                <td>
                                    ${billWeiXin}
                                        <c:set var="totalSum" value="${totalSum+billWeiXin}"/>
                                </td>
                                <td>
                                    ${billAliPay}
                                        <c:set var="totalSum" value="${totalSum+billAliPay}"/>
                                </td>
                                <j:if test="${'1' eq intervalTotalReport.queryType}">
                                    <td>
                                            ${billOther}
                                                <c:set var="totalSum" value="${totalSum+billOther}"/>
                                    </td>
                                    <td>
                                            ${billDue}
                                                <c:set var="totalSum" value="${totalSum+billDue}"/>
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
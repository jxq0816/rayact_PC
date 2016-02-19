<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场馆报表</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="venueIncomeReport"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>场馆报表</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveVenue" action="${ctx}/reserve/reserveVenue/report"
                           method="post" class="content">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                    <div class="row">
                        <div class="col-sm-6 col-md-6 col-lg-6">
                        <table class="no-border">
                            <tbody class="no-border-y">
                                <tr>
                                    <td>场馆:</td>
                                    <td>

                                                <sys:select cssClass="input-medium" name="reserveVenue.id"
                                                            value="reserveVenue"
                                                            items="${reserveVenueList}" itemLabel="name" itemValue="id"
                                                            defaultLabel="----请选择-----"
                                                            defaultValue=""></sys:select>
                                    </td>
                                    <td>
                                        <input name="consDate" id="month" type="text" readonly="readonly"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
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
                                <th>时间</th>
                                <th>场地费（现金）</th>
                                <th>场地费（银行卡）</th>
                                <th>场地费（储值卡）</th>
                                <th>场地费（微信）</th>
                                <th>场地费（支付宝）</th>
                                <th>场地费（其它）</th>
                                <th>场地费（欠账）</th>
                                <th>预付（现金）</th>
                                <th>预付（银行卡）</th>
                            </tr>
                            </thead>
                            <tbody>

                                <tr>
                                    <td>
                                        ${m}
                                    </td>
                                    <td>
                                        ${monthReport.fieldBillCash}
                                    </td>
                                    <td>
                                        ${monthReport.fieldBillBankCard}
                                    </td>
                                    <td>
                                        ${monthReport.fieldBillStoredCard}
                                    </td>
                                    <td>
                                        ${monthReport.fieldBillWeiXin}
                                    </td>
                                    <td>
                                        ${monthReport.fieldBillAliPay}
                                    </td>
                                    <td>
                                        ${monthReport.fieldBillOther}
                                    </td>
                                    <td>
                                        尚未开发
                                    </td>
                                    <td>
                                        尚未开发
                                    </td>
                                    <td>
                                        尚未开发
                                    </td>
                                </tr>
                            </tbody>
                        </table>

                       <%-- <div class="row">
                            <div class="col-sm-12">

                                <div class="pull-right">
                                    <div class="dataTables_paginate paging_bs_normal">
                                        <sys:javascript_page p="${page}"></sys:javascript_page>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>--%>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

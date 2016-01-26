<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>订单管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="fieldOrder"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>订单管理</h3>
                </div>

                <form:form id="searchForm" modelAttribute="reserveVenueCons"
                           action="${ctx}/reserve/reserveVenueConsData/order" method="post"
                           class="breadcrumb form-search">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                    <div class="row">
                        <div class="col-sm-6 col-md-6 col-lg-6">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>订单人：</td>
                                    <td><form:input path="userName" htmlEscape="false" cssstyle="width:70px;" maxlength="30" class="form-control"/></td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </form:form>
                <sys:message content="${message}"/>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>场馆</th>
                                <th>交易金额(含教练)</th>
                                <th>会员卡</th>
                                <th>现金</th>
                                <th>银行卡</th>
                                <th>微信</th>
                                <th>支付宝</th>
                                <th>其它</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${datas}" var="data">
                                 <tr>
                                     <td>${data.venueName}</td>
                                     <td>${data.orderPrice}</td>

                                     <td>${data.memberCount}</td>
                                     <td>${data.moneyCount}</td>
                                     <td>${data.bankCartCount}</td>
                                     <td>${data.weixinCount}</td>
                                     <td>${data.zfbCount}</td>
                                     <td>${data.otherCount}</td>
                                 </tr>
                             </c:forEach>
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
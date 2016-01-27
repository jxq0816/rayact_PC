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
                                <th>订单编号</th>
                                <th>预定人</th>
                                <th>预定日期</th>
                                <th>场馆</th>
                                <th>预定金额</th>
                                <th>实收金额</th>
                                <th>创建时间</th>
                                <th>备注</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="reserveVenueCons">
                                 <tr>
                                     <td><%--${reserveVenueCons.orderNo}--%></td>
                                     <td>${reserveVenueCons.userName}</td>
                                     <td><fmt:formatDate value="${reserveVenueCons.consDate}" pattern="yyyy-MM-dd"/></td>
                                     <td>${reserveVenueCons.reserveVenue.name}</td>
                                     <td>${reserveVenueCons.consPrice}</td>
                                     <td>${reserveVenueCons.orderPrice}</td>
                                     <td><fmt:formatDate value="${reserveVenueCons.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                     <td>
                                             ${reserveVenueCons.remarks}
                                     </td>
                                 </tr>
                             </c:forEach>
                            </tbody>
                        </table>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="pull-right">
                                    <div class="dataTables_paginate paging_bs_normal">
                                        <sys:javascript_page p="${page}"></sys:javascript_page>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
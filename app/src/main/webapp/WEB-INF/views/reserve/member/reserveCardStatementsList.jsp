<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>交易记录</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="statements"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <h3>交易记录</h3>
            </div>
                <div class="header">
                <form:form id="searchForm" modelAttribute="reserveCardStatements"
                           action="${ctx}/reserve/reserveCardStatements/list"
                           method="post" class="breadcrumb form-search">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                    <div class="row">
                        <div class="col-sm-10 col-md-10 col-lg-10">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>姓名：</td>
                                    <td><form:input path="reserveMember.name" htmlEscape="false" maxlength="30"
                                                       class="form-control"/></td>

                                    <td>卡号：</td>
                                    <td> <form:input path="reserveMember.cartno" htmlEscape="false" maxlength="20"
                                                        class="form-control"/>
                                    </td>

                                    <td> <input id="btnSubmit" class="btn btn-primary" type="submit"
                                                   value="查询"/></td>
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
                                <th>姓名</th>
                                <th>卡号</th>
                                <th>金额</th>
                                <th>交易类型</th>
                                <th>操作人</th>
                                <th>时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="reserveCardStatements">
                                <tr>
                                    <td>
                                            ${reserveCardStatements.reserveMember.name}
                                    </td>
                                    <td>
                                            ${reserveCardStatements.reserveMember.cartno}
                                    </td>
                                    <td>
                                            ${reserveCardStatements.transactionVolume}
                                    </td>
                                    <td>
                                        <c:if test="${reserveCardStatements.transactionType==1}">
                                            充值
                                        </c:if>
                                        <c:if test="${reserveCardStatements.transactionType==2}">
                                            退款
                                        </c:if>
                                    </td>

                                    <td>
                                            ${reserveCardStatements.createBy.name}
                                    </td>

                                    <td>
                                        <fmt:formatDate value="${reserveCardStatements.createDate}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>

                                </tr>
                            </c:forEach>

                            <tr>
                                <td>
                                </td>
                                <td>
                                </td>
                                <td>
                                    111
                                </td>
                                <td>

                                </td>

                                <td>
                                </td>

                                <td>

                                </td>

                            </tr>
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

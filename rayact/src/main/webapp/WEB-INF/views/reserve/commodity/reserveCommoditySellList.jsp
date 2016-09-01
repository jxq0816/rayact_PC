<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品订单列表</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="reserveCommoditySell"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>商品订单列表</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveCommoditySell" action="${ctx}/reserve/reserveCommoditySell/list"
                           method="post">
                    <div class="breadcrumb form-search">
                        <div class="row">
                            <div class="col-sm-12 col-md-12 col-lg-12">
                                <div class="form-group col-lg-3">
                                    <form:input placeholder="请输入订单编号" path="id" htmlEscape="false"
                                                cssstyle="width:70px;" maxlength="30"
                                                class="form-control"/>
                                </div>
                                <div class="form-group col-lg-2">
                                    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="content">
                        <div class="table-responsive">
                            <table>
                                <thead>
                                <tr>
                                    <th>订单编号</th>
                                    <th>顾客</th>
                                    <th>订单金额</th>
                                    <j:if test="${userType==5 or userType==1 or userType==6}">
                                        <th>操作</th>
                                    </j:if>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.list}" var="sell">
                                    <tr>
                                        <td>
                                                ${sell.id}
                                        </td>
                                        <td>
                                                ${sell.reserveMember.name}
                                        </td>
                                        <td>
                                                ${sell.totalSum}元
                                        </td>

                                        <j:if test="${userType==5 or userType==1 or userType==6}">
                                            <td>
                                                <a class="btn btn-danger btn-xs"
                                                   href="${ctx}/reserve/reserveCommoditySell/delete?id=${sell.id}"
                                                   onclick="return confirmb('确认要删除该商品订单吗？', this.href)">删除</a>
                                            </td>
                                        </j:if>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="row">
                                <div class="col-sm-12">

                                    <div class="pull-right">
                                        <div class="dataTables_paginate paging_bs_normal">
                                            <sys:javascript_page p="${page}" formId="searchForm"></sys:javascript_page>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
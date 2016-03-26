<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="commoditySell"></jsp:param>
</jsp:include>
<div class="container-fluid">
    <div class="row">
        <div class="block-flat">
            <div class="header">
                <h3>商品列表</h3>
            </div>
            <form:form id="searchForm" modelAttribute="reserveCommodity"
                       action="${ctx}/reserve/commodity/list"
                       method="post"
                       class="breadcrumb form-search">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                <div class="row">
                    <div class="col-sm-6 col-md-6 col-lg-6">
                        <table class="no-border">
                            <tbody class="no-border-y">
                            <tr>
                                <td>名称：</td>
                                <td><form:input path="name" htmlEscape="false" maxlength="30"
                                                class="form-control"/></td>
                                <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </form:form>


            <div class="row">
                <%-- left start--%>
                <div class="col-sm-6 col-md-6 col-lg-6">
                    <table id="contentTable">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>名称</th>
                            <th>价格</th>
                            <th>库存量</th>
                            <th>类别</th>
                            <shiro:hasPermission name="reserve:commodity:edit">
                                <th>操作</th>
                            </shiro:hasPermission>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="commodity">
                            <tr>
                                <td><a href="${ctx}/reserve/commodity/form?id=${commodity.id}">
                                        ${commodity.commodityId}
                                </a></td>
                                <td>
                                        ${commodity.name}
                                </td>
                                <td>
                                        ${commodity.price}&nbsp;元
                                </td>
                                <td>
                                        ${commodity.repertoryNum}
                                </td>
                                <td>
                                        ${commodity.commodityType.name}
                                </td>

                                <td>
                                   <%-- <j:if test="">--%>
                                    <button id="${commodity.id}" class="btn btn-primary btn-xs"
                                            onclick="outStorage('${commodity.id}','${commodity.name}',${commodity.price})">
                                        售卖
                                    </button>
                                    <%--</j:if>--%>
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
                <%--end of left--%>
                <%-- right start--%>
                <div class="col-sm-6 col-md-6 col-lg-6">
                    <form id="paySell">
                        <input type="hidden" name="token" value="${token}"/>
                        商品销售表
                        <table id="sellList" class="table table-striped table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th align="center">商品名称</th>
                                <th align="center">数量</th>
                            </tr>
                            </thead>
                        </table>
                        合计：<span id="sum">0</span>元
                        <a class="btn btn-success" onclick="sellSubmit()">付款</a>
                    </form>
                </div>
                <%--end of right--%>
            </div>
        </div>
    </div>
    <%-- end of row-fluid--%>
</div>
<%--end of container-fluid--%>
<script src="${ctxStatic}/modules/reserve/js/reserve_commodity_sell.js?id=66"
        type="text/javascript"></script>

</body>
</html>
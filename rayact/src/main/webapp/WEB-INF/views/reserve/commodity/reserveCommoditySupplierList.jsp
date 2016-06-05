<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>供应商管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="commoditySupplier"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>供应商列表</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveCommoditySupplier"
                           action="${ctx}/reserve/reserveCommoditySupplier/list"
                           method="post">
                    <div class="breadcrumb form-search">
                        <div class="row">
                            <div class="col-sm-12 col-md-12 col-lg-12">
                                <div class="form-group col-lg-3">
                                    <form:input placeholder="请输入供应商名称" path="name" htmlEscape="false" maxlength="30"
                                                class="form-control"/>
                                </div>
                                <div class="form-group col-lg-2">
                                    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                                </div>
                                <div class="form-group pull-right">
                                    <a class="btn btn-success" href="${ctx}/reserve/reserveCommoditySupplier/form">
                                        <i class="fa fa-plus"></i>添加
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="content">
                        <div class="table-responsive">
                            <table>
                                <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.list}" var="commoditySupplier">
                                    <tr>
                                        <td>
                                            <a href="${ctx}/reserve/reserveCommoditySupplier/form?id=${commoditySupplier.id}">
                                                    ${commoditySupplier.name}
                                            </a></td>

                                        <td>
                                            <a class="btn btn-primary btn-xs "
                                               href="${ctx}/reserve/reserveCommoditySupplier/form?id=${commoditySupplier.id}">修改</a>
                                            <a class="btn btn-danger btn-xs"
                                               href="${ctx}/reserve/reserveCommoditySupplier/delete?id=${commoditySupplier.id}"
                                               onclick="return confirmb('确认要删除该供应商吗？', this.href)">删除</a>
                                        </td>
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
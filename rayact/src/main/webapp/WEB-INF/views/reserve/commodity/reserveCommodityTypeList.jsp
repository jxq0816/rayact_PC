<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品类别管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="commodityType"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>商品类别列表</h3>
                </div>

                <form:form id="searchForm" modelAttribute="reserveCommodityType" action="${ctx}/reserve/commodityType/"
                           method="post">
                    <div class="breadcrumb form-search">
                        <div class="row">
                            <div class="col-sm-6 col-md-6 col-lg-6">
                                <table class="no-border">
                                    <tbody class="no-border-y">
                                    <tr>
                                        <td>名称：</td>
                                        <td><form:input path="name" htmlEscape="false" cssstyle="width:70px;"
                                                        maxlength="30"
                                                        class="form-control"/></td>
                                        <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="pull-right">
                                <a class="btn btn-success" href="${ctx}/reserve/commodityType/form">
                                    <i class="fa fa-plus"></i>添加
                                </a>
                            </div>
                        </div>
                    </div>

                    <sys:message content="${message}"/>
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
                                <c:forEach items="${page.list}" var="commodityType">
                                    <tr>
                                        <td><a href="${ctx}/reserve/commodityType/form?id=${commodityType.id}">
                                                ${commodityType.name}
                                        </a></td>

                                        <td>
                                            <a class="btn btn-primary btn-xs"
                                               href="${ctx}/reserve/commodityType/form?id=${commodityType.id}"><i
                                                    class="fa fa-pencil"></i>修改</a>
                                            <a class="btn btn-danger btn-xs"
                                               href="${ctx}/reserve/commodityType/delete?id=${commodityType.id}"
                                               onclick="return confirmb('确认要删除该商品类别吗？', this.href)">删除</a>
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
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
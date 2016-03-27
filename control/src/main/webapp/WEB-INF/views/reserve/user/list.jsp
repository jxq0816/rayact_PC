<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场馆管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="user"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>用户管理</h3>
                </div>
                <form:form id="searchForm" modelAttribute="user" action="${ctx}/reserve/user/list"
                           method="post" class="breadcrumb form-search">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                    <div class="row">
                        <div class="col-sm-6 col-md-6 col-lg-6">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>姓名:</td>
                                    <td>
                                        <form:input path="name" htmlEscape="false" cssstyle="width:50px;" maxlength="30"
                                                    class="form-control"/>
                                    </td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="pull-right">
                            <a class="btn btn-success" href="${ctx}/reserve/user/form">
                                <i class="fa fa-plus"></i>添加
                            </a>
                        </div>
                    </div>
                </form:form>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead><tr><th>归属部门</th>
                                <th class="sort-column login_name">登录名</th>
                               <%-- <th>场馆</th>--%>
                                <th class="text-center primary-emphasis">姓名</th>
                                <th>电话</th><th>手机</th><%--<th>角色</th> --%><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="user">
                                <tr style="height: 30px;">
                                    <td>${user.office.name}</td>
                                   <%-- <th>${user.office.name}</th>--%>
                                    <td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
                                    <td class="text-center primary-emphasis">${user.name}</td>
                                    <td>${user.phone}</td>
                                    <td>${user.mobile}</td><%--
				<td>${user.roleNames}</td> --%>
                                    <shiro:hasPermission name="sys:user:edit"><td>
                                        <a class="btn btn-primary btn-xs" href="${ctx}/reserve/user/form?id=${user.id}"><i
                                                class="fa fa-pencil"></i>修改</a>
                                        <a class="btn btn-danger btn-xs" href="${ctx}/reserve/user/delete?id=${user.id}" onclick="return confirmb('确认要删除该用户吗？', this.href)"><i
                                                class="fa fa-times"></i>删除</a>
                                    </td></shiro:hasPermission>
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

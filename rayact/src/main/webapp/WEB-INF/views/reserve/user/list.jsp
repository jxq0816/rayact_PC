<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理</title>
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
                            <thead><tr><th>归属公司</th><th class="sort-column login_name">登录名</th>
                                <th class="text-center">姓名</th><th>手机</th><th>角色</th>
                                <th>操作</th></tr></thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="user">
                                <tr style="height: 30px;">
                                    <td>${user.company.name}</td>
                                    <td><a href="${ctx}/reserve/user/form?id=${user.id}">${user.loginName}</a></td>
                                    <td class="text-center">${user.name}</td>
                                    <td>${user.phone}</td>
                                    <td>
                                            ${fns:getUserType(user.userType)}
                                    </td>
                                    <td>
                                        <a class="btn btn-primary btn-xs" href="${ctx}/reserve/user/form?id=${user.id}"><i
                                                class="fa fa-pencil"></i>修改</a>
                                        <a class="btn btn-danger btn-xs" href="${ctx}/reserve/user/delete?id=${user.id}" onclick="return confirmb('确认要删除该用户吗？', this.href)"><i
                                                class="fa fa-times"></i>删除</a>
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
            </div>
        </div>
    </div>
</div>
</body>
</html>

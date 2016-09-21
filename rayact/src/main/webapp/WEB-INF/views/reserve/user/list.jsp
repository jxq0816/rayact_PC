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
                <form id="searchForm" action="${ctx}/reserve/user/list"
                      method="post">
                    <div class="row breadcrumb form-search col-lg-12 col-sm-12"
                         style="margin-left:0px; margin-right:0px;">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                        <div class="row">
                            <div class="form-group col-xs-4">
                                <div class="col-xs-4">
                                    <label class="control-label" for="userName">姓名：</label>
                                </div>
                                <div class="col-xs-8" id="userName">
                                      <input name="name" htmlEscape="false"
                                             maxlength="30" value="${query.name}"
                                             class="form-control"/>
                                </div>
                            </div>
                            <div class="form-group col-xs-4">
                                <label class="control-label col-xs-4" for="userType">用户类型：</label>
                                <div class="col-xs-8">
                                    <select id="userType" name="userType" class="select2" style="width: 100%">
                                        <option value="">全部</option>
                                        <option value="1"
                                                <j:if test="${1 eq query.userType}">selected="selected"</j:if>
                                        >超级管理员
                                        </option>
                                        <option value="2"
                                                <j:if test="${2 eq query.userType}">selected="selected"</j:if>
                                        >场馆管理员
                                        </option>
                                        <option value="3"
                                                <j:if test="${3 eq query.userType}">selected="selected"</j:if>
                                        >高管
                                        </option>
                                        <option value="4"
                                                <j:if test="${3 eq query.userType}">selected="selected"</j:if>
                                        >收银
                                        </option>
                                        <option value="5"
                                                <j:if test="${5 eq query.userType}">selected="selected"</j:if>
                                        >财务
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-xs-2">
                                <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                            </div>
                            <div class="pull-right">
                                <div class="form-group col-xs-2">
                                    <a class="btn btn-success" href="${ctx}/reserve/user/form">
                                        <i class="fa fa-plus"></i>添加
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>归属公司</th>
                                <th class="sort-column login_name">登录名</th>
                                <th class="text-center">姓名</th>
                                <th>手机</th>
                                <th>角色</th>
                                <th>操作</th>
                            </tr>
                            </thead>
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
                                        <a class="btn btn-danger btn-xs" href="${ctx}/reserve/user/delete?id=${user.id}"
                                           onclick="return confirmb('确认要删除该用户吗？', this.href)"><i
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

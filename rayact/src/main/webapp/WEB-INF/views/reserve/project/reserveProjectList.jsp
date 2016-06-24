<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>项目管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="project"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>项目列表</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveProject" action="${ctx}/reserve/reserveProject/"
                           method="post">

                    <div class="row breadcrumb form-search"
                         style="margin-left:0px; margin-right:0px;">
                        <div class="form-group col-lg-3 col-sm-4">
                            <label class="control-label" for="name">项目名称：</label>
                            <form:input path="name" id="name" htmlEscape="false" maxlength="30"
                                        class="input-medium"/>
                        </div>


                        <div class="form-group col-lg-2 col-sm-3">
                            <input id="btnSubmit" class="btn btn-primary" type="submit"
                                   value="查询"/>
                        </div>

                        <div class="pull-right">
                            <a class="btn btn-success" href="${ctx}/reserve/reserveProject/form">
                                <i class="fa fa-plus"></i>添加
                            </a>
                        </div>

                    </div>


                    <sys:message content="${message}"/>
                    <div class="content">
                        <div class="table-responsive">
                            <table>
                                <thead>
                                <tr>
                                    <th>项目名称</th>
                                    <th>是否启用</th>
                                    <th>备注</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.list}" var="reserveProject">
                                    <tr>
                                        <td><a href="${ctx}/reserve/reserveProject/form?id=${reserveProject.id}">
                                                ${reserveProject.name}
                                        </a></td>
                                        <td>
                                                ${fns:getDictLabel(reserveProject.available, 'yes_no', '')}
                                        </td>
                                        <td>
                                                ${reserveProject.remarks}
                                        </td>
                                        <td>
                                            <a class="btn btn-primary btn-xs" href="${ctx}/reserve/reserveProject/form?id=${reserveProject.id}">修改</a>
                                            <a class="btn btn-danger btn-xs" href="${ctx}/reserve/reserveProject/delete?id=${reserveProject.id}"
                                               onclick="return confirmb('确认要删除该项目吗？', this.href)">删除</a>
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

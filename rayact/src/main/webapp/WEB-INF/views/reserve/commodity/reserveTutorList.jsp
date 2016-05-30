<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>教练管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="tutor"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>教练列表</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveTutor" action="${ctx}/reserve/reserveTutor/"
                           method="post">
                    <div class="breadcrumb form-search">
                        <div class="row">
                            <div class="col-sm-10 col-md-10 col-lg-10">
                                <div class="form-group col-lg-3 col-sm-5">
                                    <label class="control-label" for="project">项目：</label>
                                    <sys:select cssClass="input-large" name="project.id" id="project"
                                                cssStyle="width:50%"
                                                value="${query.project.id}"
                                                items="${reserveProjectList}" itemLabel="name" itemValue="id"
                                                defaultLabel="----请选择-----"
                                                defaultValue=""></sys:select>
                                </div>
                                <div class="form-group col-lg-3 col-sm-5">
                                    <form:input path="name" htmlEscape="false" id="tutorName" cssStyle="width: 100%"
                                                placeholder="请输入教练名称"
                                                class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input id="btnSubmit" class="btn btn-primary" type="submit"
                                           value="查询"/>
                                </div>
                            </div>
                            <div class="pull-right">
                                <a class="btn btn-success" href="${ctx}/reserve/reserveTutor/form">
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
                                    <th>姓名</th>
                                    <th>是否启用</th>
                                    <th>项目</th>
                                    <th>价格</th>
                                    <th>级别</th>
                                    <th>备注</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.list}" var="reserveTutor">
                                    <tr>
                                        <td><a href="${ctx}/reserve/reserveTutor/form?id=${reserveTutor.id}">
                                                ${reserveTutor.name}
                                        </a></td>
                                        <td class="color-success">
                                                ${fns:getDictLabel(reserveTutor.startUsing, 'yes_no', '')}
                                        </td>
                                        <td>
                                                ${reserveTutor.project.name}
                                        </td>
                                        <td>
                                                ${reserveTutor.price}
                                            <li class='fa fa-cny'></li>
                                            （小时）

                                        </td>
                                        <td>
                                                ${reserveTutor.level}
                                        </td>
                                        <td>
                                                ${reserveTutor.remarks}
                                        </td>
                                        <td>
                                            <a class="btn btn-primary btn-xs"
                                               href="${ctx}/reserve/reserveTutor/form?id=${reserveTutor.id}"><i
                                                    class="fa fa-pencil"></i>修改</a>
                                            <a class="btn btn-danger btn-xs"
                                               href="${ctx}/reserve/reserveTutor/delete?id=${reserveTutor.id}"
                                               onclick="return confirmx('确认要删除该教练吗？', this.href)"><i
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
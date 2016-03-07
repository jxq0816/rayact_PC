<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>次卡设置</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="timecardSet"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>次卡列表</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveTimecardMemberSet"
                           action="${ctx}/reserve/reserveTimecardMemberSet/"
                           method="post"  class="breadcrumb form-search">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                    <div class="row">
                        <div class="col-sm-9 col-md-9 col-lg-9">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>姓名：</td>
                                    <td><form:input path="name" htmlEscape="false" maxlength="30"
                                                       class="form-control       "/></td>
                                    <td> <input id="btnSubmit" class="btn btn-primary" type="submit"
                                                   value="查询"/></td>

                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="pull-right">
                            <a class="btn btn-success" href="${ctx}/reserve/reserveTimecardMemberSet/form">
                                <i class="fa fa-plus"></i>添加
                            </a>
                        </div>
                    </div>
                </form:form>
                <sys:message content="${message}"/>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>名称</th>
                                <th>项目</th>
                                <th>次数</th>
                                <th>赠送次数</th>
                                <th>分钟/次</th>
                                <th>备注</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="reserveTimecardMemberSet">
                                <tr>
                                    <td>
                                        <a href="${ctx}/reserve/reserveTimecardMemberSet/form?id=${reserveTimecardMemberSet.id}">
                                                ${reserveTimecardMemberSet.name}
                                        </a></td>
                                    <td>${reserveTimecardMemberSet.reserveProject.name}</td>
                                    <td>
                                            ${reserveTimecardMemberSet.startTime}~${reserveTimecardMemberSet.endTime}次
                                    </td>
                                    <td>
                                            ${reserveTimecardMemberSet.giveTime}次
                                    </td>

                                    <td>
                                            ${reserveTimecardMemberSet.minutesPerTime}
                                    </td>

                                    <td>
                                            ${reserveTimecardMemberSet.remarks}
                                    </td>
                                        <td>
                                            <a class="btn btn-primary btn-xs"
                                               href="${ctx}/reserve/reserveTimecardMemberSet/form?id=${reserveTimecardMemberSet.id}"><i
                                                    class="fa fa-pencil"></i>修改</a>
                                            <a class="btn btn-danger btn-xs"
                                               href="${ctx}/reserve/reserveTimecardMemberSet/delete?id=${reserveTimecardMemberSet.id}"
                                               onclick="return confirmb('确认要删除该次卡设置吗？', this.href)"><i
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
            </div>
        </div>
    </div>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>时令管理</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="timeInterval"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>时令管理</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveTimeInterval"
                           action="${ctx}/reserve/reserveTimeInterval/"
                           method="post" class="breadcrumb form-search form-horizontal">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                    <div class="row">
                        <div class="col-sm-4 col-md-4 col-lg-8">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>名称：</td>
                                    <td><form:input path="name" htmlEscape="false"
                                                    maxlength="30"
                                                    class="form-control"/></td>
                                    <td>
                                        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="pull-right">
                            <a class="btn btn-success" href="${ctx}/reserve/reserveTimeInterval/form">
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
                                <th>开始日期</th>
                                <th>结束日期</th>
                                <th>备注</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="reserveTimeInterval">
                                <tr>
                                    <td><a href="${ctx}/reserve/reserveTimeInterval/form?id=${reserveTimeInterval.id}">
                                            ${reserveTimeInterval.name}
                                    </a></td>
                                    <td>
                                            ${reserveTimeInterval.startMonth}月${reserveTimeInterval.startDate}日
                                    </td>
                                    <td>
                                            ${reserveTimeInterval.endMonth}月${reserveTimeInterval.endDate}日
                                    </td>
                                    <td>
                                            ${reserveTimeInterval.remarks}
                                    </td>
                                    <td>
                                        <a class="btn btn-primary btn-xs"
                                           href="${ctx}/reserve/reserveTimeInterval/form?id=${reserveTimeInterval.id}"><i
                                                class="fa fa-pencil"></i>修改</a>
                                        <a class="btn btn-danger btn-xs"
                                           href="${ctx}/reserve/reserveTimeInterval/delete?id=${reserveTimeInterval.id}"
                                           onclick="return confirmb('确认要删除该时令吗？', this.href)"><i
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
<script type="text/javascript">
    $(document).ready(function () {
        $('.icheck').iCheck({
            checkboxClass: 'icheckbox_square-blue checkbox',
            radioClass: 'iradio_square-blue'
        });
    });
</script>
</body>
</html>

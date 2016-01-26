<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>次卡设置管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/reserve/reserveTimecardMemberSet/">次卡设置列表</a></li>
    <shiro:hasPermission name="reserve:reserveTimecardMemberSet:edit">
        <li><a href="${ctx}/reserve/reserveTimecardMemberSet/form">次卡设置添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="reserveTimecardMemberSet" action="${ctx}/reserve/reserveTimecardMemberSet/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>名称：</label>
            <form:input path="name" htmlEscape="false" maxlength="30" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>名称</th>
        <th>项目</th>
        <th>次数</th>
        <th>赠送次数</th>
        <th>截止日期</th>
        <th>分钟/次</th>
        <th>备注</th>
        <shiro:hasPermission name="reserve:reserveTimecardMemberSet:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="reserveTimecardMemberSet">
        <tr>
            <td><a href="${ctx}/reserve/reserveTimecardMemberSet/form?id=${reserveTimecardMemberSet.id}">
                    ${reserveTimecardMemberSet.name}
            </a></td>
            <td>${reserveTimecardMemberSet.reserveProject.name}</td>
            <td>
                    ${reserveTimecardMemberSet.startTime}~${reserveTimecardMemberSet.endTime}次
            </td>
            <td>
                    ${reserveTimecardMemberSet.giveTime}次
            </td>
            <td><fmt:formatDate value="${reserveTimecardMemberSet.deadline}" type="date"/></td>

            <td>
                    ${reserveTimecardMemberSet.minutesPerTime}
            </td>

            <td>
                    ${reserveTimecardMemberSet.remarks}
            </td>
            <shiro:hasPermission name="reserve:reserveTimecardMemberSet:edit">
                <td>
                    <a href="${ctx}/reserve/reserveTimecardMemberSet/form?id=${reserveTimecardMemberSet.id}">修改</a>
                    <a href="${ctx}/reserve/reserveTimecardMemberSet/delete?id=${reserveTimecardMemberSet.id}"
                       onclick="return confirmx('确认要删除该次卡设置吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
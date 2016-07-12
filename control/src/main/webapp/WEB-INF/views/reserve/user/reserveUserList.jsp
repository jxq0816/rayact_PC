<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商家用户管理</title>
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
    <li class="active"><a href="${ctx}/reserve/reserveUser/">商家用户列表</a></li>
    <shiro:hasPermission name="reserve:reserveUser:edit">
        <li><a href="${ctx}/reserve/reserveUser/form">商家用户添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="reserveUser" action="${ctx}/reserve/reserveUser/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>姓名：</label>
            <form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>姓名</th>
        <th>商家</th>
        <th>登录名</th>
        <th>最后登陆IP</th>
        <th>最后登陆时间</th>
        <th>更新时间</th>
        <th>备注信息</th>
        <shiro:hasPermission name="reserve:reserveUser:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="reserveUser">
        <tr>
            <td><a href="${ctx}/reserve/reserveUser/form?id=${reserveUser.id}">
                    ${reserveUser.name}
            </a></td>
            <td>
                    ${reserveUser.company.name}
            </td>
            <td>
                    ${reserveUser.loginName}
            </td>
            <td>
                    ${reserveUser.loginIp}
            </td>
            <td>
                <fmt:formatDate value="${reserveUser.loginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <fmt:formatDate value="${reserveUser.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${reserveUser.remarks}
            </td>
            <shiro:hasPermission name="reserve:reserveUser:edit">
                <td>
                    <a href="${ctx}/reserve/reserveUser/form?id=${reserveUser.id}">修改</a>
                    <a href="${ctx}/reserve/reserveUser/delete?id=${reserveUser.id}"
                       onclick="return confirmx('确认要删除该商家用户吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
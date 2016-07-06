<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>菜单管理</title>
    <meta name="decorator" content="main"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>

</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="menu"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>菜单列表</h3>
                </div>
                <div class="breadcrumb form-search">
                    <div class="row">
                        <div class="pull-right">
                            <div class="col-sm-2 col-md-2 col-lg-2">
                                <a class="btn btn-success" href="${ctx}/reserve/reserveMenu/form">
                                    <i class="fa fa-plus"></i>添加
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <sys:msg content="${message}"/>
                <form:form id="searchForm" modelAttribute="reserveMenu" action="${ctx}/reserve/reserveMenu/list"
                           method="post">
                    <div class="content">
                        <div class="table-responsive">
                            <table id="treeTable">
                                <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>链接</th>
                                    <th style="text-align:center;">排序</th>
                                    <th>可见</th>
                                    <th>权限标识</th>
                                    <th>操作</th>
                                </thead>
                                <tbody><c:forEach items="${list}" var="menu">
                                    <tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
                                        <td nowrap><i class="icon-${not empty menu.icon?menu.icon:' hide'}"></i><a
                                                href="${ctx}/reserve/reserveMenu/form?id=${menu.id}">${menu.name}</a>
                                        </td>
                                        <td title="${menu.href}">${fns:abbr(menu.href,30)}</td>
                                        <td style="text-align:center;">
                                            <input type="hidden" name="ids" value="${menu.id}"/>
                                            <input name="sorts" type="text" value="${menu.sort}"
                                                   style="width:50px;margin:0;padding:0;text-align:center;">
                                                <%-- ${menu.sort}--%>
                                        </td>
                                        <td>${menu.isShow eq '1'?'显示':'隐藏'}</td>
                                        <td title="${menu.permission}">${fns:abbr(menu.permission,30)}</td>
                                        <td nowrap>
                                            <a href="${ctx}/reserve/reserveMenu/form?id=${menu.id}">修改</a>
                                            <a href="${ctx}/reserve/reserveMenu/delete?id=${menu.id}"
                                               onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
                                            <a href="${ctx}/reserve/reserveMenu/form?parent.id=${menu.id}">添加下级菜单</a>
                                        </td>
                                    </tr>
                                </c:forEach></tbody>
                            </table>
                        </div>
                    </div>
                    <div class="form-actions pagination-left">
                        <input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序"
                               onclick="updateSort();"/>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $("#treeTable").treeTable({expandLevel: 3}).show();
    });
    function updateSort() {
        $("#searchForm").attr("action", "${ctx}/reserve/reserveMenu/updateSort");
        $("#searchForm").submit();
    }
</script>
</body>
</html>
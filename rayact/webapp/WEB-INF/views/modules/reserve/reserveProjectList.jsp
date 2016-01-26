<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/reserve/reserveProject/">项目列表</a></li>
		<shiro:hasPermission name="reserve:reserveProject:edit"><li><a href="${ctx}/reserve/reserveProject/form">项目添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reserveProject" action="${ctx}/reserve/reserveProject/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>场地名称：</label>
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
				<th>场地名称</th>
				<th>是否启用</th>
				<th>备注</th>
				<shiro:hasPermission name="reserve:reserveProject:edit"><th>操作</th></shiro:hasPermission>
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
				<shiro:hasPermission name="reserve:reserveProject:edit"><td>
    				<a href="${ctx}/reserve/reserveProject/form?id=${reserveProject.id}">修改</a>
					<a href="${ctx}/reserve/reserveProject/delete?id=${reserveProject.id}" onclick="return confirmx('确认要删除该项目吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
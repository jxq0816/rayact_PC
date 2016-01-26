<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>场地管理</title>
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
		<li class="active"><a href="${ctx}/reserve/reserveField/">场地列表</a></li>
		<shiro:hasPermission name="reserve:reserveField:edit"><li><a href="${ctx}/reserve/reserveField/form">场地添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reserveField" action="${ctx}/reserve/reserveField/" method="post" class="breadcrumb form-search">
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
				<th>所属项目</th>
				<th>所属场馆</th>
				<th>是否启用</th>
				<shiro:hasPermission name="reserve:reserveField:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reserveField">
			<tr>
				<td><a href="${ctx}/reserve/reserveField/form?id=${reserveField.id}">${reserveField.name}</a></td>
				<td>${reserveField.reserveProject.name}</td>
				<td>${reserveField.reserveVenue.name}</td>
				<td>
					${fns:getDictLabel(reserveField.available, 'yes_no', '')}
				</td>
				<shiro:hasPermission name="reserve:reserveField:edit"><td>
    				<a href="${ctx}/reserve/reserveField/form?id=${reserveField.id}">修改</a>
					<a href="${ctx}/reserve/reserveField/delete?id=${reserveField.id}" onclick="return confirmx('确认要删除该场地吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
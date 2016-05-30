<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>战队管理</title>
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
		<li class="active"><a href="${ctx}/cms/team/">战队列表</a></li>
		<shiro:hasPermission name="cms:team:edit"><li><a href="${ctx}/cms/team/form">战队添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="team" action="${ctx}/cms/team/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>队伍名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>队伍名称</th>
				<th>位置</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>备注</th>
				<shiro:hasPermission name="cms:team:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="team">
			<tr>
				<td><a href="${ctx}/cms/team/form?id=${team.id}">
					${team.name}
				</a></td>
				<td>
						${team.position}
				</td>
				<td>
						${team.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${team.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${team.remarks}
				</td>
				<shiro:hasPermission name="cms:team:edit"><td>
    				<a href="${ctx}/cms/team/form?id=${team.id}">修改</a>
					<a href="${ctx}/cms/team/delete?id=${team.id}" onclick="return confirmx('确认要删除该战队吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
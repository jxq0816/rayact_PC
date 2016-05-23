<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>点赞管理</title>
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
		<li class="active"><a href="${ctx}/cms/attitude/">点赞列表</a></li>
		<li><a href="${ctx}/cms/attitude/form">点赞添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="attitude" action="${ctx}/cms/attitude/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>对象ID</th>
				<th>对象所属模块</th>
				<th>态度</th>
				<th>操作者</th>
				<th>时间</th>
				<shiro:hasPermission name="cms:attitude:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="attitude">
			<tr>
				<td>
					${attitude.modelId}
				</td>
				<td>${attitude.modelName}</td>
				<td>${attitude.status}</td>
				<td>${attitude.createBy.name}</td>
				<td><fmt:formatDate value="${attitude.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
    				<a href="${ctx}/cms/attitude/form?id=${attitude.id}">修改</a>
					<a href="${ctx}/cms/attitude/delete?id=${attitude.id}" onclick="return confirmx('确认要删除该点赞吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
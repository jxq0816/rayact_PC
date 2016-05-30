<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关注管理</title>
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
		<li class="active"><a href="${ctx}/cms/focus/">关注列表</a></li>
		<shiro:hasPermission name="cms:focus:edit"><li><a href="${ctx}/cms/focus/form">关注添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="focus" action="${ctx}/cms/focus/" method="post" class="breadcrumb form-search">
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
				<th>关注实例ID</th>
				<th>模块名</th>
				<th>关注人</th>
				<th>关注时间</th>
				<shiro:hasPermission name="cms:focus:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="focus">
			<tr>
				<td>
					${focus.modelId}
				</td>
				<td>
				    ${focus.modelName}
				</td>
				<td>
					${focus.fan.name}
				</td>
				<td>
					<fmt:formatDate value="${focus.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cms:focus:edit"><td>
    				<a href="${ctx}/cms/focus/form?id=${focus.id}">修改</a>
					<a href="${ctx}/cms/focus/delete?id=${focus.id}" onclick="return confirmx('确认要删除该关注吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
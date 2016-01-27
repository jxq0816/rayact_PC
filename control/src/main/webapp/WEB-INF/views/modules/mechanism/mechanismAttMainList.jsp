<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文档管理管理</title>
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
		<li class="active"><a href="${ctx}/mechanism/mechanismAttMain/">文档管理列表</a></li>
		<shiro:hasPermission name="mechanism:mechanismAttMain:edit"><li><a href="${ctx}/mechanism/mechanismAttMain/form">文档管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="mechanismAttMain" action="${ctx}/mechanism/mechanismAttMain/" method="post" class="breadcrumb form-search">
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
				<th>remarks</th>
				<th>update_date</th>
				<shiro:hasPermission name="mechanism:mechanismAttMain:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="mechanismAttMain">
			<tr>
				<td><a href="${ctx}/mechanism/mechanismAttMain/form?id=${mechanismAttMain.id}">
					${mechanismAttMain.remarks}
				</a></td>
				<td>
					<fmt:formatDate value="${mechanismAttMain.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="mechanism:mechanismAttMain:edit"><td>
    				<a href="${ctx}/mechanism/mechanismAttMain/form?id=${mechanismAttMain.id}">修改</a>
					<a href="${ctx}/mechanism/mechanismAttMain/delete?id=${mechanismAttMain.id}" onclick="return confirmx('确认要删除该文档管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
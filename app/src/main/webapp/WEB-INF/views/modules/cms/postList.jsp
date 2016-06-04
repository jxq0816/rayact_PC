<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>回帖管理</title>
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
		<li class="active"><a href="${ctx}/cms/post/">回帖列表</a></li>
		<shiro:hasPermission name="cms:post:edit"><li><a href="${ctx}/cms/post/form">回帖添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="post" action="${ctx}/cms/post/" method="post" class="breadcrumb form-search">
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
				<th>跟帖人</th>
				<th>内容</th>
				<th>所属帖子</th>
				<th>回复跟帖</th>
				<th>回复跟帖的回复</th>
				<th>回帖时间</th>
				<shiro:hasPermission name="cms:post:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="post">
			<tr>
				<td>${post.createBy.name}</td>
				<td>${post.content}</td>
				<td>${post.postMain.subject}</td>
				<td>${post.postId}</td>
				<td>${post.ptpId}</td>
				<td>
					<fmt:formatDate value="${post.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cms:post:edit"><td>
    				<a href="${ctx}/cms/post/form?id=${post.id}">修改</a>
					<a href="${ctx}/cms/post/delete?id=${post.id}" onclick="return confirmx('确认要删除该回帖吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
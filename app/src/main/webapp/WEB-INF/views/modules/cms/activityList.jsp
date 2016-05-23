<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动管理</title>
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
		<li class="active"><a href="${ctx}/cms/activity/">活动列表</a></li>
		<shiro:hasPermission name="cms:activity:edit"><li><a href="${ctx}/cms/activity/form">活动添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="activity" action="${ctx}/cms/activity/" method="post" class="breadcrumb form-search">
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
				<th>标题</th>
				<th>发起人</th>
				<th>所属分类</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>是否有效</th>
				<th>创建时间</th>
				<shiro:hasPermission name="cms:activity:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="activity">
			<tr>
				<td><a href="${ctx}/cms/activity/form?id=${activity.id}">
					${activity.subject}
				</a></td>
				<td>
					${activity.fkManagerName}
				</td>
				<td>${activity.category.name}</td>
				<td>
					<fmt:formatDate value="${activity.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${activity.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${activity.isAvaliable}
				</td>
				<td>
					<fmt:formatDate value="${activity.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cms:activity:edit"><td>
					<a href="${pageContext.request.contextPath}${fns:getFrontPath()}/view-${activity.category.id}-${activity.id}${fns:getUrlSuffix()}" target="_blank">访问</a>
    				<a href="${ctx}/cms/activity/form?id=${activity.id}">修改</a>
					<a href="${ctx}/cms/activity/delete?id=${activity.id}" onclick="return confirmx('确认要删除该活动吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
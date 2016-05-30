<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动报名管理</title>
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
		<li class="active"><a href="${ctx}/cms/apply/">活动报名列表</a></li>
		<shiro:hasPermission name="cms:apply:edit"><li><a href="${ctx}/cms/apply/form">活动报名添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="apply" action="${ctx}/cms/apply/" method="post" class="breadcrumb form-search">
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
				<th>活动</th>
				<th>报名人</th>
				<th>电话</th>
				<th>年龄</th>
				<th>性别</th>
				<th>申请人</th>
				<th>时间</th>
				<shiro:hasPermission name="cms:apply:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="apply">
			<tr>
				<td><a href="${ctx}/cms/activity/form?id=${apply.activity.id}">
					${apply.activity.subject}
				</a></td>
				<td><a href="${ctx}/cms/apply/form?id=${apply.id}">
						${apply.name}
				</a></td>
				<td>
						${apply.phone}
				</td>
				<td>
						${apply.age}
				</td>
				<td>
						${apply.sex}
				</td>
				<td>
						${apply.user.name}
				</td>
				<td>
					<fmt:formatDate value="${apply.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cms:apply:edit"><td>
    				<a href="${ctx}/cms/apply/form?id=${apply.id}">修改</a>
					<a href="${ctx}/cms/apply/delete?id=${apply.id}" onclick="return confirmx('确认要删除该活动报名吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
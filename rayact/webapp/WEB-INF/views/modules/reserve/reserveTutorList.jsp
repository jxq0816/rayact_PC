<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>教练管理</title>
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
		<li class="active"><a href="${ctx}/reserve/reserveTutor/">教练列表</a></li>
		<shiro:hasPermission name="reserve:reserveTutor:edit"><li><a href="${ctx}/reserve/reserveTutor/form">教练添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reserveTutor" action="${ctx}/reserve/reserveTutor/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
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
				<th>姓名</th>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="reserve:reserveTutor:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reserveTutor">
			<tr>
				<td><a href="${ctx}/reserve/reserveTutor/form?id=${reserveTutor.id}">
					${reserveTutor.name}
				</a></td>
				<td>
					<fmt:formatDate value="${reserveTutor.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${reserveTutor.remarks}
				</td>
				<shiro:hasPermission name="reserve:reserveTutor:edit"><td>
    				<a href="${ctx}/reserve/reserveTutor/form?id=${reserveTutor.id}">修改</a>
					<a href="${ctx}/reserve/reserveTutor/delete?id=${reserveTutor.id}" onclick="return confirmx('确认要删除该教练吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
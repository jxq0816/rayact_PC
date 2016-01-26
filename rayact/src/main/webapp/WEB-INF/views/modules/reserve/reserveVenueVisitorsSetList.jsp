<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人次票设置管理</title>
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
		<li class="active"><a href="${ctx}/reserve/reserveVenueVisitorsSet/">人次票设置列表</a></li>
		<shiro:hasPermission name="reserve:reserveVenueVisitorsSet:edit"><li><a href="${ctx}/reserve/reserveVenueVisitorsSet/form">人次票设置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reserveVenueVisitorsSet" action="${ctx}/reserve/reserveVenueVisitorsSet/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>name：</label>
				<form:input path="name" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>project_id：</label>
				<form:input path="projectId" htmlEscape="false" maxlength="19" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>name</th>
				<th>project_id</th>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="reserve:reserveVenueVisitorsSet:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reserveVenueVisitorsSet">
			<tr>
				<td><a href="${ctx}/reserve/reserveVenueVisitorsSet/form?id=${reserveVenueVisitorsSet.id}">
					${reserveVenueVisitorsSet.name}
				</a></td>
				<td>
					${reserveVenueVisitorsSet.projectId}
				</td>
				<td>
					<fmt:formatDate value="${reserveVenueVisitorsSet.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${reserveVenueVisitorsSet.remarks}
				</td>
				<shiro:hasPermission name="reserve:reserveVenueVisitorsSet:edit"><td>
    				<a href="${ctx}/reserve/reserveVenueVisitorsSet/form?id=${reserveVenueVisitorsSet.id}">修改</a>
					<a href="${ctx}/reserve/reserveVenueVisitorsSet/delete?id=${reserveVenueVisitorsSet.id}" onclick="return confirmx('确认要删除该人次票设置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>青草球队队员管理</title>
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
		<li class="active"><a href="${ctx}/cms/teamMemberTmp/">青草球队队员列表</a></li>
		<shiro:hasPermission name="cms:teamMemberTmp:edit"><li><a href="${ctx}/cms/teamMemberTmp/form">青草球队队员添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="teamMemberTmp" action="${ctx}/cms/teamMemberTmp/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
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
				<th>姓名</th>
				<th>所属队伍</th>
				<th>角色</th>
				<th>电话</th>
				<th>身份证</th>
				<shiro:hasPermission name="cms:teamMemberTmp:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="teamMemberTmp">
			<tr>
				<td><a href="${ctx}/cms/teamMemberTmp/form?id=${teamMemberTmp.id}">
					${teamMemberTmp.name}
				</a></td>
				<td>
					${teamMemberTmp.teamTmp.name}
				</td>
				<td>
					<c:if test="${teamMemberTmp.role == '1'}">
						领队
					</c:if>
					<c:if test="${teamMemberTmp.role == '2'}">
						教练
					</c:if>
					<c:if test="${teamMemberTmp.role == '3'}">
						队员
					</c:if>
				</td>
				<td>
				    ${teamMemberTmp.phone}
				</td>
				<td>
					${teamMemberTmp.cardNo}
				</td>
				<shiro:hasPermission name="cms:teamMemberTmp:edit"><td>
    				<a href="${ctx}/cms/teamMemberTmp/form?id=${teamMemberTmp.id}">修改</a>
					<a href="${ctx}/cms/teamMemberTmp/delete?id=${teamMemberTmp.id}" onclick="return confirmx('确认要删除该青草球队队员吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
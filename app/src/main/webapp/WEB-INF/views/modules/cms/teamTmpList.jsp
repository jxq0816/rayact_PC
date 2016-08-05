<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>青草球队管理</title>
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
		<li class="active"><a href="${ctx}/cms/teamTmp/">青草球队列表</a></li>
		<shiro:hasPermission name="cms:teamTmp:edit"><li><a href="${ctx}/cms/teamTmp/form">青草球队添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="teamTmp" action="${ctx}/cms/teamTmp/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>球队名：</label>
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
				<th>球队名</th>
				<th>组别</th>
				<th>制式</th>
				<th>状态</th>
				<shiro:hasPermission name="cms:teamTmp:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="teamTmp">
			<tr>
				<td><a href="${ctx}/cms/teamTmp/info?id=${teamTmp.id}" target="_blank">
					${teamTmp.name}
				</a></td>
				<td>
					${teamTmp.zb}
				</td>
				<td>
					<c:if test="${teamTmp.rz == '5'}">五人制</c:if>
					<c:if test="${teamTmp.rz == '8'}">八人制</c:if>
				</td>
				<td>
					<c:if test="${teamTmp.delFlag == '0'}">待审</c:if>
					<c:if test="${teamTmp.delFlag == '1'}">驳回</c:if>
					<c:if test="${teamTmp.delFlag == '2'}">通过</c:if>
				</td>
				<shiro:hasPermission name="cms:teamTmp:edit"><td>
    				<a href="${ctx}/cms/teamTmp/form?id=${teamTmp.id}">修改</a>
					<a href="${ctx}/cms/teamTmp/delete?id=${teamTmp.id}" onclick="return confirmx('确认要删除该青草球队吗？', this.href)">删除</a>
					<a href="${ctx}/cms/teamTmp/changeStatus?id=${teamTmp.id}&delFlag=2" onclick="return confirmx('确认要审核通过该青草球队吗？', this.href)">通过</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
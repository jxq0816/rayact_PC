<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>举报管理</title>
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
		<li class="active"><a href="${ctx}/cms/cmsReport/">举报列表</a></li>
		<shiro:hasPermission name="cms:cmsReport:edit"><li><a href="${ctx}/cms/cmsReport/form">举报添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cmsReport" action="${ctx}/cms/cmsReport/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>模块名称：</label>
				<form:select path="modelName" htmlEscape="false" maxlength="255" class="input-medium">
					<form:option value="">请选择</form:option>
					<form:option value="article">资讯</form:option>
					<form:option value="postMain">帖子</form:option>
					<form:option value="user">人</form:option>
					<form:option value="group">圈子</form:option>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>实例ID</th>
				<th>模块名</th>
				<th>举报原因</th>
				<th>举报时间</th>
				<shiro:hasPermission name="cms:cmsReport:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cmsReport">
			<tr>
				<td>${cmsReport.modelId}</td>
				<td>${cmsReport.modelName}</td>
				<td>${cmsReport.remarks}</td>
				<td>
					<fmt:formatDate value="${cmsReport.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cms:cmsReport:edit"><td>
    				<a href="${ctx}/cms/cmsReport/form?id=${cmsReport.id}">修改</a>
					<a href="${ctx}/cms/cmsReport/delete?id=${cmsReport.id}" onclick="return confirmx('确认要删除该举报吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
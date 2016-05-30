<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>话题管理</title>
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
		<li class="active"><a href="${ctx}/cms/cmsSubject/">话题列表</a></li>
		<shiro:hasPermission name="cms:cmsSubject:edit"><li><a href="${ctx}/cms/cmsSubject/form">话题添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cmsSubject" action="${ctx}/cms/cmsSubject/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>话题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>话题</th>
				<th>所属圈子</th>
				<th>创建时间</th>
				<th>备注</th>
				<shiro:hasPermission name="cms:cmsSubject:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cmsSubject">
			<tr>
				<td><a href="${ctx}/cms/cmsSubject/form?id=${cmsSubject.id}">
					${cmsSubject.title}
				</a></td>
				<td>
					${cmsSubject.group.name}
				</td>
				<td>
					<fmt:formatDate value="${cmsSubject.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cmsSubject.remarks}
				</td>
				<shiro:hasPermission name="cms:cmsSubject:edit"><td>
    				<a href="${ctx}/cms/cmsSubject/form?id=${cmsSubject.id}">修改</a>
					<a href="${ctx}/cms/cmsSubject/delete?id=${cmsSubject.id}" onclick="return confirmx('确认要删除该话题吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
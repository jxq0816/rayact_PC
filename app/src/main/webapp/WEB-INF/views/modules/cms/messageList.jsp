<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息管理</title>
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
		<li class="active"><a href="${ctx}/cms/message/">消息列表</a></li>
		<shiro:hasPermission name="cms:message:edit"><li><a href="${ctx}/cms/message/form">消息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="message" action="${ctx}/cms/message/" method="post" class="breadcrumb form-search">
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
				<th>消息标题</th>
				<th>投放模块名</th>
				<th>投放实例ID</th>
				<th>消息内容</th>
				<th>消息链接</th>
				<th>消息来源ID</th>
				<th>消息来源模块</th>
				<th>发布时间</th>
				<shiro:hasPermission name="cms:message:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="message">
			<tr>
				<td>
					${message.subject}
				</td>
				<td>
					${message.targetId}
				</td>
				<td>
						${message.targetName}
				</td>
				<td>
						${message.content}
				</td>
				<td>
						${message.url}
				</td>
				<td>
						${message.sendId}
				</td>
				<td>
						${message.sendName}
				</td>
				<td>
					<fmt:formatDate value="${message.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cms:message:edit"><td>
    				<a href="${ctx}/cms/message/form?id=${message.id}">修改</a>
					<a href="${ctx}/cms/message/delete?id=${message.id}" onclick="return confirmx('确认要删除该消息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
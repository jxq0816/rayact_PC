<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>战队成员管理</title>
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
		//全选、取消全选的事件
		function selectAll(){
			if ($("input[name='selectAll']").attr("checked")) {
				$(":checkbox").attr("checked", true);
			} else {
				$(":checkbox").attr("checked", false);
			}
		}
		function deleteAll(){
			if(confirm("确认删除？")){
				var delIds = [];
				$("input[name='ids']:checked").each(function(){
					delIds.push($(this).val());
				});
				if(delIds.length<=0){
					alert("未选中删除项");
					return;
				}
				$.post("${ctx}/cms/teamMember/deleteAll",{ids:delIds},function(data){
					alert(data);
					location.reload();
				})
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cms/teamMember/">战队成员列表</a></li>
		<shiro:hasPermission name="cms:teamMember:edit"><li><a href="${ctx}/cms/teamMember/form">战队成员添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="teamMember" action="${ctx}/cms/teamMember/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="批量删除" onclick="deleteAll();"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input type="checkbox" name="selectAll" onclick="selectAll();"/></th>
				<th>姓名</th>
				<th>电话</th>
				<th>特长</th>
				<th>所属队伍</th>
				<shiro:hasPermission name="cms:teamMember:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="teamMember">
			<tr>
				<td><input type="checkbox" name="ids" value="${teamMember.id}"></td>
				<td><a href="${ctx}/cms/teamMember/form?id=${teamMember.id}">
					${teamMember.name}
				</a></td>
				<td>
					${teamMember.phone}
				</td>
				<td>
					${teamMember.role}
				</td>
				<td>
				    ${teamMember.team.name}
				</td>
				<shiro:hasPermission name="cms:teamMember:edit"><td>
    				<a href="${ctx}/cms/teamMember/form?id=${teamMember.id}">修改</a>
					<a href="${ctx}/cms/teamMember/delete?id=${teamMember.id}" onclick="return confirmx('确认要删除该战队成员吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
<link href="${ctxStatic}/jquery-select2/3.4/select2.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/common/jeesite.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/2.3.1/custom-theme/assets/js/vendor/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.method.min.js" type="text/javascript"></script>
<style type="text/css">.reply{border:1px solid #ddd;background:#fefefe;margin:10px;}</style>
<script type="text/javascript">
	function comment(id,name){
			$("#commentForm0  input[name='replyId']").val(id);
		    $("#commentForm0  textarea[name='content']").attr("placeholder","回复"+name+":");
			$("#commentForm0  textarea[name='content']").focus();
	}
	function ajSub(dom){
		$.post("${ctx}/comment",$(dom).closest("form").serialize(),function(data){
			alert(data);
			location.replace(location);
		})
	}
</script>
<h5>评论列表</h5>
<ul>
	<c:forEach items="${page.list}" var="comment">
		<li>
			<h6>姓名: ${comment.name} &nbsp;时间：<fmt:formatDate value="${comment.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				<a href="javascript:comment('${comment.id}','${comment.name}')">回复</a></h6>
			<div>${comment.content}</div>
		</li>
	</c:forEach>
	<c:if test="${fn:length(page.list) eq 0}">
		<li>暂时还没有人评论！</li>
	</c:if>
</ul>
<div class="pagination">${page}</div>
<h5>我要评论</h5>
<div id="commentForm0">
	<form:form action="${ctx}/comment" method="post" class="form-horizontal" name="cc">
		<input type="hidden" name="category.id" value="${comment.category.id}"/>
		<input type="hidden" name="contentId" value="${comment.contentId}"/>
		<input type="hidden" name="title" value="${comment.title}"/>
		<input type="hidden" name="replyId"/>
		<div class="control-group">
			<label class="control-label">留言内容:</label>
			<div class="controls">
				<textarea name="content" rows="4" maxlength="200" class="txt required" style="width:400px;"></textarea>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<sys:treeselect id="auditUser" name="auditUser.id" value="${comment.auditUser.id}" labelName="comment.auditUser.name" labelValue="${comment.auditUser.name}"
								title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true"/>
				<input class="btn mid" type="button" value="提 交" onclick="ajSub(this)"/>&nbsp;
			</div>
		</div>
		<div class="alert alert-error messageBox" style="display:none">输入有误，请先更正。</div>
	</form:form>
</div>
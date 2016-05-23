<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>${activity.subject} - ${category.name}</title>
	<script type="text/javascript">
		$(document).ready(function() {
			if ("${category.allowComment}"=="1"){
				$("#comment").show();
				page(1);
			}
		});
		function page(n,s){
			$.get("${ctx}/comment",{theme: '${site.theme}', 'category.id': '${category.id}',
				contentId: '${activity.id}', title: '${activity.subject}', pageNo: n, pageSize: s, date: new Date().getTime()
			},function(data){
				$("#comment").html(data);
			});
		}
	</script>
</head>
<body>
	<div class="row">
	   <div class="span10">
	     <div class="row">
	       <div class="span10">
			   <h3 style="color:#555555;font-size:20px;text-align:left;padding-bottom:15px;margin:10px 0;">${activity.subject}</h3>
			   <div style="border-bottom:1px solid #ddd;padding:10px;margin:5px 0;">${activity.fkManagerName} &nbsp; <fmt:formatDate value="${activity.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </div>
			   <div>${activity.content}</div>
  	       </div>
  	     </div>
	     <div class="row">
			<div id="comment" class="hide span10">
				<div class="applyBtn">我要报名</div>
			</div>
	     </div>
  	  </div>
   </div>
<script>

</script>
</body>
</html>
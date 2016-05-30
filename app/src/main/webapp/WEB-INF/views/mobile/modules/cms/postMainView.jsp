<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>${postMain.subject}</title>
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-mobile/list.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/bootstrap/2.3.1/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript">
		$(document).ready(function() {
			if ("${category.allowComment}"=="1" && "${article.articleData.allowComment}"=="1"){
				$("#comment").show();
				page(1);
			}
		});
		function page(n,s){
			$.get("${ctx}/mobile/comment",{theme: '${site.theme}', 'category.id': '${category.id}',
				contentId: '${article.id}', title: '${article.title}', pageNo: n, pageSize: s, date: new Date().getTime()
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
			<h3 style="color:#555555;font-size:20px;text-align:left;margin:10px 0;">${postMain.subject}</h3>
			   <div style="border-bottom:1px solid #ddd;padding:10px;margin:5px 0;">${postMain.createBy.name} <div style="float: right"><fmt:formatDate value="${postMain.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div></div>
			   <c:forEach items="${imgs}" var="url">
				   <img src="${url}" width="" height=""/>
			   </c:forEach>
			   <div>${postMain.content}</div>
  	       </div>
			 <div class="span10">
				 <h3 style="color:#555555;font-size:20px;text-align:left;margin:10px 0;">回帖</h3>
					 <c:forEach items="${ptm}" var="post">
						 <div class="row" style="margin-left: 20px"><img src="${post.createBy.photo}" onclick="jumpToInfo()" height="50px" width="50px" class="img-circle" name="${post.createBy.id}">${post.createBy.name}: &nbsp;${post.content} &nbsp; <fmt:formatDate value="${post.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
						 <div  name="${post.id}" class="ptp">

						 </div>
					 </c:forEach>
				     <a name="" onclick="jumpToApp()">查看更多 </a>
			 </div>
  	     </div>
  	  </div>
   </div>
<script>
	$(function(){
		$(".ptp").each(function(){
			var postId = $(this).attr("name");
			var $dom = $(this);
			$.get("/app/a/cms/post/app/ptp?postId="+postId,function(data){
				var vo = eval(data);
				if (vo&&vo.length>0){
					for(var i = 0 ; i < vo.length ; i++){
						$dom.append("<div style='margin-left: 20px;'>"+vo[i].createBy.name+":"+vo[i].content+"&nbsp;&nbsp;"+ vo[i].createDate +"</div>");
					}
				}
			});
		});
	});
	function jumpToApp(){
		iOSskip();
	}
	function jumpToInfo(){
		var userId = $(this).attr("name");
		return userId;
	}
</script>
</body>
</html>
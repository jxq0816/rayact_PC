<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title></title>

	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-mobile/list.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/bootstrap/2.3.1/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />

</head>
<body>
<div class="row">
	<style>
		.round {
			width:140px; height:140px;
			-moz-border-radius: 70px;      /* Gecko browsers */
			-webkit-border-radius: 70px;   /* Webkit browsers */
			border-radius:70px;            /* W3C syntax */
		}
		.row{
			margin-left: 5px;
		}
		.subject{
			font-size:60px;
			line-height:65px;
			width:83%;
			margin:10px 10px;
			display: inline-block;
			font-weight:bold;
			float: right;
		}
		.postli{
			margin:0 5px;
			padding-bottom: 10px;
			border-bottom: 1px solid #C8C8C8;
		}
		.after_img{
			display: inline-block;position: relative;bottom: -30px;line-height: 50px
		}
		.post_content{
			margin-left:140px;
			line-height: 150%;
			font-size: 42px
		}
		.ptp_name{
			color:#7a7a7a;
			margin-top:10px;
			font-size: 35px;
			display: inline-block
		}
		.ptp_content{
			font-size: 40px;display: inline-block;color:#1e1e1e
		}
	</style>
	<div class="row">
		<div style="margin:10px 0px;display: inline-block">
			<img class="round" src="${postMain.createBy.photo}" name="${postMain.createBy.id}" onclick="jumpToInfo(this)">
		</div>
		<div class="subject">${postMain.subject}</div>
		<div style="display: inline-block;line-height: 80px;border-bottom: 1px solid #c8c8c8;width: 100%">
			<span style="color: #f0860c;font-size: 40px">${postMain.createBy.name}</span>
			<span style="color:#8C8C8C;font-size: 40px">&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${postMain.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
		</div>
		<div style="line-height: 150%;font-size: 50px;color:#555555;margin-top: 30px;word-wrap:break-word;word-break:break-all;">${postMain.content}</div>
		<c:forEach items="${imgs}" var="url">
			<img src="${url}" width="100%" style="margin: 5px"/>
		</c:forEach>
	</div>
</div>
<div class="postSubject"><div style="border-left:13px solid #f0860c;border-right:3px solid #f0860c;display:inline-block;margin-right: 15px">&nbsp;</div>回帖(${count})</div>
<div id="wrapper" style="position: relative;">
	<ul>
		<c:forEach items="${ptm}" var="post" varStatus="status">
			<li class="row postli">
				<div class="row" style="display: block;">
					<div style="display: inline-block">
						<img src="${post.createBy.photo}" class="round" name="${post.createBy.id}" onclick="jumpToInfo(this)">
					</div>
					<div class="after_img">
						<span style="color: #f0860c;font-size: 40px">${post.createBy.name}</span>
						<br>
						<span style="color:#7a7a7a; font-size: 40px"> 第${status.index + 1}楼 &nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${post.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
					</div>
					<!--<img src="${ctxStatic}/images/btn-huifu-h@2x.png" height="50px" width="100px" style="float: right;display: inline-block;"/>-->
					<div class="post_content">${post.content}</div>
				</div>
				<div  name="${post.id}" class="ptp"  style="margin-left:140px;line-height:60px">

				</div>
			</li>
		</c:forEach>
	</ul>
</div>

<script type="text/javascript">
	$(function(){
		$(".ptp").each(function(){
			var postId = $(this).attr("name");
			var $dom = $(this);
			$.get("${ctx}/cms/post/app/list?postId="+postId,function(data){
				var vo = eval(data);
				if (vo&&vo.length>0){
					for(var i = 0 ; i < vo.length ; i++){
						$dom.append("<div class='ptp_name'>"+vo[i].pname+":<span class='ptp_content'>"+vo[i].content+"</span></div>");
					}
				}
			});
		});
	});
	var pageNo = 1;
	var postMainId = "${postMain.id}";
	$(window).scroll(function(){
		var scrollTop = $(this).scrollTop();
		var scrollHeight = $(document).height();
		var windowHeight = $(this).height();
		if(scrollTop + windowHeight+28 >= scrollHeight){
			pageNo++;
			$.post("${ctx}/cms/post/app/list",{postMain:{id:postMainId},pageNo:pageNo,pageSize:5},function(data){
				var rtn = eval(data);
				var length = rtn.length;
				for(var i = 0;i<length;i++){
					var tmp = rtn[i];
					var tmpInner = tmp.pop;
					var htmlInner = "";
					if(tmpInner){
						for(var j=0;j<tmpInner.length;j++){
							htmlInner+="<div class='ptp_name'>"+tmpInner[j].createByName+":<span class='ptp_content'>"+tmpInner[j].content+"</span></div>";
						}
					}
					var html="<li class='row postli'>"+
					"<div class='row' style='display: block'>"+
					"<div style='display: inline-block'>"+
					"<img src='"+tmp.photo+"' class='round' name='"+tmp.pid+"' onclick='jumpToInfo(this)'>"+
					"</div>"+
					"<div class='after_img'>"+
					"<span style='color: #f0860c;font-size: 40px'>"+tmp.pname+"</span>"+
					"<br>"+
					"<span style='color:#7a7a7a; font-size: 40px'> 第楼 &nbsp;&nbsp;&nbsp;&nbsp;"+tmp.time+"</span>"+
					"</div>"+
					"<img src='${ctxStatic}/images/btn-huifu-h@2x.png' height='50px' width='100px' style='float: right;display: inline-block;'/>"+
					"<div class='post_content'>"+tmp.content+"</div>"+
					"</div>"+
					"<div  name='"+tmp.id+"' class='ptp'  style='margin-left:140px;line-height:60px'>"+htmlInner+
					"</div>"+
					"</li>";
					$("#wrapper ul").append(html);
				}
			});

		}
	});
</script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-mobile/list.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/bootstrap/2.3.1/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div class="row" style="margin-left: 20px;margin-top: 30px">
	<div style="border-left:5px solid #f0860c;color:#555555;font-size:45px;text-align:left;width: 25%;display:inline-block">队员信息</div><div style="border-bottom:1px solid #C8C8C8;width: 70%;display:inline-block"></div>
	<form action="a/cms/teamMember/app/save" name="membersForm">
		<c:forEach items="${members}" var="member" varStatus="status">
			<div class="row" style="border-bottom:1px solid #C8C8C8;padding: 10px">
				<div class="row" style="margin:10px 20px;height: 50px;font-size: 45px;">
					姓名：<input type="text" name="member[${status.index}].name" value="${member.name}" style="width:80%;height: 45px;font-size: 40px">
				</div>
				<div class="row" style="margin:10px 20px;height: 50px;font-size: 45px">
					电话：<input type="text" name="member[${status.index}].phone" value="${member.phone}" readonly style="width:80%;height: 45px;font-size: 40px">
				</div>
				<div class="row" style="margin:10px 20px;height: 50px;font-size: 45px">
					位置：<c:if test="${member.role == null || member.role == 'null' || member.role == ''}"><input type="text" name="member[${status.index}].role" value="" style="width:80%;height: 45px;font-size: 40px"></c:if><c:if test="${member.role != null && member.role != 'null' && member.role != ''}"><input type="text" name="member[${status.index}].role" value="${member.role}" style="width:80%;height: 45px;font-size: 40px"></c:if>
				</div>
			</div>
		</c:forEach>
	</form>
	<div class="row" style="align:center;height: 60px;background-color: #f0860c;">
		<div onclick="jumpToApp()" style="margin:10px;text-align:center;font-size: 40px" >提交</div>
	</div>
</div>
<script>
	function jumpToApp(){
		var b = $("form[name='membersForm']").serializeArray();
		var a={};
		var numreg = /\[[0-9]*\]\./;
		var index = 0;
		var attnum = 3;
		var tmp = 0 ;
		$.each(b,function(n,v){
			var name = v.name;
			var names = name.split(numreg);
			if(names.length > 1){//数组属性
				if(!a[names[0]]) a[names[0]]= [];
				if(!a[names[0]][index]) a[names[0]][index]= {};
				a[names[0]][index][names[1]] = v.value;
				tmp++;
				if((tmp)%attnum==0){
					index++;
				}
			}else{//普通属性
				a[v.name] = v.value;
			}
		});
		$.post("save",{'members':JSON.stringify(a),'teamId':'${param.teamId}'},function(){
			iOSskipToTeam();
		});
	}
</script>
</body>
</html>
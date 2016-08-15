<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/reserve/css/login.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/cleanzone/css/style.css">
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function () {
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."}, password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function (error, element) {
					error.appendTo($("#loginError").parent());
				}
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if (self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0) {
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
<!--[if lte IE 6]><br/>
<div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a>
	<h4>温馨提示：</h4>
	<p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的
		Chrome、Firefox、Safari 等。</p></div><![endif]-->
<div class="form-signin">
	<form id="loginForm" action="${ctx}/login" method="post">
		<div class="login_form">
			<h1 class="form-signin-heading">${fns:getConfig('productName')}</h1>
			<div class="row">
				<input type="text" id="username" name="username" class="input-block-level required" value="${username}"
					   placeholder="请输入用户名"/>
			</div>
			<div class="row">
				<input type="password" id="password" name="password" class="input-block-level required"
					   placeholder="密码"/>
			</div>
			<c:if test="${isValidateCodeLogin}">
				<div class="row validateCode" style="margin-bottom: 10px">
					<sys:validateCode name="validateCode"/>
				</div>
			</c:if>
			<div class="row" style="margin-bottom: 20px">
				<div class="col-lg-6" style="padding-left:0px ">
					<label for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe"
																	name="rememberMe" ${rememberMe ? 'checked' : ''}/>下次自动登录</label>
				</div>
				<div class="col-lg-6">
					<div id="messageBox">
						<div id="loginError" style="color: red">${message}</div>
					</div>
				</div>
			</div>
			<div class="row">
				<input class="btn btn-large btn-primary" style="width: 100%;height: 56px; border-radius: 5px;"
					   type="submit"
					   value="登 录"/>
			</div>
		</div>
	</form>
</div>
</body>
</html>
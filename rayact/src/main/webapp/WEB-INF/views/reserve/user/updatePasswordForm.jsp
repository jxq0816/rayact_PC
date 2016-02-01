<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>修改密码</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="venue"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="page-head">
        <h3>修改密码</h3>
    </div>
    <div class="cl-mcont">
        <div class="block-flat">
            <form:form id="inputForm" modelAttribute="user" action="${ctx}/reserve/user/updatePasswordSubmit"
                       class="form-horizontal" role="form" onsubmit="return checkForm()">
                <form:hidden id="id" path="id"/>
                <input type="hidden" name="token" value="${token}"/>
                <sys:msg content="${message}"/>
                <div class="form-group">
                    <label class="col-sm-2 control-label">工号:</label>
                    <div class="col-sm-4">
                            ${user.no}
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">姓名:</label>
                    <div class="col-sm-4">
                            ${user.name}
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">登录名:</label>
                    <div class="col-sm-4">
                       ${user.loginName}
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">原始密码:</label>

                    <div class="col-sm-4">
                        <input id="oldPassword" name="oldPassword" type="password" value=""
                               maxlength="50" minlength="3"
                               class="required form-control"/>
                        <span class="help-inline"> <font color="red">*</font></span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">请输入密码:</label>

                    <div class="col-sm-4">
                        <input id="password" name="password" type="password" value=""
                               maxlength="50" minlength="3"
                               class="required form-control"/>
                        <span class="help-inline"> <font color="red">*</font></span>
                    </div>
                </div>


                <div class="form-group">
                    <label class="col-sm-2 control-label">请再次输入密码:</label>

                    <div class="col-sm-4">
                        <input id="password2" name="password2" type="password" value=""
                               maxlength="50" minlength="3"
                               class="required form-control"/>
                        <span class="help-inline"> <font color="red">*</font></span>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" id="btnSubmit" class="btn btn-primary">保存</button>
                        <button class="btn btn-default" onclick="history.go(-1)">返回</button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/update_password_form.js"> </script>
</body>
</html>

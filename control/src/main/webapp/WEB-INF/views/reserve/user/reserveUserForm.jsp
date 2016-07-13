<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商家用户管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate({
                messages:{
                    confirmNewPassword: {
                        equalTo: "两次密码输入不一致"
                    }
                },
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/reserve/reserveUser/">商家用户列表</a></li>
    <li class="active"><a href="${ctx}/reserve/reserveUser/form?id=${reserveUser.id}">商家用户<shiro:hasPermission
            name="reserve:reserveUser:edit">${not empty reserveUser.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="reserve:reserveUser:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="reserveUser" action="${ctx}/reserve/reserveUser/save" method="post"
           class="form-horizontal" onsubmit="return checkLoginName()">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">归属公司：</label>
        <div class="controls">
            <sys:select cssClass="input-large" name="company.id"
                        cssStyle="width:16%"
                        value="${reserveUser.company.id}"
                        items="${reserveOfficeList}" itemLabel="name" itemValue="id"
                        defaultLabel="----请选择-----"
                        defaultValue=""></sys:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">登录名：</label>
        <div class="controls">
            <input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
            <form:input path="loginName" id="loginName" htmlEscape="false" maxlength="100"
                        class="input-xlarge required"/>
            <span class="help-inline" id="checkLoginNameResult" style="color: red ">* </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">密码：</label>
        <div class="controls">
            <input id="password" name="password" type="password" value="" maxlength="50" minlength="3"
                   class="<c:if test="${empty reserveUser.id}">required</c:if>"/>
            <c:if test="${empty reserveUser.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
            <c:if test="${not empty reserveUser.id}"><span class="help-inline">若不修改密码，请留空</span></c:if>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">确认密码:</label>
        <div class="controls">
            <input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50"
                   minlength="3" equalTo="#password"/>
            <c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">工号：</label>
        <div class="controls">
            <form:input path="no" htmlEscape="false" maxlength="100" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">姓名：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">邮箱：</label>
        <div class="controls">
            <form:input path="email" htmlEscape="false" maxlength="200" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">电话：</label>
        <div class="controls">
            <form:input path="phone" htmlEscape="false" maxlength="200" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机：</label>
        <div class="controls">
            <form:input path="mobile" htmlEscape="false" maxlength="200" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户类型：</label>
        <div class="controls">
            超级管理员
            <form:input path="userType" value="1" type="hidden" htmlEscape="false" maxlength="1" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否可登录：</label>
        <div class="controls">
            <form:select path="loginFlag" cssClass="input-large" cssStyle="width: 16%">
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">授权码：</label>
        <div class="controls">
            <form:input path="checkoutPwd" htmlEscape="false" maxlength="45" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注信息：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="reserve:reserveUser:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                                    value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/reserve_user_check_loginName.js"></script>
</body>
</html>
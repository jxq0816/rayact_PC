<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>会员管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
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
    <li><a href="${ctx}/reserve/storedCardMember/list">储值卡会员列表</a></li>
    <li class="active"><a href="${ctx}/reserve/storedCardMember/form?id=${reserveMember.id}">储值卡会员<shiro:hasPermission
            name="reserve:reserveMember:edit">${not empty reserveMember.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="reserve:reserveMember:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="reserveMember" action="${ctx}/reserve/storedCardMember/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table id="contentTable" class="table table-bordered">
        <tr>
            <td>姓名:</td>
            <td><form:input path="name" htmlEscape="false" maxlength="30" class="input-xlarge required "/>
                <span class="help-inline"><font color="red">*</font> </span></td>
            <td>手机号:</td>
            <td>
                <form:input path="mobile" htmlEscape="false" maxlength="20" class="input-xlarge mobile required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </td>
        </tr>
        <tr>
            <td>密码:</td>
            <td>
                <form:input path="password" htmlEscape="false" maxlength="16" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </td>

            <td>身份证:</td>
            <td>
                <form:input path="sfz" htmlEscape="false" maxlength="18" class="input-xlarge "/>
            </td>
        </tr>

        <tr>
            <td>地址:</td>
            <td>
                <form:input path="address" htmlEscape="false" maxlength="100" class="input-xlarge "/>
            </td>

            <td>邮箱:</td>
            <td>
                <form:input path="email" htmlEscape="false" maxlength="30" class="input-xlarge "/>
            </td>
        </tr>
        <tr>
            <td>性别:</td>
            <td>
                <form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value"
                                   htmlEscape="false"/>
            </td>

            <td>卡号:</td>
            <td>
                <form:input path="cartno" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </td>
        </tr>
        <tr>
            <td>余额:</td>
            <td colspan="3">
                <form:input path="remainder" htmlEscape="false" maxlength="20" class="input-xlarge "/>
            </td>
        </tr>

        <tr>
            <td>备注:</td>
            <td colspan="3">
                <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
            </td>
        </tr>
    </table>
    <div>
        <shiro:hasPermission name="reserve:reserveMember:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                      type="submit"
                                                                      value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
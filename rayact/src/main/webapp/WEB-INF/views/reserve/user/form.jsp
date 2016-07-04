<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理</title>
    <meta name="decorator" content="main"/>
    <%@include file="/WEB-INF/views/include/upload.jsp" %>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="user"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="page-head">
        <h3>用户管理</h3>
    </div>
    <div class="cl-mcont">
        <div class="block-flat">
            <form:form id="inputForm" modelAttribute="user" action="${ctx}/reserve/user/save"
                       class="form-horizontal" role="form">
                <form:hidden path="id"/>
                <input type="hidden" name="token" value="${token}"/>
                <sys:msg content="${message}"/>
                <div class="form-group">
                    <label class="col-sm-2 control-label">集团:</label>

                    <div class="col-sm-4">
                        <p class="form-control-static">${user.company.name}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">工号:</label>

                    <div class="col-sm-4">
                        <form:input path="no" htmlEscape="false" maxlength="50" class="required form-control"/>
                        <span class="help-inline"> </span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">姓名:</label>

                    <div class="col-sm-4">
                        <form:input path="name" htmlEscape="false" maxlength="50" class="required form-control"/>
                        <span class="help-inline"> </span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">手机:</label>

                    <div class="col-sm-4">
                        <form:input path="phone" htmlEscape="false" maxlength="50" class="required form-control"/>
                        <span class="help-inline"> </span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">登录名:</label>

                    <div class="col-sm-4">
                        <input id="oldLoginName" name="oldLoginName" type="hidden"
                               value="${user.loginName}">
                        <form:input path="loginName" htmlEscape="false" maxlength="50"
                                    class="required userName form-control"/>
                        <span class="help-inline"> </span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">密码:</label>

                    <div class="col-sm-4">
                        <input id="newPassword" name="newPassword" type="password" value=""
                               maxlength="50" minlength="3"
                               class="${empty user.id?'required':''} form-control"/>
                        <c:if test="${empty user.id}"><span class="help-inline"><font
                                color="red">*</font> </span></c:if>
                        <c:if test="${not empty user.id}"><span
                                class="help-inline">若不修改密码，请留空。</span></c:if>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">头像:</label>

                    <div class="col-sm-4">
                    <mechanism:upload id="financeSchoolPic" fdKey="userPic"
                                      name="attMains1" exts=""
                                      btnText="添加"
                                      modelId="${user.id}"
                                      showImg="true" resizeImg="true" resizeWidth="454"
                                      resizeHeight="247"
                                      imgWidth="120" imgHeight="80"
                                      modelName="com.bra.modules.sys.entity.User" multi="false"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">场馆管理:</label>

                    <div class="col-sm-10">
                        <c:forEach items="${venueList}" var="venue" varStatus="status">
                            <label>
                                <c:set var="vchecked" value=""/>
                                <c:forEach items="${userRole.venueList}" var="v">
                                    <c:if test="${venue.id eq v}">
                                        <c:set var="vchecked" value="checked=\"checked\""/>
                                    </c:if>
                                </c:forEach>
                                <input type="checkbox" id="userRoleCheck" ${vchecked}
                                       name="reserveRole.venueList[${status.index}]" class="icheck"
                                       value="${venue.id}"/>${venue.name}</label>
                        </c:forEach>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">角色:</label>

                    <div class="col-sm-10">

                        <c:forEach items="${roleAuthList}" var="roleAuth" varStatus="status">
                            <label><input type="radio" <j:if test="${userRole.userType eq roleAuth.userType}"> checked="checked" </j:if>
                                          name="userType" class="icheck userType" value="${roleAuth.userType}"/>${roleAuth.name}</label>
                        </c:forEach>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">结账密码:</label>

                    <div class="col-sm-4">
                        <form:input path="checkoutPwd" htmlEscape="false" maxlength="50"
                                    class="form-control"/>
                    </div>

                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">备注:</label>

                    <div class="col-sm-6">
                        <form:textarea path="remarks" cssClass="form-control"></form:textarea>
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
<script type="text/javascript">
    $(document).ready(function () {
        var childCheck = false;
        $(".authCheck").on('ifChecked', function () {
            if (childCheck) {
                return;
            }
            var id = $(this).attr("value");
            $(".childAuthCheck[data-parent='" + id + "']").iCheck('check');
        });
        $(".authCheck").on('ifUnchecked', function () {
            var id = $(this).attr("value");
            $(".childAuthCheck[data-parent='" + id + "']").iCheck('uncheck');
        });
        $(".childAuthCheck").on('ifChecked', function (event) {
            var id = $(this).attr("data-parent");
            childCheck = true;
            $(".authCheck[value='" + id + "']").iCheck('check');
            childCheck = false;
        });
    });
</script>
<script type="text/javascript">
    $(document).ready(function () {

        $("#no").focus();
        $("#inputForm").validate({
            rules: {
                loginName: {remote: "${ctx}/reserve/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
            },
            messages: {
                loginName: {remote: "用户登录名已存在"},
                confirmNewPassword: {equalTo: "输入与上面相同的密码"}
            },
            submitHandler: function (form) {
                formLoding('正在提交，请稍等...');
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
</body>
</html>

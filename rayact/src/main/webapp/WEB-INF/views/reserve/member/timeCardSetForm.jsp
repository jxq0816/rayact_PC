<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>次卡管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="timecardSet"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="page-head">
        <h3>次卡添加</h3>
    </div>
    <div class="cl-mcont">
        <div class="block-flat">
            <form:form id="inputForm" modelAttribute="reserveTimecardMemberSet"
                       action="${ctx}/reserve/reserveTimecardMemberSet/save" onsubmit="return checkForm()" method="post"
                       class="form-horizontal" role="form">
            <form:hidden path="id"/>
            <input type="hidden" name="token" value="${token}"/>
            <sys:msg content="${message}"/>
            <div class="form-group">
                <label class="col-sm-3 control-label">名称:</label>
                <div class="col-sm-6 ">
                    <form:input path="name" htmlEscape="false" maxlength="30"
                                class="form-control required"/>
                </div>
                <div class="col-sm-1 ">
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">项目:</label>
                <div class="col-sm-6">
                    <sys:select cssClass="input-large" name="reserveProject.id"
                                items="${reserveProjectList}" cssStyle="width:50%"
                                defaultLabel="全部" defaultValue=""
                                value="${reserveTimecardMemberSet.reserveProject.id}" itemLabel="name" itemValue="id">

                    </sys:select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">起止次数:</label>
                <div class="col-sm-6">
                    <form:input path="startTime" cssstyle="width:20px" htmlEscape="false" maxlength="11"
                                class="input-xlarge "/>&nbsp;至
                    <form:input path="endTime" htmlEscape="false" maxlength="11" class="input-xlarge "/>&nbsp;次
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">增送次数:</label>
                <div class="col-sm-6">
                    <form:input path="giveTime" htmlEscape="false" maxlength="11" class="form-control "/>
                </div>
                <div class="col-sm-1">
                    次
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">周期：</label>
                <div class="col-sm-6">
                    <form:input path="minutesPerTime" htmlEscape="false" maxlength="11" class="form-control "/>
                </div>
                <div class="col-sm-1">
                    分钟/次
                </div>
            </div>


            <div class="form-group">
                <label class="col-sm-3 control-label">备注:</label>
                <div class="col-sm-6">
                    <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255"
                                   class="form-control"/>
                </div>
            </div>

        </div>
        <div class="form-actions">
            <input
                    id="btnSubmit"
                    class="btn btn-primary"
                    type="submit"
                    value="保 存"/>&nbsp;
            <input id="btnCancel" class="btn" type="button" value="返 回"
                   onclick="history.go(-1)"/>
        </div>
        </form:form>
    </div>
</div>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/validate.js"></script>
</body>
</html>
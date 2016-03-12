<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>储值卡管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="storedcardSet"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="page-head">
        <h3>储值卡编辑</h3>
    </div>
    <div class="cl-mcont">
        <div class="block-flat">
            <form:form id="inputForm" modelAttribute="reserveStoredcardMemberSet"
                       action="${ctx}/reserve/storedCardMemberSet/save" method="post"
                       class="form-horizontal">
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
                                items="${reserveProjectList}"
                                defaultLabel="全部" defaultValue=""
                                value="${reserveProject}" itemLabel="name" itemValue="id">

                    </sys:select>
                </div>
            </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">场地:</label>
                    <div class="col-sm-6">
                        <sys:select cssClass="input-large" name="reserveField.id"
                                    items="${reserveFieldList}"
                                    defaultLabel="请选择场地" defaultValue=""
                                    value="${reserveField}" itemLabel="name" itemValue="id">

                        </sys:select>
                    </div>
                </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">起止金额:</label>
                <div class="col-sm-6">
                    <form:input path="startPrice" htmlEscape="false"
                                class="input-xlarge  number "/>&nbsp;至
                    <form:input path="endPrice" htmlEscape="false"
                                class="input-xlarge  number "/>&nbsp;元
                </div>

            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">赠送金额:</label>
                <div class="col-sm-6 ">
                    <form:input path="givePrice" htmlEscape="false"
                                class="form-control number"/>
                </div>
                <div class="col-sm-1 ">
                    元
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">备注:</label>
                <div class="col-sm-6">
                    <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255"
                                   class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">折扣比率:</label>
                <div class="col-sm-6">
                    <form:input path="discountRate" htmlEscape="false"
                                class="form-control  number"/>
                </div>
                <div class="col-sm-1 ">
                    %
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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>时令管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="timeInterval"></jsp:param>
</jsp:include>

<div class="cl-mcont" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>时令添加</h3>
                </div>
                <div class="content">
                    <div class="tab-container">
                        <div class="tab-content">
                            <div class="form-horizontal group-border-dashed">

                                <form:form id="inputForm" modelAttribute="reserveTimeInterval"
                                           action="${ctx}/reserve/reserveTimeInterval/save"
                                           method="post" class="form-horizontal">
                                    <form:hidden path="id"/>
                                    <input type="hidden" name="token" value="${token}"/>
                                    <sys:message content="${message}"/>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">名称：</label>
                                        <div class="col-sm-6">
                                            <form:input path="name" htmlEscape="false" maxlength="30"
                                                        class="required form-control "/>
                                        </div>
                                        <div class="col-sm-1">
                                            <span class="help-inline"> <font color="red">*</font></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">开始日期：</label>
                                        <div class="col-sm-6">
                                            <form:input path="startDate" htmlEscape="false"
                                                        class="required form-control "/>
                                        </div>
                                        <div class="col-sm-1">
                                            <span class="help-inline"> <font color="red">*</font></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">结束日期：</label>
                                        <div class="col-sm-6">
                                            <form:input path="endDate" htmlEscape="false"
                                                        class="required form-control "/>
                                        </div>
                                        <div class="col-sm-1">
                                            <span class="help-inline"> <font color="red">*</font></span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">备注：</label>
                                        <div class="col-sm-6">
                                            <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255"
                                                           class="form-control"/>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
                                        <input id="btnCancel" class="btn" type="button" value="返 回"
                                               onclick="history.go(-1)"/>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/validate.js"></script>
</body>
</html>
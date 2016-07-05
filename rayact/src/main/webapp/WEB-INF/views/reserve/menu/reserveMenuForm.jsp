<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>菜单管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="roleAuth"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-lg-12">
            <div class="block-flat">
                <div class="header">
                    <h3>角色权限配置</h3>
                </div>
                <div class="content">
                    <div class="tab-container">
                        <div class="form-horizontal group-border-dashed">
                            <form:form id="inputForm" modelAttribute="reserveMenu"
                                       action="${ctx}/reserve/reserveMenu/save" method="post" class="form-horizontal">
                                <form:hidden path="id"/>
                                <sys:message content="${message}"/>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">父级编号：</label>
                                    <div class="col-sm-6">
                                        <sys:select cssClass="input-large"
                                                    name="parent.id"
                                                    cssStyle="width:100%"
                                                    items="${menuList}"
                                                    value="${reserveMenu.parent.id}"
                                                    itemLabel="name"
                                                    itemValue="id"
                                                    defaultValue=""
                                                    defaultLabel="请选择父菜单"
                                        ></sys:select>
                                    </div>
                                </div>
                                <%--<div class="form-group">
                                    <label class="control-label">：</label>
                                    <div class="controls">
                                        <span class="help-inline"><font color="red">*</font> </span>
                                    </div>
                                </div>--%>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">所有父级编号：</label>
                                    <div class="col-sm-6">
                                        <form:input path="parentIds" htmlEscape="false" maxlength="2000"
                                                    class="form-control required"/>
                                        <span class="help-inline"><font color="red">*</font> </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">名称：</label>
                                    <div class="col-sm-6">
                                        <form:input path="name" htmlEscape="false" maxlength="100"
                                                    class="form-control required"/>
                                        <span class="help-inline"><font color="red">*</font> </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">排序：</label>
                                    <div class="col-sm-6">
                                        <form:input path="sort" htmlEscape="false" class="form-control required"/>
                                        <span class="help-inline"><font color="red">*</font> </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">链接：</label>
                                    <div class="col-sm-6">
                                        <form:input path="href" htmlEscape="false" maxlength="2000"
                                                    class="form-control "/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">目标：</label>
                                    <div class="col-sm-6">
                                        <form:input path="target" htmlEscape="false" maxlength="20"
                                                    class="form-control "/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">图标：</label>
                                    <div class="col-sm-6">
                                        <form:input path="icon" htmlEscape="false" maxlength="100"
                                                    class="form-control "/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">是否在菜单中显示：</label>
                                    <div class="col-sm-6">
                                        <form:input path="isShow" htmlEscape="false" maxlength="1"
                                                    class="form-control required"/>
                                        <span class="help-inline"><font color="red">*</font> </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">权限标识：</label>
                                    <div class="col-sm-6">
                                        <form:input path="permission" htmlEscape="false" maxlength="200"
                                                    class="form-control "/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">备注信息：</label>
                                    <div class="col-sm-6">
                                        <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255"
                                                       class="form-control"/>
                                    </div>
                                </div>
                                <div class="form-actions">
                                    <input id="btnSubmit"
                                           class="btn btn-primary"
                                           type="submit"
                                           value="保 存"/>
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
</body>
</html>
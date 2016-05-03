<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>项目管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="project"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>项目编辑</h3>
                </div>
                <div class="content">
                    <td class="tab-container">
                        <tr class="form-horizontal group-border-dashed">
                            <form:form id="inputForm" modelAttribute="reserveProject"
                                       action="${ctx}/reserve/reserveProject/save" method="post"
                                       class="form-horizontal">
                                <form:hidden id="id" path="id"/>
                            <input type="hidden" name="token" value="${token}"/>
                                <sys:message content="${message}"/>
                            <div class="row">
                                <div class="form-group">
                                    <label class="control-label col-lg-3" for="name">名称：</label>
                                    <div class="col-lg-3">
                                        <form:input path="name" id="name" htmlEscape="false" maxlength="30"
                                                    class="form-control"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group">
                                    <label class="control-label col-lg-3" for="available">是否启用：</label>
                                    <div class="col-lg-3">
                                        <sys:radio name="available" value="${reserveVenue.available}"
                                                   items="${fns:getDictList('yes_no')}" itemLabel="label"
                                                   itemValue="value" cssClass="icheck">
                                        </sys:radio>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group">
                                    <label class="control-label col-lg-3" for="remarks">备注：</label>
                                    <div class="col-lg-3">
                                        <form:textarea path="remarks" id="remarks" htmlEscape="false" rows="4" maxlength="255"
                                                       class="input-xxlarge "/>
                                    </div>
                                </div>
                            </div>
                            <div class="form-actions">
                                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
                                <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
                            </div>
                            </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
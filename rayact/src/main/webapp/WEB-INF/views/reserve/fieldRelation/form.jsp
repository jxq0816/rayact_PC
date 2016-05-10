<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地关系编辑</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="fieldRelation"></jsp:param>
</jsp:include>

<div class="cl-mcont" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>场地关系编辑</h3>
                </div>
                <div class="content">
                    <div class="tab-container">
                        <div class="tab-content">
                            <div class="form-horizontal group-border-dashed">

                                <form:form id="inputForm" modelAttribute="reserveFieldRelation"
                                           action="${ctx}/reserve/reserveFieldRelation/save"
                                           method="post" class="form-horizontal">
                                    <form:hidden id="id" path="id"/>
                                    <input type="hidden" name="token" value="${token}"/>
                                    <sys:message content="${message}"/>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">全场：</label>
                                        <div class="col-sm-6">
                                            <sys:select cssClass="input-xlarge" id="parentFieldId"
                                                        name="parentField.id"
                                                        items="${fields}"
                                                        value="${reserveFieldRelation.parentField.id}"
                                                        itemLabel="name"
                                                        itemValue="id"
                                                        defaultValue=""
                                                        defaultLabel="请选择全场"
                                            ></sys:select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="halfField">半场：</label>
                                        <div class="col-sm-6">
                                            <j:if test="${reserveFieldRelation.id!=''}">
                                                <p id="halfField" class="form-control-static">${reserveFieldRelation.childField.name}</p>
                                                <input type="hidden" path="childField.id"
                                                       value="${reserveFieldRelation.childField.id}"/>
                                            </j:if>
                                            <j:if test="${reserveFieldRelation.id==null}">
                                                <sys:select cssClass="input-xlarge" name="childField.id"
                                                            id="childFieldId"
                                                            items="${fields}"
                                                            value="${reserveFieldRelation.childField.id}"
                                                            itemLabel="name"
                                                            itemValue="id"
                                                            defaultValue=""
                                                            defaultLabel="请选择半场"
                                                ></sys:select>
                                            </j:if>
                                        </div>
                                    </div>

                                    <div c lass="form-actions">
                                        <input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
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
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/reserve_fieldRelation_form.js"></script>
</body>
</html>
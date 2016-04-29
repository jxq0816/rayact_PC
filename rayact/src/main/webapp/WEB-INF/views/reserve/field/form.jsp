<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地管理</title>
    <meta name="decorator" content="main"/>
    <%@include file="/WEB-INF/views/include/upload.jsp" %>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="field"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12" style="padding-right: 0px">
            <div class="block-flat">
                <div class="header">
                    <h3>场地管理</h3>
                </div>
                <div class="content">
                    <form:form id="inputForm" modelAttribute="reserveField" action="${ctx}/reserve/reserveField/save"
                               method="post"
                               class="form-horizontal">
                    <form:hidden path="id"/>
                    <input type="hidden" name="token" value="${token}"/>


                    <div class="tab-container">
                        <ul class="nav nav-tabs" id="myTab">
                            <li class="active"><a href="#home" data-toggle="tab">基本信息</a></li>
                        </ul>

                        <div class="tab-content">
                            <div class="tab-pane active" id="home">
                                <div class="form-horizontal group-border-dashed">
                                    <div class="form-group">
                                        <label for="name" class="col-sm-3 control-label">场地名称</label>

                                        <div class="col-sm-6">
                                            <form:input path="name" htmlEscape="false" maxlength="30"
                                                        class="form-control"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">所属场馆</label>
                                        <div class="col-sm-6">
                                            <sys:select cssClass="input-medium" name="reserveVenue.id"
                                                        value="${reserveField.reserveVenue.id}"
                                                        items="${venues}" itemLabel="name" itemValue="id"
                                                        defaultLabel="----请选择-----"
                                                        defaultValue=""></sys:select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">是否启用</label>
                                        <div class="col-sm-6">
                                            <form:radiobuttons path="available" items="${fns:getDictList('yes_no')}"
                                                               itemLabel="label"
                                                               itemValue="value"
                                                               htmlEscape="false" class="icheck"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">所属项目</label>
                                        <div class="col-sm-6">
                                            <sys:select cssClass="input-medium" name="reserveProject.id"
                                                        value="${reserveField.reserveProject.id}"
                                                        items="${projects}" itemLabel="name" itemValue="id"
                                                        defaultLabel="请选择"
                                                        defaultValue=""></sys:select>
                                        </div>
                                    </div>
                                    <j:if test="${reserveField.reserveParentField==''}">
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">父场地</label>
                                            <div class="col-sm-6">
                                                    ${reserveField.reserveParentField.name}
                                            </div>
                                        </div>
                                    </j:if>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">是否分时令</label>
                                        <div class="col-sm-6">
                                            <form:radiobuttons path="isTimeInterval"
                                                               items="${fns:getDictList('yes_no')}"
                                                               itemLabel="label"
                                                               itemValue="value"
                                                               htmlEscape="false" class="icheck required"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">备注</label>
                                        <div class="col-sm-6">
                                            <form:textarea path="remarks" htmlEscape="false" rows="4"
                                                           maxlength="255" class="form-control"/>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">场地图片</label>
                                        <div class="col-sm-3">
                                            <mechanism:upload id="financeSchoolPic" fdKey="fieldPic"
                                                              name="attMains1" exts=""
                                                              btnText="添加"
                                                              modelId="${reserveField.id}"
                                                              showImg="true" resizeImg="true" resizeWidth="454"
                                                              resizeHeight="247"
                                                              imgWidth="120" imgHeight="80"
                                                              modelName="reserveField" multi="true"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <input id="btnSubmit"
                           class="btn btn-primary"
                           type="submit"
                           value="保 存"/>&nbsp;
                    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
                </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/validate.js"></script>
</body>
</html>

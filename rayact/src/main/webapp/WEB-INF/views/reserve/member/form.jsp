<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>会员管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="member"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>会员基本信息编辑</h3>
                </div>
                <div class="content">
                    <div class="tab-container">
                        <div class="tab-content">
                            <div class="form-horizontal group-border-dashed">

                                <form:form id="inputForm" modelAttribute="reserveMember"
                                           action="${ctx}/reserve/reserveMember/save"
                                           method="post" class="form-horizontal">
                                    <form:hidden path="id"/>
                                    <input type="hidden" name="token" value="${token}"/>
                                    <sys:message content="${message}"/>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">姓名：</label>
                                        <div class="col-sm-6">
                                            <form:input path="name" htmlEscape="false" maxlength="30"
                                                        class="required form-control "/>
                                            <span class="help-inline"> <font color="red">*</font></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">手机号：</label>
                                        <div class="col-sm-6">
                                            <form:input path="mobile" htmlEscape="false" maxlength="20"
                                                        class="form-control "/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">密码：</label>
                                        <div class="col-sm-6">
                                            <form:input path="password" type="password" htmlEscape="false"
                                                        maxlength="16"
                                                        class="form-control "/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">性别：</label>
                                        <div class="col-sm-6">
                                            <form:radiobuttons path="sex" items="${fns:getDictList('sex')}"
                                                               itemLabel="label"
                                                               itemValue="value" htmlEscape="false" class=""/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">卡号：</label>
                                        <div class="col-sm-6">
                                            <form:input path="cartno" htmlEscape="false" maxlength="20"
                                                        class="form-control required"/>
                                            <span class="help-inline"><font color="red">*</font> </span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">卡号类型：</label>
                                        <div class="col-sm-6">
                                            <form:radiobuttons path="cartType" items="${fns:getDictList('cart_type')}"
                                                               itemLabel="label"
                                                               itemValue="value" htmlEscape="false" class="icheck"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">场馆：</label>
                                        <div class="col-sm-6">
                                            <sys:select cssClass="input-large"
                                                        name="reserveVenue.id"
                                                        cssStyle="width:100%"
                                                        items="${venueList}"
                                                        value="${reserveMember.reserveVenue.id}"
                                                        itemLabel="name"
                                                        itemValue="id"
                                                        defaultValue=""
                                                        defaultLabel="请选择场馆"
                                            ></sys:select>
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
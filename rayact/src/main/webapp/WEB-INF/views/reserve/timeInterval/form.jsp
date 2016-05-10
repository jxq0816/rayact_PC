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
                                        <label class="col-sm-3 control-label">开始月份：</label>
                                        <div class="col-sm-6">
                                            <select class="input-large" style="width: 100%" name="startMonth">
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="6">6</option>
                                                <option value="7">7</option>
                                                <option value="8">8</option>
                                                <option value="9">9</option>
                                                <option value="10">10</option>
                                                <option value="11">11</option>
                                                <option value="12">12</option>
                                            </select>
                                        </div>
                                        <div class="col-sm-1">
                                            <span class="help-inline"> <font color="red">*</font></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">开始日期：</label>
                                        <div class="col-sm-6">
                                                <select class="input-large" style="width: 100%" name="startDate">
                                                    <option value="1">1</option>
                                                    <option value="2">2</option>
                                                    <option value="3">3</option>
                                                    <option value="4">4</option>
                                                    <option value="5">5</option>
                                                    <option value="6">6</option>
                                                    <option value="7">7</option>
                                                    <option value="8">8</option>
                                                    <option value="9">9</option>
                                                    <option value="10">10</option>
                                                    <option value="11">11</option>
                                                    <option value="12">12</option>
                                                    <option value="13">13</option>
                                                    <option value="14">14</option>
                                                    <option value="15">15</option>
                                                    <option value="16">16</option>
                                                    <option value="17">17</option>
                                                    <option value="18">18</option>
                                                    <option value="19">19</option>
                                                    <option value="20">20</option>
                                                    <option value="21">21</option>
                                                    <option value="22">22</option>
                                                    <option value="23">23</option>
                                                    <option value="24">24</option>
                                                    <option value="25">25</option>
                                                    <option value="26">26</option>
                                                    <option value="27">27</option>
                                                    <option value="28">28</option>
                                                    <option value="29">29</option>
                                                    <option value="30">30</option>
                                                    <option value="31">31</option>
                                                </select>
                                        </div>
                                        <div class="col-sm-1">
                                            <span class="help-inline"> <font color="red">*</font></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">结束月份：</label>
                                        <div class="col-sm-6">
                                            <select class="input-large" style="width: 100%" name="endMonth">
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="6">6</option>
                                                <option value="7">7</option>
                                                <option value="8">8</option>
                                                <option value="9">9</option>
                                                <option value="10">10</option>
                                                <option value="11">11</option>
                                                <option value="12">12</option>
                                            </select>
                                        </div>
                                        <div class="col-sm-1">
                                            <span class="help-inline"> <font color="red">*</font></span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">结束日期：</label>
                                        <div class="col-sm-6">
                                            <select class="input-large" style="width: 100%" name="endDate">
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                                <option value="6">6</option>
                                                <option value="7">7</option>
                                                <option value="8">8</option>
                                                <option value="9">9</option>
                                                <option value="10">10</option>
                                                <option value="11">11</option>
                                                <option value="12">12</option>
                                                <option value="13">13</option>
                                                <option value="14">14</option>
                                                <option value="15">15</option>
                                                <option value="16">16</option>
                                                <option value="17">17</option>
                                                <option value="18">18</option>
                                                <option value="19">19</option>
                                                <option value="20">20</option>
                                                <option value="21">21</option>
                                                <option value="22">22</option>
                                                <option value="23">23</option>
                                                <option value="24">24</option>
                                                <option value="25">25</option>
                                                <option value="26">26</option>
                                                <option value="27">27</option>
                                                <option value="28">28</option>
                                                <option value="29">29</option>
                                                <option value="30">30</option>
                                                <option value="31">31</option>
                                            </select>
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
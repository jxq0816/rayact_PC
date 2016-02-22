<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="commodity"></jsp:param>
</jsp:include>
<div class="cl-mcont" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>商品编辑</h3>
                </div>
                <div class="content">
                    <div class="tab-container">
                        <div class="form-horizontal group-border-dashed">
                            <form:form id="inputForm" modelAttribute="commodity" action="${ctx}/reserve/commodity/save"
                                       method="post"
                                       class="form-horizontal" onsubmit="return checkCommodityId()">
                                <form:hidden path="id"/>
                                <input type="hidden" name="token" value="${token}"/>
                                <sys:message content="${message}"/>

                                <table id="contentTable" class="table table-bordered">
                                    <tr>
                                        <td>商品编号：</td>
                                        <td>
                                            <form:input id="commodityId" path="commodityId" htmlEscape="false"
                                                        maxlength="19"
                                                        class="form-control required" onblur="checkCommodityId()"/>
                                            <span class="help-inline"><font color="red">*</font> </span>
                                        </td>
                                        <td>商品名称：</td>
                                        <td>
                                            <form:input path="name" htmlEscape="false" maxlength="30"
                                                        class="form-control required"/>
                                            <span class="help-inline"><font color="red">*</font> </span>
                                        </td>
                                    </tr>


                                    <tr>
                                        <td>商品类型：</td>
                                        <td>
                                            <sys:select cssClass="input-xlarge" name="commodityType.id"
                                                        cssStyle="width:150px"
                                                        items="${commodityTypeList}"
                                                        value="${commodityType}"
                                                        itemLabel="name"
                                                        itemValue="id"
                                                        defaultLabel="请选择商品类型"
                                            ></sys:select>
                                        </td>
                                        <td>状态：</td>
                                        <td>
                                            <form:select path="shelvesStatus" class="input-xlarge">
                                                <form:option value="0" label="下架"/>
                                                <form:option value="1" label="上架"/>
                                            </form:select>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>价格：</td>
                                        <td>
                                            <form:input path="price" htmlEscape="false"
                                                        class="form-control required"/>&nbsp;元
                                            <span class="help-inline"><font color="red">*</font> </span>
                                        </td>

                                        <td>库存数量：</td>
                                        <td>
                                            <form:input path="repertoryNum" htmlEscape="false"
                                                        class="form-control required"/>
                                            <span class="help-inline"><font color="red">*</font> </span>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>单位：</td>
                                        <td>
                                            <form:input path="unit" htmlEscape="false"
                                                        class="form-control"/>
                                        </td>
                                        <td>快速搜索：</td>
                                        <td>
                                            <form:input path="quickSearch" htmlEscape="false"
                                                        class="form-control "/>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>备注：</td>
                                        <td>
                                            <form:textarea path="remarks" htmlEscape="false" rows="4"
                                                           maxlength="255"
                                                           class="input-xxlarge "/>
                                        </td>
                                        <td>所属场馆：</td>
                                        <td>
                                            <sys:select cssClass="input-xlarge" name="reserveVenue.id"
                                                        cssStyle="width:150px"
                                                        items="${venueList}"
                                                        value="${venue}"
                                                        itemLabel="name"
                                                        itemValue="id"
                                                        defaultLabel="请选择场馆"
                                            ></sys:select>
                                        </td>
                                    </tr>
                                </table>

                                <div>
                                    <input id="btnSubmit"
                                           class="btn btn-primary"
                                           type="submit"
                                           value="保 存"/>&nbsp;
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
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/validate.js"></script>
</body>
</html>
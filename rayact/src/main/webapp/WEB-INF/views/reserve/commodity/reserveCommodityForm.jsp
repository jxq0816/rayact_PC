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
                                            <div class="row">
                                                <div class="col-lg-9">
                                                    <form:input id="commodityId" path="commodityId" readonly="true"
                                                                maxlength="19"
                                                                class="form-control" placeholder="商品编号由系统自动生成，新增商品时无需输入"
                                                    />
                                                </div>
                                            </div>
                                        </td>
                                        <td>商品名称：</td>
                                        <td>
                                            <div class="row">
                                                <div class="col-lg-9">
                                                    <form:input path="name" htmlEscape="false" maxlength="30"
                                                                class="form-control required"/>
                                                </div>
                                                <div class="col-lg-3">
                                                    <span class="help-inline pull-right"><font color="red">*</font> </span>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>


                                    <tr>
                                        <td>商品类型：</td>
                                        <td>
                                            <sys:select cssClass="input-xlarge required" name="commodityType.id"
                                                        cssStyle="width: 74%"
                                                        items="${commodityTypeList}"
                                                        value="${commodity.commodityType.id}"
                                                        itemLabel="name"
                                                        itemValue="id"
                                                        defaultLabel="请选择商品类型"
                                                        defaultValue=""
                                            ></sys:select>
                                            <span class="help-inline pull-right"><font color="red">*</font> </span>
                                        </td>
                                        <td>状态：</td>
                                        <td>
                                            <form:select path="shelvesStatus" class="input-xlarge" cssStyle="width:73%">
                                                <form:option value="1" label="上架"/>
                                                <form:option value="0" label="下架"/>
                                            </form:select>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>价格：</td>
                                        <td>
                                            <div class="row">
                                                <div class="col-lg-9">
                                                    <form:input path="price" htmlEscape="false" for="yuan"
                                                                class="form-control required number"/>
                                                </div>
                                                <label id="yuan" class="control-label col-lg-1">元</label>
                                                <div class="col-lg-2">
                                                    <span class="help-inline pull-right"><font color="red">*</font> </span>
                                                </div>
                                            </div>
                                        </td>

                                        <td>库存数量：</td>
                                        <td>
                                            <div class="row">
                                                <div class="col-lg-9">
                                                    <form:input path="repertoryNum" htmlEscape="false"
                                                                class="form-control required number"/>
                                                </div>
                                                <div class="col-lg-3">
                                                    <span class="help-inline pull-right"><font color="red">*</font> </span>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>规格：</td>
                                        <td>
                                            <div class="row">
                                                <div class="col-lg-9">
                                                    <form:input path="unit" htmlEscape="false" id="unit"
                                                                class="form-control required number"/>
                                                </div>
                                                <div class="col-lg-3">
                                                    <span class="help-inline pull-right"><font color="red">*</font> </span>
                                                </div>
                                            </div>
                                        </td>
                                        <td>快速搜索：</td>
                                        <td>
                                            <form:input path="quickSearch" htmlEscape="false" cssStyle="width: 73%"
                                                        class="form-control "/>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>备注：</td>
                                        <td>
                                            <form:textarea path="remarks" htmlEscape="false" rows="4"
                                                           cssStyle="width: 100%"
                                                           maxlength="255"
                                                           class="input-xxlarge "/>
                                        </td>
                                        <td>所属场馆：</td>
                                        <td>
                                            <sys:select cssClass="input-xlarge required" name="reserveVenue.id"
                                                        cssStyle="width:73%"
                                                        items="${venueList}"
                                                        value="${commodity.reserveVenue.id}"
                                                        itemLabel="name"
                                                        itemValue="id"
                                                        defaultLabel="请选择场馆"
                                            ></sys:select>
                                            <span class="help-inline pull-right"><font color="red">*</font> </span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>单位：</td>
                                        <td>
                                            <form:input path="unitName" htmlEscape="false" class="form-control"/>
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
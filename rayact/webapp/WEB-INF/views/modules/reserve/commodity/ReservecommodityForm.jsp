<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/commodity/commodity/">商品列表</a></li>
    <li class="active"><a href="${ctx}/commodity/commodity/form?id=${commodity.id}">商品<shiro:hasPermission
            name="commodity:commodity:edit">${not empty commodity.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="commodity:commodity:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="commodity" action="${ctx}/commodity/commodity/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>

    <tr class="tab-content">
        <td class="tab-pane active" id="home">
            <table id="contentTable" class="table table-bordered">
                <tr>
                    <td>商品类型：</td>
                    <td>
                        <sys:select cssClass="input-xlarge" name="commodityType.id" items="${commodityTypeList}"
                                    value="${commodityType}" itemLabel="name" itemValue="id"></sys:select>
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
                    <td>商品编号：</td>
                    <td>
                        <form:input path="commodityId" htmlEscape="false" maxlength="19" class="input-xlarge required"/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </td>
                    <td>商品名称：</td>
                    <td>
                        <form:input path="name" htmlEscape="false" maxlength="30" class="input-xlarge required"/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </td>
                </tr>


                <tr>
                    <td>价格：</td>
                    <td>
                        <form:input path="price" htmlEscape="false" class="input-xlarge required"/>&nbsp;元
                        <span class="help-inline"><font color="red">*</font> </span>
                    </td>

                    <td>库存数量：</td>
                    <td>
                        <form:input path="repertoryNum" htmlEscape="false" class="input-xlarge required"/>
                        <span class="help-inline"><font color="red">*</font> </span>
                    </td>
                </tr>

                <tr>
                    <td>描述：</td>
                    <td colspan="3">
                        <form:textarea path="description" htmlEscape="false" rows="4" maxlength="255"
                                       class="input-xxlarge "/>
                    </td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td colspan="3">
                        <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255"
                                       class="input-xxlarge "/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <div class="form-actions">
        <div>
            <shiro:hasPermission name="commodity:commodity:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                        type="submit"
                                                                        value="保 存"/>&nbsp;</shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>
    </div>
</form:form>
</body>
</html>
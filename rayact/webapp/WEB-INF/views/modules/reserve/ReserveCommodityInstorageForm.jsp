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
    <li class="active"><a href="${ctx}/commodity/commodity/inStorageUrl?id=${commodity.id}">商品入库</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="commodity" action="${ctx}/reserve/commodity/inStorage" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <tr class="tab-content">
        <td class="tab-pane active" id="home">
            <table id="contentTable" class="table table-bordered">
                <tr>
                    <td>商品类型：</td>
                    <td>
                            ${commodity.commodityType.name}
                    </td>

                    <td>状态：</td>
                    <td>
                            <c:choose>
                                <c:when test="${commodity.shelvesStatus== '0'}">
                                    下架
                                </c:when>
                                <c:otherwise>
                                    上架
                                </c:otherwise>
                            </c:choose>
                    </td>
                </tr>

                <tr>
                    <td>商品编号：</td>
                    <td>
                            ${commodity.commodityId}

                    </td>
                    <td>商品名称：</td>
                    <td>
                            ${commodity.name}
                    </td>
                </tr>


                <tr>
                    <td>价格：</td>
                    <td>
                            ${commodity.price}
                    </td>

                    <td>库存数量：</td>
                    <td>
                            ${commodity.repertoryNum}
                    </td>
                </tr>

                <tr>
                    <td>描述：</td>
                    <td colspan="3">
                            ${commodity.description}
                    </td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td colspan="3">
                            ${commodity.remarks}
                    </td>
                </tr>
            </table>
            <div class="control-group">
                <label class="control-label">入库数量：</label>
                <div class="controls">
                    <input name="inRepertoryNum" htmlEscape="false" class="input-xlarge required"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </td>
    </tr>

    <div class="form-actions">
        <shiro:hasPermission name="reserve:commodity:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                    type="submit"
                                                                    value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
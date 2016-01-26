<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品类别管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="commodityType"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="page-head">
        <h3>商品类别编辑</h3>
    </div>

    <div class="cl-mcont">
        <div class="block-flat">
            <form:form id="inputForm" modelAttribute="commodityType"
                       action="${ctx}/reserve/commodityType/save" method="post"
                       class="form-horizontal">
                <form:hidden path="id"/>
                <input type="hidden" name="token" value="${token}"/>
                <sys:message content="${message}"/>
                <table id="contentTable" class="table table-bordered">
                    <tr>
                        <td>名称：</td>
                        <td>
                            <form:input path="name" htmlEscape="false" maxlength="30"
                                        class="required form-control"/>
                        </td>
                        <td><span class="help-inline"> <font color="red">*</font></span></td>
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
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/validate.js"></script>
</body>
</html>
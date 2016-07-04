<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="roleAuth"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-lg-12">
            <div class="block-flat">
                <div class="header">
                    <h3>角色权限配置</h3>
                </div>
                <div class="content">
                    <div class="tab-container">
                        <div class="form-horizontal group-border-dashed">
                            <form:form id="inputForm" modelAttribute="reserveRoleAuth"
                                       action="${ctx}/reserve/reserveRoleAuth/save"
                                       method="post"
                                       class="form-horizontal">
                                <form:hidden path="id"/>
                                <input type="hidden" name="token" value="${token}"/>
                                <sys:message content="${message}"/>

                                <div class="row">
                                    <label class="col-lg-1  control-label">角色：</label>
                                    <div class="col-lg-5">
                                        <form:input id="name" path="name"
                                                    maxlength="19"
                                                    class="form-control"
                                        />
                                    </div>
                                    <label class="col-lg-1  control-label">编号：</label>
                                    <div class="col-lg-5">
                                        <form:input id="userType" path="userType"
                                                    maxlength="2"
                                                    class="form-control"
                                        />
                                    </div>
                                </div>
                                <%--<td>对应用户权限(json字符串)：</td>
                                <td>
                                    <div class="row">
                                        <div class="col-lg-9">
                                            <form:input path="authority" htmlEscape="false" maxlength="30"
                                                        class="form-control required"/>
                                        </div>
                                        <div class="col-lg-3">
                                            <span class="help-inline pull-right"><font color="red">*</font> </span>
                                        </div>
                                    </div>
                                </td>--%>
                                <div class="row">
                                    <div class="form-group">
                                        <label class="col-lg-1 control-label">权限：</label>

                                        <div class="col-sm-10">

                                            <c:forEach items="${authList}" var="auth" varStatus="astatus">
                                                <%--遍历所有权限--%>
                                                <div class="row">
                                                    <div class="col-sm-6 col-md-8 col-lg-10">
                                                        <div class="row" style="margin-top:20px">
                                                                <%--权限组--%>
                                                            <div class="header">
                                                                <c:set value="" var="checked"></c:set>

                                                                <c:forEach items="${reserveRoleAuth.authorityList}" var="ur">
                                                                    <%--遍历用户的已有权限--%>
                                                                    <c:if test="${ur.code eq auth.code}">
                                                                        <c:set value="checked='checked'"
                                                                               var="checked"></c:set>
                                                                    </c:if>
                                                                    <%--遍历用户的已有权限 end--%>
                                                                </c:forEach>
                                                                <label>
                                                                    <input type="checkbox" ${checked}
                                                                           name="authorityList[${astatus.index}].code"
                                                                           class="icheck authCheck"
                                                                           value="${auth.code}"/>
                                                                        ${auth.name}
                                                                </label>
                                                            </div>
                                                                <%--权限组结束--%>
                                                            <div class="row">
                                                                <c:forEach items="${auth.authorityList}" var="a"
                                                                           varStatus="s">
                                                                    <%-- 权限组的子权限--%>
                                                                    <div class="radio col-lg-4">
                                                                        <c:set value="" var="childchecked"></c:set>
                                                                        <c:forEach items="${reserveRoleAuth.authorityList}"
                                                                                   var="ur">
                                                                            <%--遍历用户的已有权限--%>
                                                                            <c:if test="${ur.code eq auth.code}">
                                                                                <c:forEach items="${ur.authorityList}"
                                                                                           var="child">
                                                                                    <c:if test="${a.code eq child.code}">
                                                                                        <c:set value="checked='checked'"
                                                                                               var="childchecked"></c:set>
                                                                                    </c:if>
                                                                                </c:forEach>
                                                                            </c:if>
                                                                            <%--遍历用户的已有权限 end--%>
                                                                        </c:forEach>

                                                                        <label> <input data-parent="${auth.code}"
                                                                                       type="checkbox" ${childchecked}
                                                                                       value="${a.code}"
                                                                                       name="authorityList[${astatus.index}].authorityList[${s.index}].code"
                                                                                       class="icheck childAuthCheck"> ${a.name}
                                                                            <input
                                                                                    type="hidden"
                                                                                    name="authorityList[${astatus.index}].authorityList[${s.index}].code"
                                                                            >
                                                                        </label>
                                                                    </div>
                                                                    <%-- 子权限 end--%>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <%--遍历所有权限 end--%>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>

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
<script type="text/javascript">
    $(document).ready(function () {
        var childCheck = false;
        $(".authCheck").on('ifChecked', function () {
            if (childCheck) {
                return;
            }
            var id = $(this).attr("value");
            $(".childAuthCheck[data-parent='" + id + "']").iCheck('check');
        });
        $(".authCheck").on('ifUnchecked', function () {
            var id = $(this).attr("value");
            $(".childAuthCheck[data-parent='" + id + "']").iCheck('uncheck');
        });
        $(".childAuthCheck").on('ifChecked', function (event) {
            var id = $(this).attr("data-parent");
            childCheck = true;
            $(".authCheck[value='" + id + "']").iCheck('check');
            childCheck = false;
        });
    });
</script>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/validate.js"></script>
</body>
</html>
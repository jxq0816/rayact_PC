<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>菜单管理</title>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap/2.3.1/custom-theme/jquery-ui-1.10.3.custom.css" type="text/css" rel="stylesheet" />
    <script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
    <!--[if lte IE 6]><link href="${ctxStatic}/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet" />
    <script src="${ctxStatic}/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
    <link href="${ctxStatic}/jquery-select2/3.4/select2.min.css" rel="stylesheet" />
    <script src="${ctxStatic}/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
    <script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
    <script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/common/jeesite.js" type="text/javascript"></script>
    <script src="${ctxStatic}/bootstrap/2.3.1/custom-theme/assets/js/vendor/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
    <script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>
    <meta name="decorator" content="main"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
    <script type="text/javascript">//<!-- 无框架时，左上角显示菜单图标按钮。
    if(!(self.frameElement && self.frameElement.tagName=="IFRAME")){
        $("body").prepend("<i id=\"btnMenu\" class=\"icon-th-list\" style=\"cursor:pointer;float:right;margin:10px;\"></i><div id=\"menuContent\"></div>");
        $("#btnMenu").click(function(){
            top.$.jBox('get:${ctx}/sys/menu/treeselect;JSESSIONID=<shiro:principal property="sessionid"/>', {title:'选择菜单', buttons:{'关闭':true}, width:300, height: 350, top:10});
            //if ($("#menuContent").html()==""){$.get("${ctx}/sys/menu/treeselect", function(data){$("#menuContent").html(data);});}else{$("#menuContent").toggle(100);}
        });
    }//-->
    </script>
</head>
<body>

<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="menu"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-lg-12">
            <div class="block-flat">
                <div class="header">
                    <h3>菜单管理</h3>
                </div>
                <div class="content">
                    <div class="tab-container">
                        <div class="form-horizontal group-border-dashed">
                            <form:form id="inputForm" modelAttribute="reserveMenu"
                                       action="${ctx}/reserve/reserveMenu/save" method="post" class="form-horizontal">
                                <form:hidden path="id"/>
                                <sys:message content="${message}"/>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">上级菜单：</label>
                                    <div class="col-sm-6">
                                        <sys:treeselect id="menu" name="parent.id" value="${reserveMenu.parent.id}" labelName="parent.name" labelValue="${reserveMenu.parent.name}"
                                                        title="菜单" url="/reserve/reserveMenu/treeData" extId="${reserveMenu.id}" cssClass="required"/>
                                    </div>
                                </div>
                                <%--<div class="form-group">
                                    <label class="control-label">：</label>
                                    <div class="controls">
                                        <span class="help-inline"><font color="red">*</font> </span>
                                    </div>
                                </div>--%>
                               <%-- <div class="form-group">
                                    <label class="col-sm-3 control-label">所有父级编号：</label>
                                    <div class="col-sm-6">
                                        <form:input path="parentIds" htmlEscape="false" maxlength="2000"
                                                    class="form-control required"/>
                                        <span class="help-inline"><font color="red">*</font> </span>
                                    </div>
                                </div>--%>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">名称：</label>
                                    <div class="col-sm-6">
                                        <form:input path="name" htmlEscape="false" maxlength="100"
                                                    class="form-control required"/>
                                        <span class="help-inline"><font color="red">*</font> </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">排序：</label>
                                    <div class="col-sm-6">
                                        <form:input path="sort" htmlEscape="false" class="form-control required"/>
                                        <span class="help-inline"><font color="red">*</font> </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">链接：</label>
                                    <div class="col-sm-6">
                                        <form:input path="href" htmlEscape="false" maxlength="2000"
                                                    class="form-control "/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">目标：</label>
                                    <div class="col-sm-6">
                                        <form:input path="target" htmlEscape="false" maxlength="20"
                                                    class="form-control "/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">图标：</label>
                                    <div class="col-sm-6">
                                        <form:input path="icon" htmlEscape="false" maxlength="100"
                                                    class="form-control "/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">是否在菜单中显示：</label>
                                    <div class="col-sm-6">
                                        <form:input path="isShow" htmlEscape="false" maxlength="1"
                                                    class="form-control required"/>
                                        <span class="help-inline"><font color="red">*</font> </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">权限标识：</label>
                                    <div class="col-sm-6">
                                        <form:input path="permission" htmlEscape="false" maxlength="200"
                                                    class="form-control "/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">备注信息：</label>
                                    <div class="col-sm-6">
                                        <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255"
                                                       class="form-control"/>
                                    </div>
                                </div>
                                <div class="form-actions">
                                    <input id="btnSubmit"
                                           class="btn btn-primary"
                                           type="submit"
                                           value="保 存"/>
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
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@include file="/WEB-INF/views/include/treeview.jsp" %>
<html>
<head>
    <title>角色权限配置</title>
    <meta name="decorator" content="main"/>
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
    <script>
        // 用户-菜单
        var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
            data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
                tree.checkNode(node, !node.checked, true, true);
                return false;
            }}};
        var zNodes=[
                <c:forEach items="${menuList}" var="menu">{id:"${menu.id}", pId:"${not empty menu.parent.id?menu.parent.id:0}", name:"${not empty menu.parent.id?menu.name:'权限列表'}"},
            </c:forEach>];
        // 初始化树结构
        var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
        // 不选择父节点
        tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
        /*// 默认选择节点
        var ids = "${role.menuIds}".split(",");
        for(var i=0; i<ids.length; i++) {
            var node = tree.getNodeByParam("id", ids[i]);
            try{tree.checkNode(node, true, false);}catch(e){}
        }*/
        // 默认展开全部节点
        tree.expandAll(true);
    </script>
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
                                </div>

                                <div class="row">
                                    <label class="col-lg-1 control-label">角色授权:</label>
                                    <div class="controls">
                                        <div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
                                    </div>
                                </div>
                                <div class="row">
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
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/reserve_role_auth.js"></script>
</body>
</html>
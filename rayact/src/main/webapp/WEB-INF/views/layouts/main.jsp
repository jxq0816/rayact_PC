<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title><sitemesh:title/> - Powered By 博云睿动</title>
    <!-- Bootstrap core CSS -->
    <link href="${ctxStatic}/cleanzone/js/bootstrap/dist/css/bootstrap.css?time=21" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctxStatic}/cleanzone/fonts/font-awesome-4/css/font-awesome.min.css">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/cleanzone/js/jquery.gritter/css/jquery.gritter.css"/>

    <link rel="stylesheet" type="text/css" href="${ctxStatic}/cleanzone/js/jquery.nanoscroller/nanoscroller.css"/>
    <link rel="stylesheet" type="text/css"
          href="${ctxStatic}/cleanzone/js/jquery.easypiechart/jquery.easy-pie-chart.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/cleanzone/js/bootstrap.switch/bootstrap-switch.css"/>
    <link rel="stylesheet" type="text/css"
          href="${ctxStatic}/cleanzone/js/bootstrap.datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/cleanzone/js/jquery.select2/select2.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/cleanzone/js/bootstrap.slider/css/slider.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/cleanzone/js/intro.js/introjs.css"/>
    <!-- Custom styles for this template -->
    <link href="${ctxStatic}/cleanzone/js/jquery.icheck/skins/square/blue.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctxStatic}/cleanzone/css/pygments.css">
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.js"></script>
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.gritter/js/jquery.gritter.js"></script>
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.nanoscroller/jquery.nanoscroller.js"></script>
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/behaviour/general.js"></script>
    <script src="${ctxStatic}/cleanzone/js/jquery.ui/jquery-ui.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.sparkline/jquery.sparkline.min.js"></script>
    <script type="text/javascript"
            src="${ctxStatic}/cleanzone/js/jquery.easypiechart/jquery.easy-pie-chart.js"></script>
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.nestable/jquery.nestable.js"></script>
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/bootstrap.switch/bootstrap-switch.min.js"></script>
    <script type="text/javascript"
            src="${ctxStatic}/cleanzone/js/bootstrap.datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script src="${ctxStatic}/cleanzone/js/jquery.select2/select2.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/cleanzone/js/skycons/skycons.js" type="text/javascript"></script>
    <script src="${ctxStatic}/cleanzone/js/bootstrap.slider/js/bootstrap-slider.js" type="text/javascript"></script>
    <script src="${ctxStatic}/cleanzone/js/intro.js/intro.js" type="text/javascript"></script>
    <script src="${ctxStatic}/cleanzone/js/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="${ctxStatic}/cleanzone/js/jquery.parsley/dist/parsley.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/cleanzone/js/jquery.niftymodals/css/component.css"/>
    <link href="${ctxStatic}/cleanzone/css/style.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.gritter/js/jquery.gritter.js"></script>
    <script type="text/javascript"
            src="${ctxStatic}/cleanzone/js/jquery.niftymodals/js/jquery.modalEffects.js"></script>
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.icheck/icheck.min.js"></script>
    <link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctxStatic}/cleanzone/js/echart/echarts.common.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/common/jeesite.js" type="text/javascript"></script>
    <sitemesh:head/>
</head>

<body style="overflow-y: scroll;">
<div id="head-nav" class="navbar navbar-default navbar-fixed-top" style="position: relative">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="fa fa-gear"></span>
            </button>
            <c:if test="${alone != 'true'}">
                <a class="navbar-brand" style="margin-left: 10%;" href="${ctx}"></a>
            </c:if>
            <c:if test="${alone == 'true'}">
                <a class="navbar-brand" style="margin-left: 10%;" href="${ctx}/reserve/reserveListener/report?alone=true"></a>
            </c:if>
        </div>
        <div class="navbar-collapse collapse" style="float:right;margin-top: 20px">

            <ul class="nav navbar-nav navbar-right" >
                <li>
                    <c:if test="${alone != 'true'}">
                        <a  href="${ctx}">欢迎回来，${fns:getUser().name}</a>
                    </c:if>
                    <c:if test="${alone == 'true'}">
                        <a  href="${ctx}/reserve/reserveListener/report?alone=true">欢迎回来，${fns:getUser().name}</a>
                    </c:if>
                </li>
                <c:if test="${alone != 'true'}">
                    <li><a href="${ctx}/reserve/user/updatePasswordForm">修改密码</a></li>
                </c:if>
                <li><a href="${ctx}/logout"><img src="${ctxStatic}/cleanzone/images/logout.png"/></a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>
<div id="cl-wrapper" class="fixed-menu" style="margin-top: -35px">
    <!-- Bootstrap core JavaScript
      ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <sitemesh:body/>
</div>
<style>
    .milky {
        font-family: Microsoft YaHei;
        text-shadow: 0 1px 0px #fff;
        color: #3c3f41;
        font-size: 15px;
    }
</style>
<div  class="milky" style="text-align:center;z-index:-100;width:100%;height:90px;position: fixed;bottom: 0px;left:0px;">
    Powered by 北京博云睿动科技有限公司版权所有
</div>
<script type="text/javascript">
    $(document).ready(function () {
        //initialize the javascript
        App.init();
    });
</script>
<!--弹出对话框-->
<div class="bootbox modal fade bootbox-confirm in" tabindex="-1" role="dialog" aria-hidden="false"
     style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true"
                        style="margin-top: -10px;">×
                </button>
                <div class="bootbox-body">确认删除？</div>
            </div>
            <div class="modal-footer">
                <button data-bb-handler="cancel" type="button" data-dismiss="modal" class="btn cancel btn-default">取消
                </button>
                <button data-bb-handler="confirm" type="button" class="btn confirm btn-primary">确认</button>
            </div>
        </div>
    </div>
</div>
<div class="bootbox modal fade bootbox-alter in" tabindex="-1" role="dialog" aria-hidden="false"
     style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <button type="button" class="bootbox-close-button close" data-dismiss="modal" aria-hidden="true"
                        style="margin-top: -10px;">×
                </button>
                <div class="bootbox-body">确认？</div>
            </div>
            <div class="modal-footer">
                <button data-bb-handler="confirm" data-dismiss="modal" type="button" class="btn confirm btn-primary">
                    确认
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
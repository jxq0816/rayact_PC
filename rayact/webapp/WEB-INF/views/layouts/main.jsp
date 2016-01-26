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
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.easypiechart/jquery.easy-pie-chart.js"></script>
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
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/cleanzone/js/jquery.niftymodals/css/component.css" />
    <link href="${ctxStatic}/cleanzone/css/style.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.gritter/js/jquery.gritter.js"></script>
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.niftymodals/js/jquery.modalEffects.js"></script>
    <script type="text/javascript" src="${ctxStatic}/cleanzone/js/jquery.icheck/icheck.min.js"></script>
    <link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
    <script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

    <script src="${ctxStatic}/common/jeesite.js" type="text/javascript"></script>
    <sitemesh:head/>
</head>

<body>
<div id="head-nav" class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="fa fa-gear"></span>
            </button>
            <a class="navbar-brand" href="#"><span>博云睿动</span></a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="${ctx}/">首页</a></li>

                <%--<li><a href="#about">储值卡会员</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">场地售卖 <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li class="dropdown-submenu"><a href="#">Sub menu</a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Action</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something else here</a></li>
                            </ul>
                        </li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">人次卡售卖<b class="caret"></b></a>
                    <ul class="dropdown-menu col-menu-2">
                        <li class="col-sm-6 no-padding">
                            <ul>
                                <li class="dropdown-header"><i class="fa fa-group"></i>Users</li>
                                <li><a href="#">Action</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something else here</a></li>
                                <li class="dropdown-header"><i class="fa fa-gear"></i>Config</li>
                                <li><a href="#">Action</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something else here</a></li>
                            </ul>
                        </li>
                        <li class="col-sm-6 no-padding">
                            <ul>
                                <li class="dropdown-header"><i class="fa fa-legal"></i>Sales</li>
                                <li><a href="#">New sale</a></li>
                                <li><a href="#">Register a product</a></li>
                                <li><a href="#">Register a client</a></li>
                                <li><a href="#">Month sales</a></li>
                                <li><a href="#">Delivered orders</a></li>
                            </ul>
                        </li>
                    </ul>
                </li>--%>
            </ul>
            <ul class="nav navbar-nav navbar-right user-nav">
                <li class="dropdown profile_menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${fns:getUser().name}<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">个人中心</a></li>
                        <li class="divider"></li>
                        <li><a href="${ctx}/logout">退出</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right not-nav">
                <li class="button dropdown">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class=" fa fa-comments"></i></a>
                    <ul class="dropdown-menu messages">
                        <li>
                            <div class="nano nscroller">
                                <div tabindex="0" class="content">
                                    <ul>
                                        <li>
                                            <a href="#">
                                                <img src="images/avatar2.jpg" alt="avatar"><span class="date pull-right">13 Sept.</span> <span class="name">Daniel</span> I'm following you, and I want your money!
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <img src="images/avatar_50.jpg" alt="avatar"><span class="date pull-right">20 Oct.</span><span class="name">Adam</span> is now following you
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <img src="images/avatar4_50.jpg" alt="avatar"><span class="date pull-right">2 Nov.</span><span class="name">Michael</span> is now following you
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <img src="images/avatar3_50.jpg" alt="avatar"><span class="date pull-right">2 Nov.</span><span class="name">Lucy</span> is now following you
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                                <div style="display: none;" class="pane"><div style="top: 0px;" class="slider"></div></div></div>
                            <ul class="foot"><li><a href="#">View all messages </a></li></ul>
                        </li>
                    </ul>
                </li>
                <li class="button dropdown">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-globe"></i><span class="bubble">2</span></a>
                    <ul class="dropdown-menu">
                        <li>
                            <div class="nano nscroller">
                                <div tabindex="0" class="content">
                                    <ul>
                                        <li><a href="#"><i class="fa fa-cloud-upload info"></i><b>Daniel</b> is now following you <span class="date">2 minutes ago.</span></a></li>
                                        <li><a href="#"><i class="fa fa-male success"></i> <b>Michael</b> is now following you <span class="date">15 minutes ago.</span></a></li>
                                        <li><a href="#"><i class="fa fa-bug warning"></i> <b>Mia</b> commented on post <span class="date">30 minutes ago.</span></a></li>
                                        <li><a href="#"><i class="fa fa-credit-card danger"></i> <b>Andrew</b> killed someone <span class="date">1 hour ago.</span></a></li>
                                    </ul>
                                </div>
                                <div style="display: none;" class="pane"><div style="top: 0px;" class="slider"></div></div></div>
                            <ul class="foot"><li><a href="#">View all activity </a></li></ul>
                        </li>
                    </ul>
                </li>
            </ul>

        </div><!--/.nav-collapse -->
    </div>
</div>

<div id="cl-wrapper" class="fixed-menu">

<!-- Bootstrap core JavaScript
  ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
    <sitemesh:body/>
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
                <button data-bb-handler="cancel" type="button" data-dismiss="modal" class="btn cancel btn-default">取消</button>
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
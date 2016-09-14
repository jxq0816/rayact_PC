<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场馆管理</title>
    <meta name="decorator" content="main"/>
    <%@include file="/WEB-INF/views/include/upload.jsp" %>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="venue"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>场馆管理</h3>
                </div>
                <div class="content">
                    <form:form id="inputForm" modelAttribute="reserveVenue" action="${ctx}/reserve/reserveVenue/save"
                               method="post">
                        <form:hidden path="id"/>
                        <input type="hidden" name="token" value="${token}"/>

                        <div class="tab-container">
                            <ul class="nav nav-tabs" id="myTab">
                                <li class="active"><a href="#home" data-toggle="tab">基本信息</a></li>
                                <li><a href="#venueLabel" data-toggle="tab">场馆标签</a></li>
                                <li><a href="#messages" data-toggle="tab">地图位置</a></li>
                                <li><a href="#settings" data-toggle="tab">图片</a></li>
                            </ul>

                            <div class="tab-content">
                                <div class="tab-pane active" id="home">
                                    <div class="form-horizontal group-border-dashed">
                                        <div class="form-group">
                                            <label for="name" class="col-sm-3 control-label">场馆名称</label>

                                            <div class="col-sm-6">
                                                <form:input id="name" path="name" htmlEscape="false" maxlength="30"
                                                            class="form-control"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">是否启用</label>
                                            <div class="col-sm-6">
                                                <sys:radio name="available" value="${reserveVenue.available}"
                                                           items="${fns:getDictList('yes_no')}" itemLabel="label"
                                                           itemValue="value" cssClass="icheck">
                                                </sys:radio>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">联系电话</label>
                                            <div class="col-sm-6">
                                                <form:input path="tel" htmlEscape="false" maxlength="20"
                                                            cssClass="form-control"/>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">营业时间</label>
                                            <div class="col-sm-2">
                                                <input name="startTime" type="text"  maxlength="20"
                                                       class="form-control Wdate"
                                                       value="${reserveVenue.startTime}"
                                                       onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                                            </div>
                                            <div class="col-sm-2">
                                                <input name="endTime" type="text"  maxlength="20"
                                                       class="input-medium form-control Wdate "
                                                       value="${reserveVenue.endTime}"
                                                       onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">备注</label>
                                            <div class="col-sm-6">
                                                <form:textarea path="remarks" htmlEscape="false" rows="4"
                                                               maxlength="255" class="form-control"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane" id="venueLabel">
                                    <div class="row">
                                        <div class="pull-right">
                                            <input type="button" class="btn btn-primary" value="添加标签"
                                                   onclick="addLabel()"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="pull-left" id="venueLabelGroup">
                                            <div class="form-group">
                                                <label for="venueLabelFirst">标签</label>
                                                <input name="moreService" type="text" id="venueLabelFirst"
                                                       class="form-control" style="width:200px"
                                                       value="${reserveVenue.moreService}"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                            <div class="form-group">
                                                <label for="avePrice">人均消费</label>
                                                <input name="avePrice" type="text" id="avePrice"
                                                       class="form-control" style="width:200px"
                                                       value="${reserveVenue.avePrice}"/>
                                            </div>
                                            <div class="form-group">
                                                <label for="evaluate_score">评分</label>
                                                <input name="evaluateScore" type="text" id="evaluate_score"
                                                       class="form-control" style="width:200px" readonly="readonly"
                                                       value="${reserveVenue.evaluateScore}"/>
                                            </div>
                                    </div>
                                </div>
                                <div class="tab-pane" id="messages">
                                    <table class="table table-bordered">
                                        <tr>
                                            <td>城市：</td>
                                            <td>
                                                    <form:input  path="cityName" htmlEscape="false" cssClass="form-control" cssStyle="width: 30%"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>区：</td>
                                            <td>
                                                <form:input  path="districtName" htmlEscape="false" cssClass="form-control" cssStyle="width: 30%"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>地址：</td>
                                            <td>
                                                <div class="col-sm-6">
                                                    <form:input id="map_keyword" path="address" htmlEscape="false"
                                                                maxlength="200" cssClass="form-control"/>
                                                </div>
                                                <div class="col-sm-2">
                                                    <input type="button" value="搜索" onclick="localsearch('全国');"
                                                           class="btn btn-primary">
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>地图位置：</td>
                                            <td>
                                                经度:
                                                <input name="addressX" id="map_x"
                                                       onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"
                                                       readonly="readonly" value="${reserveVenue.addressX}"
                                                       class="com_info_text" style="float:none">
                                                &nbsp;&nbsp;
                                                维度:
                                                <input name="addressY" id="map_y"
                                                       onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"
                                                       readonly="readonly" value="${reserveVenue.addressY}"
                                                       class="com_info_text" style="float:none">
                                                &nbsp;&nbsp;
                                                <span id="by_map" class="errordisplay">请先设置地图位置</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2">
                                                <script src="http://api.map.baidu.com/api?v=1.4&amp;ak=6c075a3500d81e856868ac3d5fbb4d52"></script>
                                                <script type="text/javascript"
                                                        src="http://api.map.baidu.com/getscript?v=1.4&amp;ak=6c075a3500d81e856868ac3d5fbb4d52&amp;services=&amp;t=20150522093217"></script>
                                                <script type="text/javascript" src="${ctxStatic}/map/map.js"></script>
                                                <div class="com_body">
                                                    <div class="admin_note_map" style="position:relative">
                                                        <div id="map_container"
                                                             style="width: 100%; height: 350px; overflow: hidden; position: relative; z-index: 0; color: rgb(0, 0, 0); text-align: left; background-color: rgb(243, 241, 236);">
                                                            <div style="overflow: visible; position: absolute; z-index: 0; left: -66px; top: -36px; cursor: url(http://api.map.baidu.com/images/openhand.cur) 8 8, default;">
                                                                <div class="BMap_mask"
                                                                     style="position: absolute; left: 66px; top: 36px; z-index: 9; overflow: hidden; -webkit-user-select: none; width: 621px; height: 350px;"></div>
                                                                <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 200;">
                                                                    <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 800;"></div>
                                                                    <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 700;">
                                                                        <span class="BMap_Marker BMap_noprint"
                                                                              unselectable="on" style="position:
                                                                        absolute; padding: 0px; margin: 0px; border:
                                                                        0px; cursor: pointer; width: 23px; height: 25px;
                                                                        left: 301px; top: 150px; z-index: -7983000;
                                                                        background:
                                                                        url(http://api.map.baidu.com/images/blank.gif);"
                                                                              title=""></span></div>
                                                                    <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 600;"></div>
                                                                    <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 500;">
                                                                        <label class="BMapLabel" unselectable="on"
                                                                               style="position: absolute; display: none; cursor: inherit; border: 1px solid rgb(190, 190, 190); padding: 1px; white-space: nowrap; font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 12px; line-height: normal; font-family: arial, sans-serif; z-index: -20000; color: rgb(190, 190, 190); background-color: rgb(190, 190, 190);">shadow</label>
                                                                    </div>
                                                                    <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 400;">
                                                                        <span class="BMap_Marker" unselectable="on"
                                                                              style="position: absolute; padding: 0px; margin: 0px; border: 0px; width: 0px; height: 0px; left: 301px; top: 150px; z-index: -7983000;"><div
                                                                                style="position: absolute; margin: 0px; padding: 0px; width: 23px; height: 25px; overflow: hidden;">
                                                                            <img src="http://api.map.baidu.com/images/marker_red_hd.png"
                                                                                 style="border:none;left:0px; top:0px; position:absolute;; width:23px; height:25px;">
                                                                        </div></span></div>
                                                                    <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 300;">
                                                                        <span unselectable="on"
                                                                              style="position: absolute; padding: 0px; margin: 0px; border: 0px; width: 20px; height: 11px; left: 305px; top: 164px;"><div
                                                                                style="position: absolute; margin: 0px; padding: 0px; width: 20px; height: 11px; overflow: hidden;">
                                                                            <img src="http://api.map.baidu.com/images/marker_red_sprite.png"
                                                                                 style="border:none;left:-19px; top:-13px; position:absolute;">
                                                                        </div></span></div>
                                                                    <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 201;"></div>
                                                                    <div style="position: absolute; height: 0px; width: 0px; left: 0px; top: 0px; z-index: 200;"></div>
                                                                </div>
                                                                <div style="position: absolute; overflow: visible; top: 0px; left: 0px; z-index: 1;">
                                                                    <div style="position: absolute; overflow: visible; z-index: -100; left: 376px; top: 211px;">
                                                                        <img src="http://online3.map.bdimg.com/tile/?qt=tile&amp;x=6327&amp;y=2356&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                             style="position: absolute; border: none; width: 128px; height: 128px; left: -95px; top: -111px; max-width: none; opacity: 1;"><img
                                                                            src="http://online4.map.bdimg.com/tile/?qt=tile&amp;x=6328&amp;y=2356&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: 33px; top: -111px; max-width: none; opacity: 1;"><img
                                                                            src="http://online2.map.bdimg.com/tile/?qt=tile&amp;x=6327&amp;y=2355&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: -95px; top: 17px; max-width: none; opacity: 1;"><img
                                                                            src="http://online4.map.bdimg.com/tile/?qt=tile&amp;x=6327&amp;y=2357&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: -95px; top: -239px; max-width: none; opacity: 1;"><img
                                                                            src="http://online1.map.bdimg.com/tile/?qt=tile&amp;x=6325&amp;y=2356&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: -351px; top: -111px; max-width: none; opacity: 1;"><img
                                                                            src="http://online0.map.bdimg.com/tile/?qt=tile&amp;x=6329&amp;y=2356&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: 161px; top: -111px; max-width: none; opacity: 1;"><img
                                                                            src="http://online0.map.bdimg.com/tile/?qt=tile&amp;x=6328&amp;y=2357&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: 33px; top: -239px; max-width: none; opacity: 1;"><img
                                                                            src="http://online3.map.bdimg.com/tile/?qt=tile&amp;x=6326&amp;y=2357&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: -223px; top: -239px; max-width: none; opacity: 1;"><img
                                                                            src="http://online1.map.bdimg.com/tile/?qt=tile&amp;x=6326&amp;y=2355&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: -223px; top: 17px; max-width: none; opacity: 1;"><img
                                                                            src="http://online2.map.bdimg.com/tile/?qt=tile&amp;x=6325&amp;y=2357&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: -351px; top: -239px; max-width: none; opacity: 1;"><img
                                                                            src="http://online4.map.bdimg.com/tile/?qt=tile&amp;x=6329&amp;y=2355&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: 161px; top: 17px; max-width: none; opacity: 1;"><img
                                                                            src="http://online2.map.bdimg.com/tile/?qt=tile&amp;x=6326&amp;y=2356&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: -223px; top: -111px; max-width: none; opacity: 1;"><img
                                                                            src="http://online3.map.bdimg.com/tile/?qt=tile&amp;x=6328&amp;y=2355&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: 33px; top: 17px; max-width: none; opacity: 1;"><img
                                                                            src="http://online0.map.bdimg.com/tile/?qt=tile&amp;x=6325&amp;y=2355&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: -351px; top: 17px; max-width: none; opacity: 1;"><img
                                                                            src="http://online1.map.bdimg.com/tile/?qt=tile&amp;x=6329&amp;y=2357&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: 161px; top: -239px; max-width: none; opacity: 1;"><img
                                                                            src="http://online1.map.bdimg.com/tile/?qt=tile&amp;x=6327&amp;y=2354&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: -95px; top: 145px; max-width: none; opacity: 1;"><img
                                                                            src="http://online4.map.bdimg.com/tile/?qt=tile&amp;x=6330&amp;y=2354&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: 289px; top: 145px; max-width: none; opacity: 1;"><img
                                                                            src="http://online1.map.bdimg.com/tile/?qt=tile&amp;x=6330&amp;y=2356&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: 289px; top: -111px; max-width: none; opacity: 1;"><img
                                                                            src="http://online0.map.bdimg.com/tile/?qt=tile&amp;x=6326&amp;y=2354&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: -223px; top: 145px; max-width: none; opacity: 1;"><img
                                                                            src="http://online2.map.bdimg.com/tile/?qt=tile&amp;x=6328&amp;y=2354&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: 33px; top: 145px; max-width: none; opacity: 1;"><img
                                                                            src="http://online2.map.bdimg.com/tile/?qt=tile&amp;x=6330&amp;y=2357&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: 289px; top: -239px; max-width: none; opacity: 1;"><img
                                                                            src="http://online0.map.bdimg.com/tile/?qt=tile&amp;x=6330&amp;y=2355&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: 289px; top: 17px; max-width: none; opacity: 1;"><img
                                                                            src="http://online3.map.bdimg.com/tile/?qt=tile&amp;x=6329&amp;y=2354&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: 161px; top: 145px; max-width: none; opacity: 1;"><img
                                                                            src="http://online4.map.bdimg.com/tile/?qt=tile&amp;x=6325&amp;y=2354&amp;z=15&amp;styles=ph&amp;udt=20150518"
                                                                            style="position: absolute; border: none; width: 128px; height: 128px; left: -351px; top: 145px; max-width: none; opacity: 1;">
                                                                    </div>
                                                                </div>
                                                                <div style="position: absolute; overflow: visible; top: 0px; left: 0px; z-index: 2;"></div>
                                                            </div>
                                                            <div class=" anchorBL"
                                                                 style="height: 32px; position: absolute; z-index: 10; bottom: 2px; right: auto; top: auto; left: 3px;">
                                                                <a title="到百度地图查看此区域" target="_blank"
                                                                   href="http://map.baidu.com/?sr=1"
                                                                   style="outline: none;"><img
                                                                        style="border:none;width:77px;height:32px"
                                                                        src="http://api.map.baidu.com/images/copyright_logo.png"></a>
                                                            </div>
                                                            <div id="zoomer"
                                                                 style="position:absolute;z-index:0;top:0px;left:0px;overflow:hidden;visibility:hidden;cursor:url(http://api.map.baidu.com/images/openhand.cur) 8 8,default">
                                                                <div class="BMap_zoomer" style="top:0;left:0;"></div>
                                                                <div class="BMap_zoomer" style="top:0;right:0;"></div>
                                                                <div class="BMap_zoomer" style="bottom:0;left:0;"></div>
                                                                <div class="BMap_zoomer"
                                                                     style="bottom:0;right:0;"></div>
                                                            </div>
                                                            <div unselectable="on"
                                                                 class=" BMap_stdMpCtrl BMap_stdMpType0 BMap_noprint anchorTL"
                                                                 style="width: 62px; height: 186px; bottom: auto; right: auto; top: 10px; left: 10px; position: absolute; z-index: 1100;">
                                                                <div class="BMap_stdMpPan">
                                                                    <div class="BMap_button BMap_panN"
                                                                         title="向上平移"></div>
                                                                    <div class="BMap_button BMap_panW"
                                                                         title="向左平移"></div>
                                                                    <div class="BMap_button BMap_panE"
                                                                         title="向右平移"></div>
                                                                    <div class="BMap_button BMap_panS"
                                                                         title="向下平移"></div>
                                                                    <div class="BMap_stdMpPanBg BMap_smcbg"></div>
                                                                </div>
                                                                <div class="BMap_stdMpZoom"
                                                                     style="height: 141px; width: 62px;">
                                                                    <div class="BMap_button BMap_stdMpZoomIn"
                                                                         title="放大一级"
                                                                         style="background-position: 0px -221px;">
                                                                        <div class="BMap_smcbg"></div>
                                                                    </div>
                                                                    <div class="BMap_button BMap_stdMpZoomOut"
                                                                         title="缩小一级" style="top: 120px;">
                                                                        <div class="BMap_smcbg"></div>
                                                                    </div>
                                                                    <div class="BMap_stdMpSlider"
                                                                         style="height: 102px;">
                                                                        <div class="BMap_stdMpSliderBgTop"
                                                                             style="height: 102px;">
                                                                            <div class="BMap_smcbg"></div>
                                                                        </div>
                                                                        <div class="BMap_stdMpSliderBgBot"
                                                                             style="top: 19px; height: 87px;"></div>
                                                                        <div class="BMap_stdMpSliderMask"
                                                                             title="放置到此级别"></div>
                                                                        <div class="BMap_stdMpSliderBar" title="拖动缩放"
                                                                             style="cursor: url(http://api.map.baidu.com/images/openhand.cur) 8 8, default; top: 19px;">
                                                                            <div class="BMap_smcbg"></div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="BMap_zlHolder">
                                                                        <div class="BMap_zlSt">
                                                                            <div class="BMap_smcbg"></div>
                                                                        </div>
                                                                        <div class="BMap_zlCity">
                                                                            <div class="BMap_smcbg"></div>
                                                                        </div>
                                                                        <div class="BMap_zlProv">
                                                                            <div class="BMap_smcbg"></div>
                                                                        </div>
                                                                        <div class="BMap_zlCountry">
                                                                            <div class="BMap_smcbg"></div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div unselectable="on"
                                                                 class=" BMap_cpyCtrl BMap_noprint anchorBL"
                                                                 style="cursor: default; white-space: nowrap; color: black; font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 11px; line-height: 15px; font-family: arial, sans-serif; bottom: 2px; right: auto; top: auto; left: 81px; position: absolute; z-index: 10; background: none;">
                                                                <span _cid="1" style="display: inline;"><span
                                                                        style="font-size:11px">© 2015 Baidu&nbsp;- Data © <a
                                                                        target="_blank" href="http://www.navinfo.com/"
                                                                        style="display:inline;">NavInfo</a> &amp; <a
                                                                        target="_blank"
                                                                        href="http://www.cennavi.com.cn/"
                                                                        style="display:inline;">CenNavi</a> &amp; <a
                                                                        target="_blank" href="http://www.365ditu.com/"
                                                                        style="display:inline;">道道通</a></span></span>
                                                            </div>
                                                        </div>
                                                        <br>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="tab-pane" id="settings">
                                    <table class="table table-bordered">
                                        <tr>
                                            <td colspan="4">
                                                <mechanism:upload id="financeSchoolPic" fdKey="venuePic"
                                                                  name="attMains1" exts=""
                                                                  btnText="添加"
                                                                  modelId="${reserveVenue.id}"
                                                                  showImg="true" resizeImg="true" resizeWidth="454"
                                                                  resizeHeight="247"
                                                                  imgWidth="120" imgHeight="80"
                                                                  modelName="ReserveVenue" multi="false"/>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions">
                            <input id="btnSubmit"
                                   class="btn btn-primary"
                                   type="submit"
                                   value="保 存"/>&nbsp;
                            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        //$("#name").focus();
        $("#inputForm").validate({
            submitHandler: function (form) {
                formLoding('正在提交，请稍等...');
                form.submit();
            },
            errorContainer: "#messageBox",
            errorPlacement: function (error, element) {
                formLoding('输入有误，请先更正。');
                if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                    error.appendTo(element.parent().parent());
                } else {
                    error.insertAfter(element);
                }
            }
        });
        $(".alternate-bar").on('click', 'a', function () {
            var expand = $(this).attr("data-expand");
            $("div[data-expand-target='" + expand + "']").toggle();
            //$("#" + expand).parents(expand).show();
        });
    });
    function addLabel() {
        var s = '<div class="form-group"> <label for="venueLabelFirst">添加标签</label><input name="moreService" type="text" class="form-control required" value="" style="width:200px"/></div>';
        $("#venueLabelGroup").append(s);
    }
</script>
<script>
    var map = new BMap.Map("map_container");
    setLocation('map_container', 116.404, 39.915, "map_x", "map_y");
    $(document).ready(function () {
        $(".com_admin_ask").hover(function () {
            layer.tips("精确定位，更直观展示企业位置！", this, {
                guide: 1,
                style: ['background-color:#F26C4F; color:#fff;top:-7px', '#F26C4F']
            });
        }, function () {
            layer.closeTips();
        });
        //根据IP到城市开始
        function myFun(result) {
            var cityName = result.name;
            map.setCenter(cityName);
        }

        var myCity = new BMap.LocalCity();
        myCity.get(myFun);
        //根据IP到城市结结束
    });
    var local;
    function getLocalResult() {
        var map_keyword = $.trim($("#map_keyword").val());
        var points = local.getResults();
        var lngLat = points.getPoi(0).point;
        setLocation('map_container', lngLat.lng, lngLat.lat, "map_x", "map_y");
        document.getElementById("map_x").value = lngLat.lng;
        document.getElementById("map_y").value = lngLat.lat;

    }
    function localsearch(city) {
        if ($("#map_keyword").val() == "") {
            layer.msg('请输入地址！', 2, 8);
            return false;
        }
        local = new BMap.LocalSearch(city, {
            renderOptions: {
                map: map,
                panel: "r-result",
                autoViewport: true,
                selectFirstResult: false
            }, onSearchComplete: getLocalResult
        });
        map.centerAndZoom(new BMap.Point(116.404, 39.915), 4);
        local.search($("#map_keyword").val());
    }
    function checkpost() {
        if ($.trim($("#map_x").val()) == '' || $.trim($("#map_y").val()) == '') {
            layer.msg('请先设置地图位置！', 2, 8);
            return false;
        }
    }
    function setLocation(id, x, y, xid, yid) {
        var data = get_map_config();
        var config = eval('(' + data + ')');
        var rating, map_control_type, map_control_anchor;
        if (!x && !y) {
            x = config.map_x;
            y = config.map_y;
        }
        var point = new BMap.Point(x, y);
        var marker = new BMap.Marker(point);
        var opts = {type: BMAP_NAVIGATION_CONTROL_LARGE}
        map.enableScrollWheelZoom(true);
        map.addControl(new BMap.NavigationControl(opts));
        map.centerAndZoom(point, 15);
        map.addOverlay(marker);
        map.addEventListener("click", function (e) {
            var info = new BMap.InfoWindow('', {width: 260});
            var projection = this.getMapType().getProjection();
            var lngLat = e.point;
            document.getElementById(xid).value = lngLat.lng;
            document.getElementById(yid).value = lngLat.lat;
            map.clearOverlays();
            var point = new BMap.Point(lngLat.lng, lngLat.lat);
            var marker = new BMap.Marker(point);
            map.addOverlay(marker);
        });
    }
</script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地预定</title>
    <meta name="decorator" content="default"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=36"/>
    <link href="${ctxStatic}/bootstrap/2.3.1/css/bootstrap-switch.min.css" rel="stylesheet" />
    <style>
        .modal {
            width: 100%;
            position: fixed;
            text-align: center;
            margin: 0px auto;
            top: 0px;
            left: 0px;
            bottom: 150px;
            right: 0px;
            z-index: 1050;
        }

        .modal_wrapper {
            display: table;
            overflow: auto;
            overflow-y: scroll;
            height: 100%;
            -webkit-overflow-scrolling: touch;
            outline: 0;
            text-align: center;
            margin: 0px auto;
        }

        .modal-dialog {
            margin-top: 0px;
            display: table-cell;
            vertical-align: middle;
            margin: 0px 20px;
        }
    </style>
</head>
<body>
<div class="breadcrumb form-search">
    <input type="button" value="常规价格设置" class="btn btn-success btn-small" data-toggle="modal" data-target="#myModal"/>
</div>
<div class="tab-tit-first">
    <a name="order"></a>
    <ul>
        <c:forEach items="${venueList}" var="venue" varStatus="status">
            <li <j:if test="${status.index==0}"> class="on"</j:if>><a
                    href="${ctx}/reserve/price/set?weekIndex=${weekIndex}&venueId=${venue.id}">${venue.name}</a></li>
        </c:forEach>
    </ul>
</div>

<div class="tab-tit">
    <a name="order"></a>
    <ul>
        <c:forEach items="${weekDays}" var="week" varStatus="status">
            <li <j:if test="${status.index == weekIndex}">class="on"</j:if> ><a
                    href="${ctx}/reserve/price/set?weekIndex=${status.index}&venueId=${venueId}">${week}</a></li>
        </c:forEach>
    </ul>
</div>
<div class="sy-tab-cont">
    <div class="table-left">
        <table class="table-set" style="width:auto;">
            <thead>
            <tr>
                <th></th>
                <c:forEach items="${times}" var="t">
                    <th><span>${t}</span></th>
                </c:forEach>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${fieldList}" var="field">
                <tr>
                    <th style="background-color: #fff;">${field.name}
                        <div class="switch" data-on-label="<i class='icon-ok icon-white'></i>" data-off-label="<i class='icon-remove'></i>">
                            <input type="checkbox" checked />
                        </div>
                    </th>
                    <c:forEach items="${times}" var="t">
                        <td status="2" placeholder="该场次已经售出" group_ids="" goods_id="30337886"><input
                                type="hidden" name="goods_id[]" group_ids="" value="30337886"></td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <script type="text/javascript">
        $(".table-left-l ul").css("padding-top", parseInt($(".table-set thead").eq(0).height()) + 3 + "px");
    </script>
    <!--
    <div class="my-order-right">
        <form id="order_form" method="post" action="/order/confirm">
            <div class="proj">预订项目：<span><strong>羽毛球</strong></span></div>
            <div class="date">日期：<span>2016-01-05&nbsp;(周二)</span></div>
            <div class="changci">
                场次：
                <ul class="my-sel-place">
                </ul>
                <p class="tips">已选择<span>0</span>个场地，再次单击取消</p>
            </div>
            <p class="total-price">共计：<em>0</em>元</p>
            <a class="sy-btn1 go" onclick="showCofirmOrder()" href="javascript:void(0);">提交订单</a>
            <p style="font-size: 11px;text-align: center;"></p>
        </form>
    </div>
-->

    <div class="h20"></div>
</div>
<div class="modal hide fade" style="width: 800px;" id="myModal" tabindex="-1" role="dialog">
    <div class="modal-header">
        <button class="close" type="button" data-dismiss="modal">×</button>
        <h3 id="myModalLabel">常规价格设置</h3>
    </div>

    <div class="modal-body">
        <table class="table">
            <thead>
            <tr>
                <th></th>
                <th>周一</th>
                <th>周二</th>
                <th>周三</th>
                <th>周四</th>
                <th>周五</th>
                <th>周六</th>
                <th>周日</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>散客</td>
                <td><input type="text" style="width: 50px;"/></td>
                <td><input type="text" style="width: 50px;"/></td>
                <td><input type="text" style="width: 50px;"/></td>
                <td><input type="text" style="width: 50px;"/></td>
                <td><input type="text" style="width: 50px;"/></td>
                <td><input type="text" style="width: 50px;"/></td>
                <td><input type="text" style="width: 50px;"/></td>
            </tr>
            <tr>
                <td>会员</td>
                <td><input type="text" style="width: 50px;"/></td>
                <td><input type="text" style="width: 50px;"/></td>
                <td><input type="text" style="width: 50px;"/></td>
                <td><input type="text" style="width: 50px;"/></td>
                <td><input type="text" style="width: 50px;"/></td>
                <td><input type="text" style="width: 50px;"/></td>
                <td><input type="text" style="width: 50px;"/></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="modal-footer">
        <input type="hidden" id="url"/>
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <a onclick="urlSubmit()" class="btn btn-success" data-dismiss="modal">确定</a>
    </div>
</div>
<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap-switch.min.js"></script>
</body>
</html>

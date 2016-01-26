<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地预定</title>
    <meta name="decorator" content="main"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=3399"/>
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
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="venue"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <form id="order_form" method="post">
        <div class="tab-tit-first">
            <ul>
                <c:forEach items="${reserveVenueList}" var="venue" varStatus="status">
                    <li <j:if test="${status.index==0}">class="on"</j:if>><a
                            href="/detail/4135-1.html?t=1451923200#order">${venue.name}</a></li>
                </c:forEach>
            </ul>
        </div>

        <div class="tab-tit">
            <a name="order"></a>
            <ul>
                <%--<j:else><j:if test="${status.index==0}">class="on"</j:if></j:else>--%>
                <c:forEach items="${timeSlot}" var="slot" varStatus="status">
                    <li <j:if test="${consDate.time eq slot.value}">class="on"</j:if> > <a
                        href="${ctx}/reserve/field/main?t=${slot.value}">${slot.key}</a></li>
                </c:forEach>
            </ul>
            <ul class="table-ul">
                <li style="margin-left: 0px;"><span class="green-bg-color"></span>可预订</li>
                <li><span class="blue-bg-color"></span>已选场次</li>
                <li><span class="grey-bg-color"></span>已占用</li>
            </ul>
        </div>
        <div class="sy-tab-cont">

            <div class="table-left">

                <table class="table-chang" style="width:auto;">
                    <thead>
                    <tr>
                        <th></th>
                        <c:forEach items="${times}" var="t">
                            <th><span>${t}</span></th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${venueFieldPriceList}" var="fieldPrice">
                        <tr>
                            <th style="background-color: #fff;">${fieldPrice.fieldName}</th>
                            <c:forEach items="${fieldPrice.timePriceList}" var="t">
                                <td status="${t.status}" class="<j:if test="${'0' eq t.status}">access</j:if>"
                                    price="${t.price}" data-time="${t.time}" data-id="${fieldPrice.fieldId}"
                                    placeholder="${fieldPrice.fieldName} ${t.time} ${t.price}元">
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>


            <script type="text/javascript">
                $(".table-left-l ul").css("padding-top", parseInt($(".table-chang thead").eq(0).height()) + 3 + "px");
            </script>
            <div class="my-order-right" style="overflow-y:auto; overflow-x:auto; width:200px; height:230px;">
                <div class="changci">
                    已选场次：
                    <ul class="my-sel-place">
                    </ul>
                </div>

                <input type="hidden" id="reserveOrderPrice" name="orderPrice">
                <input type="hidden" id="venueId" name="reserveVenue.id" value="${reserveVenue.id}">
                <input type="hidden" name="consDate" value="<fmt:formatDate value="${consDate}" pattern="yyyy-MM-dd"/>">
            </div>
            <div class="h20"></div>
            <!--客户信息-->
            <%--<div class="date">
                <table class="table">
                    <tr>
                        <td colspan="4">
                            <select id="customer" name="member.id" style="width: 155px;"
                                    class="select2">
                                <option value="" selected="selected">--搜索顾客信息--</option>
                                <c:forEach items="${memberList}" var="member">
                                    <option value="${member.id}">${member.mobile}-${member.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td colspan="2">
                            <div style="float: right">
                                <p class="total-price"></p>
                                <a class="btn btn-primary btn-small" onclick="showCofirmOrder()"
                                   href="javascript:void(0);">预定</a>
                                <!-- href="#my-order" -->
                                &nbsp;&nbsp;&nbsp;
                                <a data-toggle="modal" onclick="showCofirmPay()" class="btn btn-primary btn-small"
                                   href="javascript:void(0);">售卖</a>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>姓名:</td>
                        <td>
                            <input type="text" id="userName" name="userName" style="width: 100px;" placeholder="姓名"
                                   class="input-small"/>
                        </td>
                        <td>电话:</td>
                        <td>
                            <input type="text" id="consMobile" name="consMobile" style="width: 100px;"
                                   placeholder="电话"
                                   class="input-small"/>
                        </td>
                        <td colspan="2">
                            <input type="text" name="remarks" style="width: 150px;" placeholder="备注"
                                   class="input-xlarge"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            会员信息:
                        </td>
                        <td colspan="5">
                            <input type="hidden" id="memberPrice"/>
                            <span id="memberInfo"></span>
                        </td>
                    </tr>
                </table>
            </div>--%>
        </div>
    </form>
    <!--dialog-->
    <form id="payModal" class="modal hide fade form-horizontal" style="width: 800px;">
        <div class="modal-header">
            <button class="close" type="button" data-dismiss="modal">×</button>
            <h3 id="myModalLabel">确认付款</h3>
        </div>
        <div class="modal-body">
            <div class="row">
                <div class="span3">
                    应收金额
                    <table class="table">
                        <tr>
                            <td>订单金额:</td>
                            <td><span id="orderPrice"></span></td>
                        </tr>
                        <tr>
                            <td>附加费用:</td>
                            <td><input type="text" style="width:70px;" id="additionalPrice" name="additionalPrice"/>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="span3">
                    支付方式
                    <table class="table">
                        <tr>
                            <td>
                                <sys:radio name="payType" rowIndex="3" value="" items="${fns:getDictList('pay_type')}"
                                           itemLabel="label" itemValue="value"></sys:radio>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="span3">
                    实收金额
                    <table class="table">
                        <tr>
                            <td>支付金额:</td>
                            <td><input type="text" style="width: 70px;" name="collectPrice" id="collectPrice"/></td>
                        </tr>
                        <tr>
                            <td>优惠金额:</td>
                            <td><input type="text" style="width: 70px;" name="discountPrice" id="discountPrice"/></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                            <textarea id="remarks" placeholder="备注" cols="8" style="width: 260px;" name="remarks"
                                      maxlength="255"
                                      class="input-xxlarge valid" rows="4"></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <input type="hidden" id="url"/>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <a onclick="paySubmit()" class="btn btn-success" data-dismiss="modal">确定</a>
        </div>
    </form>
</div>
<!--end dialog-->
<script type="text/javascript">
    function formatRepoProvince(repo) {
        if (repo.loading) return repo.text;
        var markup = "<div>" + repo.name + "</div>";
        return markup;
    }
    function checkForm() {
        var userName = $("#userName").val();
        if (userName == '') {
            alert('请输入预定人姓名');
            return false;
        }
        var consMobile = $("#consMobile").val();
        if (consMobile == '') {
            alert('请输入预定人手机');
            return false;
        }
        var length = $(".my-sel-place li").length;
        if (length < 1) {
            alert('请选择预定场地');
            return false;
        }
        return true;
    }

    //点击售卖
    function paySubmit() {
        var payType = $('input[name="payType"]:checked').val();
        var form = $("#order_form").serializeArray();
        var add = {"name": "additionalPrice", "value": $("#additionalPrice").val()};
        form.push(add);
        add = {"name": "payType", "value": payType};
        form.push(add);
        add = {"name": "collectPrice", "value": $("#collectPrice").val()};
        form.push(add);
        add = {"name": "discountPrice", "value": $("#discountPrice").val()};
        form.push(add);
        $.postItems({
            url: '${ctx}/reserve/reserveVenueConsData/payment',
            data: form,
            success: function (values) {
                if (values == 'success') {//
                    $(".table-chang tbody td").each(function (index) {
                        var $this = $(this);
                        if ($this.attr("status") != '0') {
                            return true;
                        }
                        if ($this.hasClass("unpayed")) {
                            $this.removeClass("unpayed");
                            $this.removeClass("access");
                            $this.attr("status", "1");//
                        }
                    });
                    alert('订单提交成功!');
                }
            }
        });
    }
    //打开售卖
    function showCofirmPay() {
        if (!checkForm()) {
            return false;
        }
        var check = true;
        var orderPrice = $("#reserveOrderPrice").val();
        $.getItems({
            url: '${ctx}/reserve/reserveMember/loadMember',
            data: {id: $("#customer").val()},
            success: function (data) {
                if (data) {
                    if (data.cartType == '1') {
                        var remainder = data.remainder == undefined ? 0 : data.remainder;
                        if (remainder < orderPrice) {
                            alert('当前会员的储值金额不足,将禁用使用储值卡');
                            check = false;
                        }
                    } else if (data.cartType == '2') {
                        var residue = data.residue == undefined ? 0 : data.residue;

                    }
                }
            }
        });

        $("#orderPrice").text(orderPrice);
        $("#collectPrice").attr("value", orderPrice);
        $('#payModal').modal('show');
        var customer = $("#customer").attr("value");
        if (customer == '' || !check) {
            $("input[type=radio][name=payType][value='1']").attr("checked", 'checked');
            $("input[type=radio][name=payType][value='5']").attr("disabled", 'disabled');
            $("input[type=radio][name=payType][value='6']").attr("disabled", 'disabled');
        } else {
            $("input[type=radio][name=payType][value='5']").attr("checked", 'checked');
            $("input[type=radio][name=payType][value='5']").removeAttr("disabled");
            $("input[type=radio][name=payType][value='6']").removeAttr("disabled");
        }
    }
    //预定
    function showCofirmOrder() {
        var form = $("#order_form").serializeArray();
        if (!checkForm()) {
            return false;
        }
        $.postItems({
            url: '${ctx}/reserve/reserveVenueConsData/reservation',
            data: form,
            success: function (values) {
                if (values == 'success') {//
                    $(".table-chang tbody td").each(function (index) {
                        var $this = $(this);
                        if ($this.attr("status") != '0') {
                            return true;
                        }
                        if ($this.hasClass("unpayed")) {
                            $this.removeClass("unpayed");
                            $this.removeClass("access");
                            $this.attr("status", "1");//
                        }
                    });
                    alert('订单预定成功!');
                }
            }
        });
    }
    $(document).ready(function () {
        //实收金额改变事件
        $("#collectPrice").on('change', function () {
            var value = $(this).val();//实收金额
            var additionalPrice = $("#additionalPrice").val();//附加费用
            var orderPrice = $("#orderPrice").text();//订单金额
            var discountPrice = orderPrice * 1 + additionalPrice * 1 - value * 1;
            $("#discountPrice").attr("value", discountPrice);

        });
        //选择客户后
        $("#customer").on('change', function () {
            var t = $(this);
            var value = t.val();
            var text = t.find("option:selected").text();
            //consMobile
            var ts = text.split('-');
            $("#consMobile").attr("value", ts[0]);
            $("#userName").attr("value", ts[1]);
            $.getItems({
                url: '${ctx}/reserve/reserveMember/loadMember',
                data: {id: value},
                success: function (data) {
                    if (data) {
                        var html = '';
                        if (data.cartType == '1') {
                            var remainder = data.remainder == undefined ? 0 : data.remainder;
                            html += '类型:储值卡;余额:' + remainder;
                            $("#memberPrice").attr("value", remainder);
                        } else if (data.cartType == '2') {
                            var residue = data.residue == undefined ? 0 : data.residue;
                            html += '类型:次卡;剩余次数:' + residue;
                            $("#memberPrice").attr("value", residue)
                        }
                        $("#memberInfo").text(html);
                    }
                }
            });
        });
        //选择场地事件
        $(".table-chang tbody td").on('click', function () {
            var t = $(this);
            if (t.attr("status") != '0') {
                return false;
            }
            t.toggleClass("unpayed");
            var html = '';
            var total = 0;
            var count = 0;
            var venueId = $("#venueId").val();
            $(".table-chang tbody td").each(function (index) {
                var $this = $(this);
                if ($this.attr("status") != '0') {
                    return true;
                }
                if ($this.hasClass("unpayed")) {//选择场地
                    /*if (count > 4) {
                     t.toggleClass("unpayed");
                     alert('您选择的场次太多,请分两次预定');
                     return false;
                     }*/
                    html += '<li>' + $(this).attr("placeholder") + ' <input type="hidden" name="venueConsList[' + count + '].reserveField.id" value="' + $this.attr("data-id") + '">\
                    <input type="hidden" name="venueConsList[' + count + '].consPrice" value="' + $this.attr("price") + '">\
                    <input type="hidden" name="venueConsList[' + count + '].consInfo" value="' + $this.attr("placeholder") + '">\
                    <input type="hidden" name="venueConsList[' + count + '].consTime" value="' + $this.attr("data-time") + '">\
                    <input type="hidden" name="venueConsList[' + count + '].reserveVenue.id" value="' + venueId + '">\
                                </li>';
                    total += (t.attr("price") * 1);
                    count++;
                }
            });
            $("#reserveOrderPrice").attr("value", total);
            $(".total-price").html('共计：<em>' + total + '</em>元');
            $(".my-sel-place").html(html);
        });
    });
</script>
</body>
</html>

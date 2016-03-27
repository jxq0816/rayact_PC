<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="formBean">
    <input type="hidden" name="token" value="${token}"/>
    <input type="hidden" name="visitorsSet.id" value="${visitorsSet.id}"/>
    <input type="hidden" name="reserveVenue.id" value="${visitorsSet.reserveVenue.id}"/>

    <div class="content">
        <div class="row">
            <!--用户信息-->
            <div class="col-lg-12 pull-left cl-mcont">
                <div class="block-flat">
                    <div class="content">
                        <!--用户信息;商品信息及数量-->
                        <table class="no-border">
                            <tbody class="no-border-y">
                            <tr>
                                <td colspan="4">
                                    <label class="radio-inline">
                                        <input type="radio" id="isMember" class="icheck" value="2" checked="checked"
                                               name="orderType"/>会员
                                    </label>
                                </td>
                                <td colspan="4"><label class="radio-inline">
                                    <input type="radio" id="nMember" class="icheck" value="1" name="orderType"/>非会员
                                </label>
                                </td>
                            </tr>
                            <tr>
                                <td>手机号或姓名:</td>
                                <td>
                                    <select style="width: 80px;" id="memberId" class="select2" name="member.id">
                                        <option value="">--请输入选择--</option>
                                        <c:forEach items="${memberList}" var="m">
                                            <option value="${m.id}">${m.mobile}-${m.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>姓名:</td>
                                <td class="text-right"><input readonly="readonly" id="userName" name="userName"
                                                              type="text"
                                                              class="form-control"/></td>

                                <td>手机:</td>
                                <td class="text-right"><input readonly="readonly" id="consMobile" name="consMobile"
                                                              type="text"
                                                              class="form-control"/></td>
                                <td>人数:</td>
                                <td>
                                    <input type="text" class="form-control number" name="collectCount" id="collectCount"
                                           value="1"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div id="order" style="display:none">
                <!--应收金额-->
                <div class="col-sm-6 col-md-6 col-lg-4 cl-mcont">
                    <div class="block-flat">
                        <div class="header">
                            <h5 class="visible-lg">应收金额</h5>
                        </div>
                        <div class="content">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>订单金额:</td>
                                    <td><input type="text" id="orderPrice" readonly="readonly"
                                               value="${visitorsSet.price}"
                                               class="form-control" name="orderPrice"/></td>
                                </tr>
                                <tr>
                                    <td>教练</td>
                                    <td>
                                        <select id="tutorId" name="tutor.id" class="select2">
                                            <option value="">如果使用了教练,请选择</option>
                                            <c:forEach items="${tutors}" var="t">
                                                <option data-price="${t.price}" value="${t.id}">${t.name}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>合计:</td>
                                    <td>
                                        <span id="orderTotal">${visitorsSet.price}</span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!--支付方式-->
                <div class="col-sm-6 col-md-6 col-lg-4 cl-mcont">
                    <div class="block-flat">
                        <div class="header">
                            <h5 class="visible-lg">支付方式</h5>
                        </div>
                        <div class="content">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>
                                        <label class="radio-inline">
                                            <input type="radio" class="icheck"
                                                   checked="checked" value="1"
                                                   name="payType"/>会员卡
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" class="icheck" value="2"
                                                   name="payType"/>现金
                                        </label>
                                        <br/>
                                        <label class="radio-inline">
                                            <input type="radio" class="icheck" value="3" name="payType"/>银行卡
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" class="icheck" value="4" name="payType"/>微信
                                        </label><br/>
                                        <label class="radio-inline">
                                            <input type="radio" class="icheck" value="5" name="payType"/>支付宝
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" class="icheck" value="6" name="payType"/>其它
                                        </label>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!--实收金额-->
                <div class="col-sm-6 col-md-6 col-lg-4 cl-mcont">
                    <div class="block-flat">
                        <div class="header">
                            <h5 class="visible-lg">实收金额</h5>
                        </div>
                        <div class="content">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>支付金额:</td>
                                    <td><input type="text" id="collectPrice" class="form-control"
                                               value="${visitorsSet.price}" name="collectPrice"/></td>
                                </tr>
                                <tr>
                                    <td>备注:</td>
                                    <td>
                                        <textarea id="remarks" class="form-control" name="remarks"></textarea>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function () {

        $("#isMember").on('ifChecked', function () {
            $("#consPrice").attr("readonly", "readonly");
            $("#userName").attr("readonly", "readonly");
            $("#consMobile").attr("readonly", "readonly");
            $("input[name='payType'][value='1']").iCheck('check');
            $("input[name='payType'][value='1']").iCheck('enable');
            $("#order").hide();//隐藏订单金额
        });

        $("#nMember").on('ifChecked', function () {
            $("#consPrice").removeAttr("readonly");
            $("#userName").removeAttr("readonly");
            $("#consMobile").removeAttr("readonly");
            $("#order").show();//显示订单金额
//            $("input[name='payType'][value='2']").attr('checked','checked');
            // alert($("input[name='payType'][value='2']").val());
            $("input[name='payType'][value='2']").iCheck('check');
            $("input[name='payType'][value='1']").iCheck('disable');
        });

        $("#memberId").on('change', function () {
            var mid = $(this).attr("value");
            var text = $(this).find("option:selected").text();
            var mobile = text.split('-')[0];
            var username = text.split('-')[1];
            $("#userName").attr("value", username);
            $("#consMobile").attr("value", mobile);
        });

        jQuery.addPrice = function (price, orderPrice, count) {
            if (price == '' || price == undefined) {
                price = 0;
            }
            if (orderPrice == '' || orderPrice == undefined) {
                orderPrice = 0;
            }
            if (count == '' || count == undefined) {
                count = 1;
            }
            var orderTotal = price * count + orderPrice * count;
            $("#orderTotal").text(orderTotal);
            $("#collectPrice").attr("value", orderTotal);
        };

        //教练
        $("#tutorId").on('change', function () {
            var price = $(this).find("option:selected").attr("data-price");
            var orderPrice = $("#orderPrice").val();
            var count = $("#collectCount").val();
            $.addPrice(price, orderPrice, count);
        });

        //数量改变事件
        $("#collectCount").on('keyup', function () {
            var t = $(this);
            var r = /^[0-9]*[1-9][0-9]*$/
            var value = t.val();
            if (value == '') {
                value = 1;
            }
            if (r.test(value) == false) {
                $("#collectCount").val("1");
            } else {
                var price = $("#tutorId").find("option:selected").attr("data-price");
                var orderPrice = $("#orderPrice").val();
                $.addPrice(price, orderPrice, value);
            }
        });

    });
</script>
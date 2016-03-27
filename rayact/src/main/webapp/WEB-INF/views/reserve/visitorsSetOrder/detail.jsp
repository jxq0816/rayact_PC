<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="detailformBean">
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
                                <td>姓名:</td>
                                <td>
                                    ${venueOrder.userName}
                                        <j:if test="${'1' eq venueOrder.orderType}">(散客)</j:if>
                                    <j:if test="${'2' eq venueOrder.orderType}">(会员)</j:if>
                                </td>
                                <td>联系方式:</td>
                                <td>${venueOrder.consMobile}</td>
                            </tr>
                            <tr>
                                <td>应收金额:</td>
                                <td>${venueOrder.orderPrice}</td>
                                <td>支付方式:</td>
                                <td>
                                    <j:if test="${'2' eq venueOrder.consType}">
                                        现金
                                    </j:if>
                                    <j:if test="${'3' eq venueOrder.consType}">
                                        银行卡
                                    </j:if>
                                    <j:if test="${'4' eq venueOrder.consType}">
                                        微信
                                    </j:if>
                                    <j:if test="${'5' eq venueOrder.consType}">
                                        支付宝
                                    </j:if>
                                    <j:if test="${'6' eq venueOrder.consType}">
                                        其它
                                    </j:if>
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
</form>
<script type="text/javascript">
    $(document).ready(function () {

        $("#isMember").on('ifChecked', function () {
            $("#consPrice").attr("readonly", "readonly");
            $("#userName").attr("readonly", "readonly");
            $("#consMobile").attr("readonly", "readonly");
            $("input[name='payType'][value='1']").iCheck('check');
            $("input[name='payType'][value='1']").iCheck('enable');
        });

        $("#nMember").on('ifChecked', function () {
            $("#consPrice").removeAttr("readonly");
            $("#userName").removeAttr("readonly");
            $("#consMobile").removeAttr("readonly");
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
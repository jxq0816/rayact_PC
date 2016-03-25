<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="settlementFormBean">
    <input type="hidden" name="id" id="cosId" value="${cos.id}"/>
    <input type="hidden" name="token" id="settlementToken" value="${token}"/>
    <input type="hidden" id="checkoutId" name="checkOutUser.id"/>

    <div class="content">
        <table class="no-border">
            <tbody class="no-border-y">
            <tr>
                <td>预定人:${cos.userName}(<j:ifelse
                        test="${'1' eq cos.consType}"><j:then>散客</j:then><j:else>会员</j:else></j:ifelse>)
                </td>
                <input type="hidden" name="itemId" value="${item.id}"/>
                <input type="hidden" name="tutorOrder.id" value="${tutorOrder.id}"/>
                <input type="hidden" name="tutorOrder.orderPrice" id="tutorPrice" value="${tutorOrder.orderPrice/2}"/>
            </tr>
            </tbody>
        </table>
    </div>
    <hr/>
    <div class="content">
        <table>
            <thead>
            <th>场地</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>是否半场</th>

            <j:if test="${!empty tutorOrder}">
                <th>
                    教练
                </th>
            </j:if>

            <th>合计</th>
            </thead>
            <tbody>
            <c:forEach items="${itemList}" var="item" varStatus="status">
                <input type="hidden" name="venueConsList[${status.index}].id" value="${item.id}"/>
                <input type="hidden" name="venueConsList[${status.index}].reserveField.id"
                       value="${item.reserveField.id}"/>
                <input type="hidden" name="venueConsList[${status.index}].reserveVenue.id"
                       value="${item.reserveVenue.id}"/>
                <input type="hidden" name="venueConsList[${status.index}].consPrice" value="${item.consPrice}"/>

                <input type="hidden" name="venueConsList[${status.index}].startTime" value="${item.startTime}"/>
                <input type="hidden" name="venueConsList[${status.index}].endTime" value="${item.endTime}"/>

                <input type="hidden" name="venueConsList[${status.index}].halfCourt" value="${item.halfCourt}"/>
                <tr>
                    <td>
                            ${item.reserveField.name}
                    </td>
                    <td>
                            ${item.startTime}
                    </td>
                    <td>
                            ${item.endTime}
                    </td>
                    <td>
                        <j:ifelse test="${'1' eq item.halfCourt}"><j:then>是</j:then><j:else>否</j:else></j:ifelse>
                    </td>

                    <j:if test="${!empty tutorOrder}">
                        <td>
                                ${tutorOrder.tutor.name}
                        </td>
                    </j:if>


                    <td>
                        <input type="text" style="width: 60px;" value="${item.consPrice}"
                               class="form-control orderPrice" readonly="readonly"
                               name="venueConsList[${status.index}].orderPrice"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <hr/>
    <div class="content">
        支付方式:
        <table class="no-border" id="payType">
            <tbody class="no-border-y">
            <tr>
                <td>
                    <label class="radio-inline">
                        <input type="radio" class="icheck"
                               <j:if test="${'1' eq cos.consType}">disabled="disabled"</j:if> value="1"
                               <j:if test="${'2' eq cos.consType}">checked="checked"</j:if> name="payType"/>会员卡
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" value="2"
                               <j:if test="${'1' eq cos.consType}">checked="checked"</j:if> name="payType"/>现金
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" value="3" name="payType"/>银行卡
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" value="4" name="payType"/>微信
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" value="5" name="payType"/>支付宝
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" value="6" name="payType"/>其它
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" value="7" name="payType"/>欠账
                    </label>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <hr/>
    <div class="content">
        <div class="row">
            <div class="col-sm-4">
                <label id="totalPrice">应收(元):<input readonly="readonly" type="text" id="shouldPrice"
                                                    class="form-control"
                                                    name="shouldPrice"/></label>
                </div>
            <div class="col-sm-4">
                <label>实收(元):<input type="text" readonly="readonly" id="orderPrice" class="form-control"
                                    name="orderPrice"/></label>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function () {
        function initData() {
            var tutorOrderPrice = 0;
            var tutorPrice = $("#tutorPrice").val();
            var tutorCount = $("#tutorCount").val();
            if (tutorPrice && tutorCount) {
                tutorOrderPrice = (tutorPrice * 1) * (tutorCount * 1);
            }
            var orderPrice = 0;
            $(".orderPrice").each(function () {
                var value = $(this).val();
                orderPrice += (value * 1);
            });
            var discountPrice = $("#discountPrice").val();
            $("#shouldPrice").val(tutorOrderPrice + orderPrice);
            if (discountPrice != undefined && discountPrice != '') {
                orderPrice = orderPrice - (discountPrice * 1);
            }
            $("#orderPrice").val(tutorOrderPrice + orderPrice);
        }
        initData();
    });
</script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="settlementDetailFormBean">
    <input type="hidden" id="id" name="id" value="${order.id}"/>
    <input type="hidden" id="token" name="token" value="${token}"/>
    <div class="content">
        <table>
            <thead>
            <th>预定人</th>
            <th>场地</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>场地类型</th>
            <th>场地费用</th>
            <j:if test="${!empty tutorOrder}">
                <th>
                    教练
                </th>
                <th>
                    教练费用
                </th>
            </j:if>
            </thead>
            <tbody>
            <c:forEach items="${itemList}" var="item" varStatus="status">
                <tr>
                    <td>
                            ${order.userName}(<j:ifelse
                            test="${'1' eq cos.consType}"><j:then>散客</j:then><j:else>会员</j:else></j:ifelse>)
                    </td>
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
                        <j:ifelse test="${'1' eq item.halfCourt}">
                            <j:then>半场</j:then>
                            <j:else>全场</j:else>
                        </j:ifelse>
                        <input type="hidden" name="venueConsList[${status.index}].halfCourt"
                               value="${item.halfCourt}"/>
                    </td>
                    <td>
                            ${item.consPrice}
                    </td>
                    <j:if test="${!empty tutorOrder}">
                        <td>
                                ${tutorOrder.tutor.name}
                        </td>
                        <td>
                                ${tutorOrder.totalPrice}
                        </td>
                    </j:if>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <hr/>
    <div class="row">
        <label for="memberCard" class="col-lg-2">支付方式:</label>
        <div class="col-lg-8">
            <label class="radio-inline">
                <input type="radio" class="icheck" id="memberCard"
                       <j:if test="${'1' eq order.consType}">disabled="disabled"</j:if> value="1"
                       <j:if test="${'2' eq order.consType}">checked="checked"</j:if> name="payType"/>会员卡
            </label>
            <label class="radio-inline">
                <input type="radio" class="icheck" value="2"
                       <j:if test="${'1' eq order.consType}">checked="checked"</j:if> name="payType"/>现金
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
                <input type="radio" class="icheck" value="6" name="payType"/>优惠券
            </label>
            <label class="radio-inline">
                <input type="radio" class="icheck" value="7" name="payType"/>打白条
            </label>
            <label class="radio-inline">
                <input type="radio" class="icheck" onchange="alert(1)" value="8" id="multiplePayRadio" name="payType"/>多方式付款
            </label>
        </div>
    </div>
    <hr/>
    <div class="row" id="multiplePay" style="display: none;">
        <div class="row">
            <div class="col-lg-3">
            </div>
            <label for="shouldPrice" class="col-lg-1">会员卡:</label>
            <div class="col-lg-1">
                <input value="" type="text"
                       class="form-control"
                       name="shouldPrice"/>
            </div>
            <label for="shouldPrice" class="col-lg-1">现金:</label>
            <div class="col-lg-1">
                <input value="" type="text"
                       class="form-control"
                       name="shouldPrice"/>
            </div>

            <label for="shouldPrice" class="col-lg-1">银行卡:</label>
            <div class="col-lg-1">
                <input value="" type="text"
                       class="form-control"
                       name="shouldPrice"/>
            </div>
            <label for="shouldPrice" class="col-lg-1">微信:</label>
            <div class="col-lg-1">
                <input value="" type="text"
                       class="form-control"
                       name="shouldPrice"/>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-3">
            </div>
            <label for="shouldPrice" class="col-lg-1">支付宝:</label>
            <div class="col-lg-1">
                <input value="" type="text"
                       class="form-control"
                       name="shouldPrice"/>
            </div>
            <label for="shouldPrice" class="col-lg-1">优惠券:</label>
            <div class="col-lg-1">
                <input value="" type="text"
                       class="form-control"
                       name="shouldPrice"/>
            </div>
            <label for="shouldPrice" class="col-lg-1">打白条:</label>
            <div class="col-lg-1">
                <input value="" type="text"
                       class="form-control"
                       name="shouldPrice"/>
            </div>
        </div>
    </div>
    <j:if test="${!empty giftList}">
        <hr/>
        <div class="content">
            赠品
            <table>
                <c:forEach items="${giftList}" var="gift">
                    <tr>
                        <td>${gift.gift.name}</td>
                        <td>${gift.num}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </j:if>
    <hr/>
    <div class="row">
        <label for="shouldPrice" class="col-lg-2">应收:</label>
        <div class="col-lg-2">
            <input readonly="readonly" value="${order.shouldPrice}" type="text"
                   id="shouldPrice" class="form-control"
                   name="shouldPrice"/>
        </div>
        <div class="col-lg-4" id="discountPriceDiv" style="display:none">
            <div class="row">
                <label class="col-lg-6" for="discountPrice">会员优惠:</label>
                <div class="col-lg-6">
                    <input type="text" id="discountPrice" value="${order.discountPrice}" onkeyup="editPrice()"
                           onafterpaste="editPrice()"
                           class="form-control " name="discountPrice"/>
                </div>
            </div>
        </div>
        <label for="consPrice" class="col-lg-2">实收: <a style="cursor: hand" id="editOrderPrice">
            <li class="fa fa-edit" onclick="changePrice()"></li>
        </a></label>
        <div class="col-lg-2">
            <input type="text" readonly="readonly" id="consPrice" value="${order.consPrice}"
                   class="form-control required number" name="orderPrice"/>
        </div>
    </div>
    <hr/>
    <div class="row" id="changePrice" style="display: none">
        <label for="authUser" class="col-lg-2">授权人:</label>
        <div class="col-lg-2">
            <sys:select id="authUser" cssClass="form-control" name=""
                        defaultLabel="请选择"
                        defaultValue=""
                        items="${authUserList}"
                        value="${cons.checkOutUser.id}"
                        itemLabel="name"
                        itemValue="id"
            ></sys:select>
        </div>
        <label for="authPassword" class="col-lg-2">授权码:</label>
        <div class="col-lg-2">
            <input id="authPassword" type="password" class="form-control"/>
        </div>
        <label>
            <button type="button" onclick="checkAuthorization()" class="btn btn-info">验证</button>
        </label>
    </div>
</form>

<script type="text/javascript">
    $(document).ready(function () {
        $("#multiplePayRadio").on('ifChecked', function () {
            $("#multiplePay").show();
        });
        $("#multiplePayRadio").on('ifUnchecked',function () {
            $("#multiplePay").hide();
        });
    })
</script>
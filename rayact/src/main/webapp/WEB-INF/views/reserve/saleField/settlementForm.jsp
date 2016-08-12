<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="settlementDetailFormBean">
    <input type="hidden" id="id" name="id" value="${order.id}"/>
    <input type="hidden" id="token" name="token" value="${token}"/>
    <div class="content">
        <table>
            <thead>
            <th>预定人</th>
            <th>手机号</th>
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
                            test="${member==null}"><j:then>散客</j:then><j:else>会员</j:else></j:ifelse>)


                    </td>
                    <td>
                            ${order.consMobile}
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
        <label class="col-lg-2">预订人备注信息:</label>
        <div class="col-lg-10">
            ${member.remarks}
        </div>
    </div>
    <hr/>
    <div class="row">
        <label class="col-lg-2">订单备注:</label>
        <div class="col-lg-10">
            <input id="remarks" value="${order.remarks}" type="text" class="form-control"/>
        </div>
    </div>
    <hr/>
    <div class="row" id="payTypeDIV">
        <label for="memberCardRadio" class="col-lg-2">支付方式:</label>
        <div class="col-lg-10">
            <label class="radio-inline">
                <input type="radio" class="icheck" id="memberCardRadio"
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
                <input type="radio" class="icheck" value="9" name="payType"/>微信（个人）
            </label>
            <label class="radio-inline">
                <input type="radio" class="icheck" value="5" name="payType"/>支付宝
            </label>
            <label class="radio-inline">
                <input type="radio" class="icheck" value="10" name="payType"/>支付宝（个人）
            </label>
            <label class="radio-inline">
                <input type="radio" class="icheck" value="6" name="payType"/>优惠券
            </label>
            <label class="radio-inline">
                <input type="radio" class="icheck" value="11" name="payType"/>转账
            </label>
            <label class="radio-inline">
                <input type="radio" class="icheck" value="8" id="multiplePayRadio" name="payType"/>多方式
            </label>
        </div>
    </div>

    <div class="row" id="multiplePay" style="display: none;">
        <hr/>
        <div class="row">
            <div class="col-lg-1">
            </div>
            <label for="memberCardInput" class="col-lg-1">会员卡:</label>
            <div class="col-lg-1">
                <input id="memberCardInput" value="0" type="text" class="form-control"/>
            </div>
            <label for="cashInput" class="col-lg-1">现金:</label>
            <div class="col-lg-1">
                <input id="cashInput" value="0" type="text" class="form-control"/>
            </div>

            <label for="bankCardInput" class="col-lg-1">银行卡:</label>
            <div class="col-lg-1">
                <input id="bankCardInput" value="0" type="text" class="form-control"/>
            </div>
            <label for="weiXinInput" class="col-lg-1">微信:</label>
            <div class="col-lg-1">
                <input id="weiXinInput" value="0" type="text" class="form-control"/>
            </div>
            <label for="weiXinPersonalInput" class="col-lg-1">微信(个人):</label>
            <div class="col-lg-1">
                <input id="weiXinPersonalInput" value="0" type="text" class="form-control"/>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-1">
            </div>
            <label for="aliPayInput" class="col-lg-1">支付宝:</label>
            <div class="col-lg-1">
                <input id="aliPayInput" type="text" class="form-control" value="0"/>
            </div>
            <label for="aliPayPersonalInput" class="col-lg-1">支付宝（个人）:</label>
            <div class="col-lg-1">
                <input id="aliPayPersonalInput" type="text" class="form-control" value="0"/>
            </div>
            <label for="couponInput" class="col-lg-1">优惠券:</label>
            <div class="col-lg-1">
                <input id="couponInput" type="text" class="form-control" value="0"/>
            </div>
            <%-- <label for="owningInput" class="col-lg-1">打白条:</label>
             <div class="col-lg-1">
                 <input id="owningInput" type="text" class="form-control" value="0"/>
             </div>--%>
        </div>
    </div>
    <j:if test="${!empty giftList}">
        <hr/>
        <div class="row">
            <label class="col-lg-1" for="gift">赠品:</label>
            <div id="gift">
                <c:forEach items="${giftList}" var="gift">
                    <div class="col-lg-2">${gift.gift.name} * ${gift.num}</div>
                </c:forEach>
            </div>
        </div>
    </j:if>
    <div class="row">
        <hr/>
        <label for="shouldPrice" class="col-lg-1 col-sm-2">应收:</label>
        <div class="col-lg-1 col-sm-2">
            <input readonly="readonly" value="${order.shouldPrice}" type="text"
                   id="shouldPrice" class="form-control"
                   name="shouldPrice"/>
        </div>
        <div class="col-lg-4 col-sm-4" id="discountPriceDiv" style="display:none">
            <div class="row">
                <label class="col-lg-3 col-sm-3" for="discountPrice">优惠:</label>
                <div class="col-lg-6 col-sm-6">
                    <input type="text" id="discountPrice" placeholder="请输入优惠金额后，点击确认优惠" value="${order.discountPrice}"
                           onblur="editPrice()"
                           onafterpaste="editPrice()"
                           class="form-control " name="discountPrice"/>
                </div>
                <div class="col-lg-3 col-sm-3">
                    <button type="button" onclick="editPrice()" class="btn btn-info">确认优惠</button>
                </div>
            </div>
        </div>
        <label for="consPrice" class="col-lg-1 col-sm-2">实收: <a style="cursor: hand" id="editOrderPrice">
            <li class="fa fa-edit" onclick="changePrice()"></li>
        </a></label>
        <div class="col-lg-1 col-sm-2">
            <input type="text" readonly="readonly" id="consPrice" value="${order.consPrice}"
                   class="form-control required number" name="consPrice"/>
        </div>
        <label for="shouldPrice" class="col-lg-2">会员当前余额:</label>
        <div class="col-lg-1 col-sm-2">
            <input readonly="readonly" value="${member.remainder}" type="text"
                   class="form-control"/>
        </div>
    </div>
    <div class="row" id="changePrice" style="display: none">
        <hr/>
        <label for="authUser" class="col-lg-1 col-sm-2">授权人:</label>
        <div class="col-lg-1 col-sm-2">
            <sys:select id="authUser" cssClass="form-control" name=""
                        defaultLabel="请选择"
                        defaultValue=""
                        items="${authUserList}"
                        value="${cons.checkOutUser.id}"
                        itemLabel="name"
                        itemValue="id"
            ></sys:select>
        </div>
        <label for="authPassword" class="col-lg-1 col-sm-2">授权码:</label>
        <div class="col-lg-1 col-sm-2">
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
        $("#multiplePayRadio").on('ifUnchecked', function () {
            $("#multiplePay").hide();
        });
    })
</script>
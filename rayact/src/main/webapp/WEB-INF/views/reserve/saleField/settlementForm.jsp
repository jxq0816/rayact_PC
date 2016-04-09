<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="settlementDetailFormBean">
    <input type="hidden" id="id" name="id" value="${order.id}"/>
    <input type="hidden" id="token" name="token" value="${token}" />
    <div class="content">
        <table class="no-border">
            <tbody class="no-border-y">
            <tr>
                <td>预定人:${order.userName}(<j:ifelse
                        test="${'1' eq cos.consType}"><j:then>散客</j:then><j:else>会员</j:else></j:ifelse>)
                </td>
               <%-- <td>
                    <input type="hidden" name="itemId" value="${item.id}"/>
                    <input type="hidden" name="tutorOrder.id" value="${tutorOrder.id}"/>
                    <input type="hidden" name="tutorOrder.orderPrice" id="tutorPrice" value="${tutorOrder.orderPrice}"/>
                </td>--%>
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
                        <input type="hidden" name="venueConsList[${status.index}].halfCourt"
                               value="${item.halfCourt}"/>
                    </td>
                    <j:if test="${!empty tutorOrder}">
                        <td>
                                ${tutorOrder.tutor.name}
                        </td>
                    </j:if>
                    <td>
                        <input type="text" style="width: 60px;" value="${item.consPrice}"
                               class="form-control" readonly="readonly"
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
        <table class="no-border" id="payType2">
            <tbody class="no-border-y">
            <tr>
                <td>
                    <label class="radio-inline">
                        <input type="radio" class="icheck"
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
                        <input type="radio" class="icheck" value="6" name="payType"/>其它
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" value="7" name="payType"/>打白条
                    </label>
                </td>
            </tr>
            </tbody>
        </table>
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
                <input type="text" readonly="readonly" id="consPrice" value="${order.orderPrice}"
                                    class="form-control required number" name="orderPrice"/>
                </div>
                    <%-- --%>
           <%-- <div class="col-lg-2">
                <button type="button" onclick="changePrice()" class="btn btn-info">修改价格</button>
            </div>--%>
        </div>
        <hr/>
        <div class="row" id="button_userPwd" style="display: none">
            <label for="authUser" class="col-lg-2">授权人:</label>
            <div class="col-lg-2">
                <sys:select id="authUser" cssClass="form-control" name=""
                            items="${authUserList}"
                            value="${cons.checkOutUser.id}"
                            itemLabel="name"
                            itemValue="id"
                ></sys:select>
            </div>
           <%-- <input id="checkOutUserId" name="checkOutUserId" value=""/>--%>
            <label for="authPassword" class="col-lg-2">授权码:</label>
            <div class="col-lg-2">
                <input id="authPassword" type="password" class="form-control"/>
            </div>
            <label>
                <button type="button" onclick="checkAuthorization()" class="btn btn-info">验证</button>
            </label>
        </div>
</form>
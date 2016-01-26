<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="detailsFormBean">
    <input type="hidden" name="id" value="${cos.id}"/>

    <div class="content">
        <table class="no-border">
            <tbody class="no-border-y">
            <tr>
                <td>预定人:${cos.userName}(<j:ifelse
                        test="${'1' eq cos.consType}"><j:then>散客</j:then><j:else>会员</j:else></j:ifelse>)
                </td>
                <td>
                    <input type="hidden" name="itemId" value="${item.id}"/>
                </td>
                <j:if test="${!empty tutorOrderList}">
                    <c:forEach items="${tutorOrderList}" var="tutorOrder">
                        <td>${tutorOrder.tutor.name}(教练):</td>
                        <td>${tutorOrder.totalPrice}</td>
                    </c:forEach>
                </j:if>
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
            <th>频率</th>
            <th>是否半场</th>
            <th>价格</th>
            </thead>
            <tbody>
            <c:forEach items="${itemList}" var="item" varStatus="status">
                <input type="hidden" name="venueConsList[${status.index}].id" value="${item.id}"/>
                <input type="hidden" name="venueConsList[${status.index}].reserveField.id"
                       value="${item.reserveField.id}"/>
                <input type="hidden" name="venueConsList[${status.index}].reserveVenue.id"
                       value="${item.reserveVenue.id}"/>
                <input type="hidden" name="venueConsList[${status.index}].consPrice" value="${item.consPrice}"/>
                <tr>
                    <td>
                            ${item.reserveField.name}
                    </td>
                    <td>
                        <select readonly="" style="width: 80px;" class="select2" id="startTime"
                                name="venueConsList[${status.index}].startTime">
                            <c:forEach items="${times}" var="t">
                                <option
                                        <j:if test="${t eq item.startTime}">selected="selected"</j:if>
                                        value="${t}">${t}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select class="select2" style="width: 80px;" id="endTime"
                                name="venueConsList[${status.index}].endTime">
                            <c:forEach items="${times}" var="t">
                                <option
                                        <j:if test="${t eq item.endTime}">selected="selected"</j:if>
                                        value="${t}">${t}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <j:if test="${'1' eq item.frequency}">单次</j:if>
                        <j:if test="${'2' eq item.frequency}">每天</j:if>
                        <j:if test="${'3' eq item.frequency}">每周(${item.consWeek})</j:if>
                    </td>
                    <td>
                        <j:ifelse test="${'1' eq item.halfCourt}"><j:then>是</j:then><j:else>否</j:else></j:ifelse>
                    </td>
                    <td>
                        <input type="text" style="width: 60px;" value="${item.consPrice}" class="form-control"
                               name="venueConsList[${status.index}].orderPrice"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <hr/>
    <div class="content">
        备注:
        <textarea name="remarks" readonly="readonly" rows="4" class="form-control">${cos.remarks}</textarea>
    </div>
    <%--<div class="content">
        支付方式:
        <table class="no-border">
            <tbody class="no-border-y">
            <tr>
                <td>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" <j:if test="${'1' eq cos.consType}">disabled="disabled"</j:if>  value="1" <j:if test="${'2' eq cos.consType}">checked="checked"</j:if> name="payType"/>会员卡
                    </label>
                    <label class="radio-inline">
                        <input type="radio" class="icheck" value="2" <j:if test="${'1' eq cos.consType}">checked="checked"</j:if>  name="payType"/>现金
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
                </td>
            </tr>
            </tbody>
        </table>
    </div>--%>
</form>
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
                        <td>教练:</td><td>${tutorOrder.tutor.name}</td>
                        <td>教练费用:</td><td>${tutorOrder.totalPrice/2}</td>
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
            <th>应收(场地费+教练费)</th>
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
                        <j:if test="${'1' eq item.frequency}">单次</j:if>
                        <j:if test="${'2' eq item.frequency}">每天</j:if>
                        <j:if test="${'3' eq item.frequency}">每周(${item.consWeek})</j:if>
                    </td>
                    <td>
                        <j:ifelse test="${'1' eq item.halfCourt}"><j:then>是</j:then><j:else>否</j:else></j:ifelse>
                    </td>
                    <td>
                            ${item.consPrice}
                    </td>
                </tr>
            </c:forEach>
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
    <div class="content">
        备注:${cos.remarks}
    </div>
</form>
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
                    手机号：${cos.consMobile}
                </td>
                <j:if test="${!empty tutorOrderList}">
                    <c:forEach items="${tutorOrderList}" var="tutorOrder">
                        <td>教练:</td><td>${tutorOrder.tutor.name}</td>
                        <td>教练费用:</td><td>${tutorOrder.totalPrice}</td>
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
            <th>场地类型</th>
            <th>场地费用</th>
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
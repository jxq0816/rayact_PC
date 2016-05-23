<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<div class="content">
    <div class="table-responsive">
        <table>
            <thead>
            <tr>
                <th class="text-center">支付方式</th>
                <th class="text-center">支付金额</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="payment">
                <tr>
                    <td class="text-center">${fns:getPayType(payment.payType)}</td>
                    <td class="text-center">${payment.paymentAmount}元</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

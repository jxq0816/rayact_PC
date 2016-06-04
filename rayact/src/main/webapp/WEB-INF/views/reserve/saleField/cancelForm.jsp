<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="cancelFormBean">
    <input type="hidden" name="token" value="${token}"/>
    <div class="content">
        <table class="no-border">
            <tbody class="no-border-y">
            <tr>
                <td>预定人</td>
                <td>${cos.userName}
                    <input type="hidden" name="itemId" value="${item.id}"/>
                </td>
            </tr>
            <tr>
                <td>时间</td>
                <td>
                    ${startTime}  至 ${endTime}
                </td>
            </tr>
            <j:if test="${!empty tutorOrder}">
                <tr>
                    <input type="hidden" name="tutorOrderId" value="${tutorOrder.id}"/>
                    <td>教练:</td>
                    <td>
                        ${tutorOrder.tutor.name}
                    </td>
                    <td colspan="5"></td>
                </tr>
            </j:if>
            </tbody>
        </table>
    </div>
</form>
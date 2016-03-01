<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<div class="row">
    <div class="content">
        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>时间</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${availableTimes}" var="time" varStatus="obj">
                    <tr>
                        <c:if test="${obj.count%2 == '0'}">
                            <td>${time}</td>
                        </c:if>
                        <c:if test="${obj.count%2 == '1'}">
                            <td>${time}</td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
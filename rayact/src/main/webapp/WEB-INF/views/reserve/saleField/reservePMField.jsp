<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<table class="table-chang" style="width:auto;">
    <thead>
    <%--时刻--%>
    <tr>
        <th>下午</th>
        <c:forEach items="${timesPM}" var="t">
            <th style="background-color: #fff;">${t}</th>
        </c:forEach>
    </tr>
    <%--时刻--%>
    </thead>
    <tbody>
    <%-- 遍历所有全场的场地开始--%>
    <c:forEach items="${venueFieldPriceListPM}" var="fullField" varStatus="status">
        <tr>
                <%-- 纵坐标：场地名称--%>
            <th><span>${fullField.fieldName}</span></th>
                <%-- 横坐标：时间--%>
            <c:forEach items="${timesPM}" var="t">
                <c:set var="status" value="0"/>
                <c:set var="itemId" value="0"/>

                <%--遍历单个场地的时间、价格组成的Jason 获得状态--%>
                <c:forEach items="${fullField.timePriceList}" var="tp">
                    <%--场地jason的时间与横坐标的时间一致 --%>
                    <j:if test="${t eq tp.time}">
                        <%--设置单个场地 时间T 的状态--%>
                        <c:set var="status" value="${tp.status}"/>
                        <c:set var="price" value="${tp.price}"/>
                        <c:set var="username" value="${tp.userName}"/>
                        <c:set var="itemId" value="${tp.consItem.id}"/>
                        <%-- A场地 B时间 的状态 结束--%>
                    </j:if>
                </c:forEach>
                <%-- 遍历左半场的时间、价格组成的Jason获得 左半场 时间t 的状态--%>
                <c:forEach items="${fullField.fieldPriceLeft.timePriceList}"
                           var="leftTimePrice">
                    <%--场地的时间与横坐标的时间一致 --%>
                    <j:if test="${t eq leftTimePrice.time}">
                        <%--设置左半场 场地 时间T 的状态--%>
                        <c:set var="leftStatus"
                               value="${leftTimePrice.status}"/>
                        <c:set var="leftPrice"
                               value="${leftTimePrice.price}"/>
                        <c:set var="leftUsername"
                               value="${leftTimePrice.userName}"/>
                        <c:set var="leftItemId"
                               value="${leftTimePrice.consItem.id}"/>
                        <%-- 左半场 场地 时间T 的状态结束--%>
                    </j:if>
                </c:forEach>
                <%-- 遍历右半场的时间、价格组成的Jason获得 右半场 时间t 的状态--%>
                <c:forEach
                        items="${fullField.fieldPriceRight.timePriceList}"
                        var="rightTimePrice">
                    <%--场地的时间与横坐标的时间一致 --%>
                    <j:if test="${t eq rightTimePrice.time}">
                        <%--设置右半场 场地 时间T 的状态--%>
                        <c:set var="rightStatus"
                               value="${rightTimePrice.status}"/>
                        <c:set var="rightPrice"
                               value="${rightTimePrice.price}"/>
                        <c:set var="rightUsername"
                               value="${rightTimePrice.userName}"/>
                        <c:set var="rightItemId"
                               value="${rightTimePrice.consItem.id}"/>
                        <%-- 右半场 场地 时间T 的状态结束--%>
                    </j:if>
                </c:forEach>
                <%--设置左半场的class--%>
                <j:if test="${'0' eq leftStatus}">
                    <c:set var="leftClass" value="reserveTd access"/>
                </j:if>
                <j:if test="${'1' eq leftStatus}">
                    <c:set var="leftClass" value="reserveTd"/>
                </j:if>
                <j:if test="${'1' eq status}">
                    <c:set var="leftClass" value="fullFieldHasReserved"/> <%--全场已经预订，左半场不可再预订--%>
                </j:if>
                <j:if test="${'4' eq leftStatus}">
                    <c:set var="leftClass" value="reserveTd red"/>
                </j:if>
                <%--设置左半场的class end--%>
                <%--设置全场的class--%>
                <j:if test="${'0' eq status}">
                    <c:set var="midClass" value="reserveTd access"/>
                </j:if>
                <j:if test="${'1' eq status}">
                    <c:set var="midClass" value="reserveTd"/>
                </j:if>
                <j:if test="${'1' eq leftStatus}">
                    <c:set var="midClass" value="halfFieldHasReserved"/><%--左半场已经预订，全场不可再预订--%>
                </j:if>
                <j:if test="${'1' eq rightStatus}">
                    <c:set var="midClass" value="halfFieldHasReserved"/><%--右半场已经预订，全场不可再预订--%>
                </j:if>
                <j:if test="${'4' eq status}">
                    <c:set var="midClass" value="reserveTd red"/>
                </j:if>
                <%--设置全场的class end--%>
                <%--设置右半场的class--%>
                <j:if test="${'0' eq rightStatus}">
                    <c:set var="rightClass" value="reserveTd access"/>
                </j:if>
                <j:if test="${'1' eq rightStatus}">
                    <c:set var="rightClass" value="reserveTd"/>
                </j:if>
                <j:if test="${'1' eq status}">
                    <c:set var="rightClass" value="fullFieldHasReserved"/> <%--全场已经预订，右半场不可再预订--%>
                </j:if>
                <j:if test="${'4' eq rightStatus}">
                    <c:set var="rightClass" value="reserveTd red"/>
                </j:if>
                <%--设置右半场的class end--%>

                <%-- 场地 B时间 的状态展示--%>
                <c:choose>
                    <c:when test="${fullField.haveHalfCourt eq '1'}">
                        <td>
                            <table class="table-half">
                                <tr><%--遍历左半场 场地的时间、价格组成的Jason--%>

                                    <td style="color: #000;" status="${leftStatus}"
                                        data-item="${leftItemId}"
                                        class="${leftClass}"
                                        data-price="${price}"
                                        data-field="${fullField.fieldPriceLeft.fieldId}"
                                        data-isHalfCourt="1"
                                        data-time="${t}" title="${leftUsername}">
                                    </td>

                                    <td style="color: #000;" status="${status}"
                                        data-item="${itemId}"
                                        class="${midClass}"
                                        data-price="${price}"
                                        data-field="${fullField.fieldId}"
                                        data-isHalfCourt="0"
                                        data-time="${t}" title="${username}">
                                    </td>
                                    <td style="color: #000;" status="${rightStatus}"
                                        data-item="${rightItemId}"
                                        class="${rightClass}"
                                        data-price="${rightPrice}"
                                        data-field="${fullField.fieldPriceRight.fieldId}"
                                        data-isHalfCourt="1"
                                        data-time="${t}" title="${rightUsername}">
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <j:if test="${'0' eq status}">
                            <c:set var="fullClass" value="reserveTd access"/>
                        </j:if>
                        <j:if test="${'1' eq status}">
                            <c:set var="fullClass" value="reserveTd"/>
                        </j:if>
                        <j:if test="${'4' eq status}">
                            <c:set var="fullClass" value="reserveTd red"/>
                        </j:if>
                        <td style="color: #000;" status="${status}" data-item="${itemId}"
                            class="${fullClass}"
                            data-price="${price}"
                            data-field="${fullField.fieldId}"
                            data-isHalfCourt="0"
                            data-time="${t}" title="${username}">

                        </td>
                    </c:otherwise>
                </c:choose>

                <%-- A场地 B时间 的状态展示 结束--%>
            </c:forEach>
                <%-- 横坐标：时间 结束--%>
        </tr>
        <%-- 纵坐标：场地 结束--%>
    </c:forEach>
    <%-- 遍历所有全场 场地结束--%>
    </tbody>
</table>

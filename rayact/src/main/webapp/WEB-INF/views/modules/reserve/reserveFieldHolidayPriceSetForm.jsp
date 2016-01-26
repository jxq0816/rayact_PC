<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<tr>
    <td rowspan="3" valign="top"><a data="${priceSet.week}" style="color: red"
                                    style="width: 25px;"
                                    title="点击,设计全局数值" href="#"
                                    class="weekPriceTable">${holidayPriceDate}</a>
    </td>
    <td valign="top">散客</td>
    <input type="hidden" name="fieldPriceSetList[${index}].consType" value="1"/>
    <c:forEach items="${times}" var="t" varStatus="priceSetStatus">
        <td>
            <input type="hidden"
                   name="fieldPriceSetList[${index}].timePriceList[${priceSetStatus.index}].time"
                   value="${t}"/>
            <input type="text" data-time="${t}"
                   name="fieldPriceSetList[${index}].timePriceList[${priceSetStatus.index}].price"
                   class="number"
                   style="width: 25px;"/>
        </td>
    </c:forEach>
</tr>

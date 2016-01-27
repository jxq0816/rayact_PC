<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="false" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css class" %>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css style" %>
<%@ attribute name="items" type="java.util.Collection" required="false" description="数据源" %>
<%@ attribute name="itemLabel" type="java.lang.String" required="false" description="label" %>
<%@ attribute name="itemValue" type="java.lang.String" required="false" description="value" %>
<%@ attribute name="defaultLabel" type="java.lang.String" required="false" description="默认说明" %>
<%@ attribute name="defaultValue" type="java.lang.String" required="false" description="默认说明的值" %>
<%@ attribute name="dataRule" type="java.lang.String" required="false" description="值校验" %>

<j:if test="${!empty cssStyle}">
    <j:set name="cssStyle" value="style='${cssStyle}'"></j:set>
</j:if>
<j:if test="${empty id}">
    <j:set name="id" value="${name}"></j:set>
</j:if>

<select id="${id}" data-rule="${dataRule}" name="${name}" class="${cssClass}" ${cssStyle}>
    <j:if test="${!empty defaultLabel}">
        <option value="${defaultValue}">${defaultLabel}</option>
    </j:if>
    <c:forEach items="${items}" var="item">
        <j:ifelse test="${value eq fns:getGetFieldValue(item,itemValue)}">
            <j:then>
                <option selected value="${fns:getGetFieldValue(item,itemValue)}">${fns:getGetFieldValue(item,itemLabel)}</option>
            </j:then>
            <j:else>
                <option value="${fns:getGetFieldValue(item,itemValue)}">${fns:getGetFieldValue(item,itemLabel)}</option>
            </j:else>
        </j:ifelse>
    </c:forEach>
</select>
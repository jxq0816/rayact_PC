<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="false" description="编号" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）" %>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）" %>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css class" %>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css style" %>
<%@ attribute name="items" type="java.util.Collection" required="false" description="数据源" %>
<%@ attribute name="itemLabel" type="java.lang.String" required="false" description="label" %>
<%@ attribute name="itemValue" type="java.lang.String" required="false" description="value" %>
<%@ attribute name="defaultLabel" type="java.lang.String" required="false" description="默认说明" %>
<%@ attribute name="defaultValue" type="java.lang.String" required="false" description="默认说明的值" %>
<%@ attribute name="dataRule" type="java.lang.String" required="false" description="值校验" %>
<%@ attribute name="rowIndex" type="java.lang.Integer" required="false" description="一行多少个" %>

<j:if test="${!empty cssStyle}">
    <j:set name="cssStyle" value="style='${cssStyle}'"></j:set>
</j:if>
<j:if test="${empty id}">
    <j:set name="id" value="${name}"></j:set>
</j:if>

<c:forEach items="${items}" var="item" varStatus="status">
    <j:if test="${!empty rowIndex && status.index/rowIndex==1 && status.index!=0}">
        <br/>
    </j:if>
    <label class="radio-inline">
        <input type="radio"
               <j:if test="${value eq fns:getGetFieldValue(item,itemValue)}">checked="checked"</j:if>
               value="${fns:getGetFieldValue(item,itemValue)}" data-rule="${dataRule}" name="${name}"
               class="${cssClass}" ${cssStyle}/>
            ${fns:getGetFieldValue(item,itemLabel)}
    </label>
</c:forEach>
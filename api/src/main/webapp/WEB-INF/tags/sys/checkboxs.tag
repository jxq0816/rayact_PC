<%@ tag import="java.util.List" %>
<%@ tag import="com.google.common.collect.Lists" %>
<%@ tag import="com.fitfir.think.runtime.json.JsonUtils" %>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="false" description="编号" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）" %>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css class" %>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css style" %>
<%@ attribute name="items" type="java.util.Collection" required="false" description="数据源" %>
<%@ attribute name="checkValues" type="java.lang.Object" required="false" description="被选中的项" %>
<%@ attribute name="itemLabel" type="java.lang.String" required="false" description="label" %>
<%@ attribute name="itemValue" type="java.lang.String" required="false" description="value" %>
<%@ attribute name="valueType" type="java.lang.String" required="false" description="value类型（List：类型,Json类型）" %>
<%@ attribute name="dataRule" type="java.lang.String" required="false" description="值校验" %>
<%
    List<String> checkValueList = Lists.newArrayList();
    if (valueType == null) valueType = "list";
    if ("json".equals(valueType.toLowerCase())) {
        if (checkValues != null) {
            checkValueList = JsonUtils.readBeanByJson(checkValues.toString(), List.class, String.class);
        }
    } else {
        checkValueList = (List<String>) checkValues;
    }
    request.setAttribute("checkValueList", checkValueList);
%>
<j:if test="${!empty cssStyle}">
    <j:set name="cssStyle" value="style='${cssStyle}'"></j:set>
</j:if>
<div class="checkboxTag" id="checkbox_tag_${id}">
    <c:forEach items="${items}" var="item" varStatus="status">
        <j:ifelse test="${fn:contains(requestScope.checkValueList,fns:getGetFieldValue(item,itemValue))}">
            <j:then>
                <li data-label-width="16%" <j:if test="${status.index>8}">class="hide" </j:if>>
                    <input data-rule="${dataRule}" name="${name}" checked type="checkbox"
                           class="${cssClass}" ${cssStyle}
                           value="${fns:getGetFieldValue(item,itemValue)}"/>${fns:getGetFieldValue(item,itemLabel)}</li>
            </j:then>
            <j:else>
                <li data-label-width="16%"
                    <j:if test="${status.index>8}">class="hide"  </j:if> >
                    <input data-rule="${dataRule}" name="${name}" type="checkbox" class="${cssClass}" ${cssStyle}
                           value="${fns:getGetFieldValue(item,itemValue)}"/>${fns:getGetFieldValue(item,itemLabel)}
                </li>
            </j:else>

        </j:ifelse>
    </c:forEach>
</div>
<br/>
<%--<div>
<a style="color: red;" href="javascript:showMore('checkbox_tag_${id}')">更多</a>
</div>--%>
<script type="text/javascript">
    function showMore(tagId) {
        $("#" + tagId + " .hide").toggle();
    }
</script>
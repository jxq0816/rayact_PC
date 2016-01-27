<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="content" type="java.lang.String" required="true" description="消息内容" %>
<%@ attribute name="type" type="java.lang.String" description="消息类型：info、success、warning、error、loading" %>
<c:if test="${not empty content}">
    <c:if test="${not empty type}"><c:set var="ctype" value="${type}"/></c:if>
    <c:if test="${empty type}">
        <c:set var="ctype"
               value="${fn:indexOf(content,'失败') eq -1?'fa fa-check sign':'fa fa-times-circle sign'}"/>
        <c:set var="alertType"
               value="${fn:indexOf(content,'失败') eq -1?'success':'danger'}"/>
    </c:if>
    <div id="bootAlert" class="alert alert-${alertType}">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        <i class="${ctype}"></i><strong id="messageBox">${content}</strong>
    </div>
</c:if>
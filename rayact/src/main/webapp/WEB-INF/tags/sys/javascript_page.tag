<%@ tag import="com.bra.common.config.Global" %>
<%@ tag import="com.bra.common.utils.StringUtils" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="p" type="com.bra.common.persistence.Page"
              required="true" %>
<%@ attribute name="message" type="java.lang.String" required="false" %>
<%@ attribute name="bootstrap3" type="java.lang.Boolean" required="false" %>
<%@ attribute name="formId" type="java.lang.String" required="false" %>
<%@ attribute name="pageNoTag" type="java.lang.String" required="false" %>
<%@ attribute name="pageSizeTag" type="java.lang.String" required="false" %>
<%@ attribute name="action" type="java.lang.String" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    int paginationSize = Integer.valueOf(Global.getConfig("page.pageSize"));
    int current = p.getPageNo();
    int begin = Math.max(1, current - paginationSize / 2);
    int end = Math.min(begin + (paginationSize - 1), p.getTotalPage());
    request.setAttribute("current", current);
    request.setAttribute("begin", begin);
    request.setAttribute("end", end);
    int first = 1;
    int slider = 1;
    String funcName = "page";
    if (bootstrap3 == null) {
        bootstrap3 = false;
    }
    if (StringUtils.isBlank(pageNoTag)) {
        pageNoTag = "pageNo";
    }
    if (StringUtils.isBlank(pageSizeTag)) {
        pageSizeTag = "pageSize";
    }
%>
<%
    if (p.getTotalPage() <= 1) {
        return;
    }
    StringBuilder sb = new StringBuilder("<ul class=\"pagination\">");

    if (p.getPageNo() == first) {// 如果是首页
        sb.append("<li class=\"disabled\"><a href=\"javascript:\">&#171; 上一页</a></li>\n");
    } else {
        sb.append("<li><a href=\"javascript:\" onclick=\"" + funcName
                + "(" + p.getPrev() + "," + p.getPageSize()
                + ");\">&#171; 上一页</a></li>\n");
    }

    if (begin > first) {
        int i = 0;
        for (i = first; i < first + slider && i < begin; i++) {
            sb.append("<li><a href=\"javascript:\" onclick=\""
                    + funcName + "(" + i + "," + p.getPageSize()
                    + ");\">" + (i + 1 - first) + "</a></li>\n");
        }
        if (i < begin) {
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
        }
    }

    for (int i = begin; i <= end; i++) {
        if (i == p.getPageNo()) {
            sb.append("<li class=\"active\"><a href=\"javascript:\">"
                    + (i + 1 - first) + "</a></li>\n");
        } else {
            sb.append("<li><a href=\"javascript:\" onclick=\""
                    + funcName + "(" + i + "," + p.getPageSize()
                    + ");\">" + (i + 1 - first) + "</a></li>\n");
        }
    }

    if (p.getTotalPage() - end > slider) {
        sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
        end = p.getTotalPage() - slider;
    }

    for (int i = end + 1; i <= p.getTotalPage(); i++) {
        sb.append("<li><a href=\"javascript:\" onclick=\"" + funcName
                + "(" + i + "," + p.getPageSize() + ");\">"
                + (i + 1 - first) + "</a></li>\n");
    }

    if (p.getPageNo() == p.getTotalPage()) {
        sb.append("<li class=\"disabled\"><a href=\"javascript:\">下一页 &#187;</a></li>\n");
    } else {
        sb.append("<li><a href=\"javascript:\" onclick=\"" + funcName
                + "(" + p.getNext() + "," + p.getPageSize()
                + ");\">" + "下一页 &#187;</a></li>\n");
    }

    sb.append("<li class=\"disabled controls\"><a href=\"javascript:\">当前 ");
    sb.append(p.getPageNo());
    sb.append(p.getPageSize()+" 条，");
    sb.append("共 " + p.getTotalPage() + " 条"
            + (message != null ? message : "") + "</a><li>\n");
    if (bootstrap3) {
        sb.insert(0, "<ul class=\"pagination\">\n").append("</ul>\n");
    } else {
        sb.insert(0, "<ul>").append("</ul>\n");
    }

    sb.append("<div style=\"clear:both;\"></div>");
    sb.append("</ul>");
    //sb.insert(0,"<div class=\"page\">\n").append("</div>\n");
    out.println(sb.toString());
%>
<%
    if (
            StringUtils.isNotBlank(formId)) {
%>
<input id="<%=pageNoTag%>" name="<%=pageNoTag%>" type="hidden" value="<%=p.getPageNo()%>"/>
<input id="<%=pageSizeTag%>" name="<%=pageSizeTag%>" type="hidden" value="<%=p.getPageSize()%>"/>
<script type="text/javascript">
    function page(n, s) {
        $("#<%=pageNoTag%>").val(n);
        $("#<%=pageSizeTag%>").val(s);
        <%
            if(StringUtils.isBlank(action)){
            %>
        $("#<%=formId%>").submit();
        <%
            }else{
            %>
        $("#<%=formId%>").attr("action", "<%=action%>");
        $("#<%=formId%>").submit();
        <%
            }
        %>

        return false;
    }

</script>
<%
    }
%>
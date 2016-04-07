<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="cl-sidebar">
    <%--<div class="cl-toggle"><i class="fa fa-bars"></i></div>--%>
    <div class="cl-navblock">
        <div class="menu-space">
            <div class="content">
               <%-- <div class="side-user">
                    <div class="info">
                        <a href="#">四得体育</a>
                    </div>
                </div>--%>
                <ul class="cl-vnavigation">
                    <c:forEach items="${fns:getAuthByUser(fns:getUser())}" var="auth">
                        <li class="">
                            <a href="#">
                            <span style="margin-right:5px"><img style="width:30px" src="${ctxStatic}/cleanzone/images/sidebar/${auth.code}.png"></img></span>
                            <span>${auth.name}</span>
                            </a>
                            <ul class="sub-menu">
                                <c:forEach items="${auth.authorityList}" var="a">
                                    <li <j:if test="${a.code eq param.action}">class="active"</j:if> ><a href="${ctx}${a.href}">${a.name}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
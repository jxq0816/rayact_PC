<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="cl-sidebar">
    <%--<div class="cl-toggle"><i class="fa fa-bars"></i></div>--%>
    <div class="row">
        <div class="col-lg-11">
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
                                <li>
                                    <a href="#">
                                        <div class="row">
                                            <div class="col-lg-2">
                                                <img style="width:28px"
                                                     src="${ctxStatic}/cleanzone/images/sidebar/${auth.code}.png"/>
                                            </div>
                                            <div class="col-lg-6">${auth.name}</div>
                                        </div>
                                    </a>
                                    <ul class="sub-menu">
                                        <c:forEach items="${auth.authorityList}" var="a">
                                            <li
                                                    <j:if test="${a.code eq param.action}">class="active"</j:if> ><a
                                                    href="${ctx}${a.href}">${a.name}</a></li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-1" style="float: right">
            <img src="${ctxStatic}/cleanzone/images/triangle.png"/>
        </div>
    </div>
</div>
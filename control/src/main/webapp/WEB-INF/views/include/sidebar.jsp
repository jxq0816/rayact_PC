<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="cl-sidebar" data-position="right" data-step="1" data-intro="<strong>Fixed Sidebar</strong> <br/> It adjust to your needs." >
    <div class="cl-toggle"><i class="fa fa-bars"></i></div>
    <div class="cl-navblock">
        <div class="menu-space">
            <div class="content">
              <div class="side-user">
                    <div class="info">
                        <a href="#">四得体育</a>
                    </div>
                </div>
                <ul class="cl-vnavigation">
                    <c:forEach items="${fns:getAuthByUser(fns:getUser())}" var="auth">
                        <li><a href="#"><i class="fa fa-home"></i><span>${auth.name}</span></a>
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
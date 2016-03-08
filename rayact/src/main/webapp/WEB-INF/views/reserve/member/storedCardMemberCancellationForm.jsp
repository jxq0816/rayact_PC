<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="content">
    <form:form id="formBean" modelAttribute="reserveMember"
               action="${ctx}/reserve/storedCardMember/save" method="post"
               class="form-horizontal">
        <form:hidden id="id" path="id"/>
        <input type="hidden" id="token" name="token" value="${token}"/>
        <sys:msg content="${message}"/>
        <table id="contentTable" class="table table-bordered">
            <tr>
                <td>姓名:</td>
                <td>${reserveMember.name}</td>
                <td>手机号:</td>
                <td>${reserveMember.mobile}</td>
            </tr>
            <tr>
                <td>身份证:</td>
                <td>${reserveMember.sfz}</td>

                <td>地址:</td>
                <td>${reserveMember.address}</td>
            </tr>

            <tr>
                <td>性别:</td>
                <td>${reserveMember.sex}</td>

                <td>卡号:</td>
                <td>${reserveMember.cartno}</td>
            </tr>
            <tr>
                <td>余额:</td>
                <td>${reserveMember.remainder}</td>

                <td>储值卡名称:</td>
                <td>${reserveMember.storedcardSet.name}
                </td>
            </tr>

            <tr>
                <td>备注:</td>
                <td colspan="3">${reserveMember.remarks}
                </td>
            </tr>
            <tr>
                <td>费率:</td>
                <td colspan="3">
                    20%
                </td>
            </tr>
            <tr>
                <td>实际费用:</td>
                <td colspan="3">
                    <input id="realRefundVolume" value="${reserveMember.remainder*0.8}" readonly="readonly" class="form-control required number"">
                </td>
            </tr>
        </table>
    </form:form>
</div>

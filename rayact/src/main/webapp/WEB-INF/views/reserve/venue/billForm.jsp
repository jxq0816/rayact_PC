<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场馆损益管理</title>
    <meta name="decorator" content="main"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="reserveVenueBill"></jsp:param>
</jsp:include>
<div class="cl-mcont" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>场馆损益添加</h3>
                </div>
                <div class="content">
                    <div class="tab-container">
                        <div class="form-horizontal group-border-dashed">
                            <form:form id="inputForm" modelAttribute="reserveVenueBill"
                                       action="${ctx}/reserve/reserveVenueBill/save" method="post"
                                       class="form-horizontal">
                                <form:hidden path="id"/>
                                <input type="hidden" name="token" value="${token}"/>
                                <sys:message content="${message}"/>
                                <table id="contentTable" class="table table-bordered">
                                    <tr>
                                        <td>水费</td>
                                        <td><form:input path="waterBill" htmlEscape="false"
                                                        class="form-control  number"/></td>
                                        <td>水费说明</td>
                                        <td><form:input path="waterBillRemark" htmlEscape="false" maxlength="255"
                                                        class="form-control"/></td>
                                    </tr>
                                    <tr>
                                        <td>电费</td>
                                        <td><form:input path="elecBill" htmlEscape="false"
                                                        class="form-control  number"/></td>
                                        <td>电费说明</td>
                                        <td><form:input path="elecBillRemark" htmlEscape="false" maxlength="255"
                                                        class="form-control"/></td>
                                    </tr>
                                    <tr>
                                        <td>油费</td>
                                        <td><form:input path="oilBill" htmlEscape="false"
                                                        class="form-control  number"/></td>
                                        <td>油费说明</td>
                                        <td><form:input path="oilBillRemark" htmlEscape="false" maxlength="255"
                                                        class="form-control"/></td>
                                    </tr>
                                    <tr>
                                        <td>体育用品维修</td>
                                        <td><form:input path="sportDeviceRepairBill" htmlEscape="false"
                                                        class="form-control  number"/></td>
                                        <td>体育用品维修说明</td>
                                        <td><form:input path="sportDeviceRepairBillRemark" htmlEscape="false"
                                                        maxlength="255"
                                                        class="form-control"/></td>
                                    </tr>
                                    <tr>
                                        <td>办公设备维修</td>
                                        <td><form:input path="officeDeviceRepairBill" htmlEscape="false"
                                                        class="form-control  number"/></td>
                                        <td>办公设备维修说明</td>
                                        <td><form:input path="officeDeviceRepairBillRemark" htmlEscape="false"
                                                        maxlength="255"
                                                        class="form-control"/></td>
                                    </tr>
                                    <tr>
                                        <td>场馆设备维修</td>
                                        <td><form:input path="venueDeviceRepairBill" htmlEscape="false"
                                                        class="form-control  number"/></td>
                                        <td>场馆设备维修说明</td>
                                        <td><form:input path="venueDeviceRepairBillRemark" htmlEscape="false"
                                                        maxlength="255"
                                                        class="form-control"/></td>
                                    </tr>

                                    <tr>
                                        <td>其他</td>
                                        <td><form:input path="otherBill" htmlEscape="false"
                                                        class="form-control  number"/></td>
                                        <td>其他说明</td>
                                        <td><form:input path="otherBillRemark" htmlEscape="false" maxlength="255"
                                                        class="form-control"/></td>
                                    </tr>
                                    <tr>
                                        <td>开始时间:</td>
                                        <td>
                                            <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${startDate}"/>"
                                                   name="startDate" id="startDate" type="text" readonly="readonly"
                                                   maxlength="20"
                                                   class="input-medium form-control Wdate "
                                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                        </td>
                                        <td>结束时间:</td>
                                        <td>
                                            <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${endDate}"/>"
                                                   name="endDate" id="endDate" type="text" readonly="readonly"
                                                   maxlength="20"
                                                   class="input-medium form-control Wdate "
                                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>场馆</td>
                                        <td>
                                            <c:forEach items="${venueList}" var="venue" varStatus="status">
                                                <label>
                                                    <c:forEach items="${userRole.venueList}" var="v">
                                                        <c:if test="${venue.id eq v}">
                                                            <c:set var="vchecked" value="checked=\"checked\""/>
                                                        </c:if>
                                                    </c:forEach>
                                                    <input type="checkbox" id="userRoleCheck" ${vchecked}
                                                           name="reserveVenue.id" class="icheck required"
                                                           value="${venue.id}"/>${venue.name}
                                                </label>
                                            </c:forEach>
                                        </td>
                                        <td>备注</td>
                                        <td><form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255"
                                                           class="form-control"/></td>
                                    </tr>
                                </table>
                                <div>
                                    <input id="btnSubmit"
                                           class="btn btn-primary"
                                           type="submit"
                                           value="保 存"/>&nbsp;
                                    <input id="btnCancel" class="btn" type="button" value="返 回"
                                           onclick="history.go(-1)"/>
                                </div>
                            </form:form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctxStatic}/modules/reserve/js/validate.js"></script>
</body>
</html>
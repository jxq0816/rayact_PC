<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=7862256"/>
    <title>会员收入统计</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="memberIncomeReport"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>会员收入统计</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveCardStatements"
                           action="${ctx}/reserve/reserveCardStatements/memberIncomeReport"
                           method="post" class="breadcrumb form-search">
                    <div class="row">
                        <div class="col-sm-10 col-md-10 col-lg-10">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <td>场馆:</td>
                                <td>

                                    <sys:select cssClass="input-large" name="reserveVenue.id"
                                                value="reserveVenue"
                                                items="${reserveVenueList}" itemLabel="name" itemValue="id"
                                                defaultLabel="----请选择-----"
                                                defaultValue=""></sys:select>
                                </td>

                                <td>类型：</td>
                                <td>
                                    <div class="btn-group" id="payType">
                                        <label class="radio-inline">
                                            <input type="radio" class="icheck" value="1" checked="checked"
                                                   name="queryType"/>汇总
                                        </label>

                                        <label class="radio-inline">
                                            <input type="radio" class="icheck" value="2" name="queryType"/>明细
                                        </label>
                                    </div>
                                </td>
                                <td>
                                    <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveCardStatements.startDate}"/>"
                                           name="startDate" id="startDate" type="text"
                                           maxlength="20"
                                           class="input-medium form-control Wdate "
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                </td>
                                <td>
                                    <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${reserveCardStatements.endDate}"/>"
                                           name="endDate" id="endDate" type="text"
                                           maxlength="20"
                                           class="input-medium form-control Wdate "
                                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                </td>
                                <td><input id="btnSubmit" class="btn btn-primary" type="submit"
                                           value="查询"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </form:form>
                <sys:msg content="${message}"/>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>场馆</th>
                                <th>项目</th>
                                <th>充值金额</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page}" var="report">
                                <tr>
                                    <td>
                                            ${report.venueName}
                                    </td>
                                    <td>
                                            ${report.projectName}
                                    </td>
                                    <td>
                                            ${report.transactionVolume}
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctxStatic}/modules/reserve/js/commodity_income_record.js" type="text/javascript"></script>
</body>
</html>

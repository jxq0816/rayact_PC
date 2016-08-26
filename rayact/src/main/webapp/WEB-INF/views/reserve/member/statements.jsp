<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>交易明细</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="member"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <div class="row">
                        <div class="col-lg-2">
                            <h3>交易明细</h3>
                        </div>
                        <div class="pull-right col-lg-1">
                            <a onclick="history.go(-1)"><img
                                    style="width:30px;height: 30px"
                                    src="${ctxStatic}/modules/reserve/images/return.png"></a>
                        </div>
                    </div>
                </div>

                <form:form id="searchForm" action="${ctx}/reserve/reserveMember/statements" method="post">
                    <input type="hidden" name="reserveMember.id" value="${statements.reserveMember.id}">
                    <table class="table">
                        <tr>
                            <th>姓名：${statements.reserveMember.name}</th>
                            <th>场馆：${statements.reserveMember.reserveVenue.name}</th>
                            <th>卡号：${statements.reserveMember.cardNo}</th>
                            <th>会员卡类型：
                                <j:if test="${statements.reserveMember.cardType==1}">
                                    储值卡
                                </j:if>
                                <j:if test="${statements.reserveMember.cardType==2}">
                                    次卡
                                </j:if>
                            </th>
                            <th>手机号：${statements.reserveMember.mobile}</th>
                            <th>
                                <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${statements.startDate}"/>"
                                       name="startDate" id="startDate" type="text"
                                       maxlength="20"
                                       class="input-medium form-control Wdate "
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                            </th>
                            <th>
                                <input value="<fmt:formatDate  pattern="yyyy-MM-dd" value="${statements.endDate}"/>"
                                       name="endDate" id="endDate" type="text"
                                       maxlength="20"
                                       class="input-medium form-control Wdate "
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                            </th>
                            <th>
                                <input id="btnSubmit" class="btn btn-primary" type="submit"
                                       value="查询"/>
                                <input id="btnExport" class="btn btn-primary"
                                       type="button" value="导出"/>
                            </th>
                        </tr>
                    </table>

                    <table>
                        <thead>
                        <tr>
                            <th>流水编号</th>
                            <th>状态</th>
                            <th>充值金额</th>
                            <j:set name="chargeSum" value="0"></j:set>
                            <th>项目/摘要</th>
                            <th>消费时间/半小时</th>
                            <j:set name="consumptionTimeSum" value="0"></j:set>
                            <th>付款方式</th>
                            <th>消费日期</th>
                            <th>消费金额</th>
                            <j:set name="transactionVolumeSum" value="0"></j:set>
                            <th>会员余额</th>
                            <th>操作员</th>
                            <th>备注</th>
                            <th>操作时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="statement">
                            <tr>
                                <td>
                                        ${statement.id}
                                </td>
                                <td>
                                        ${fns:getDictLabel(statement.delFlag,'del_flag',null)}
                                </td>
                                <td>
                                    <j:if test="${statement.transactionType==1 or statement.transactionType==7}">
                                        ${statement.transactionVolume}
                                        <j:set name="chargeSum"
                                               value="${statement.transactionVolume+chargeSum}"></j:set>
                                    </j:if>
                                </td>
                                <td>
                                        ${fns:getTransactionType(statement.transactionType)}
                                </td>
                                <td>
                                        ${statement.transactionNum}
                                    <j:set name="consumptionTimeSum"
                                           value="${consumptionTimeSum+statement.transactionNum}"></j:set>
                                </td>
                                <td>
                                        ${fns:getPayType(statement.payType)}
                                </td>
                                <td>
                                    <fmt:formatDate value="${statement.createDate}"
                                                    pattern="yyyy-MM-dd"/>
                                </td>
                                <td>
                                    <j:if test="${statement.transactionType!=1 and statement.transactionType!=7}">
                                        ${statement.transactionVolume}
                                        <j:set name="transactionVolumeSum"
                                               value="${statement.transactionVolume+transactionVolumeSum}"></j:set>
                                    </j:if>
                                </td>
                                <td>
                                        ${statement.reserveMember.remainder}
                                </td>

                                <td>
                                        ${statement.updateBy.name}
                                </td>
                                <td>
                                        ${statement.remarks}
                                </td>

                                </td>
                                <td>
                                    <fmt:formatDate value="${statement.updateDate}"
                                                    pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="2">
                            </td>
                            <td>
                                    ${chargeSum}
                            </td>
                            <td>
                            </td>
                            <td>
                                    ${consumptionTimeSum}
                            </td>
                            <td>
                                    ${fns:getPayType(statement.payType)}
                            </td>
                            <td>
                            </td>
                            <td>
                                    ${transactionVolumeSum}
                            </td>
                            <td>

                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                    <div class="row">
                        <div class="col-sm-12">

                            <div class="pull-right">
                                <div class="dataTables_paginate paging_bs_normal">
                                    <sys:javascript_page p="${page}" formId="searchForm"></sys:javascript_page>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#btnExport").click(function () {
        $("#searchForm").attr("action", "${ctx}/reserve/reserveMember/statementsExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action", "${ctx}/reserve/reserveMember/statements");
    });
</script>
</body>
</html>

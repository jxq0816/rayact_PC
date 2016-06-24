<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>场地利用率报表</title>
    <meta name="decorator" content="main"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/modules/reserve/css/field.css?id=7862256"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="listOpenRate"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>场地利用率报表</h3>
                </div>
                <form id="searchForm" action="${ctx}/reserve/saleVenueReport/listOpenRate"
                      method="post" class="content">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                    <div class="row">
                        <div class="col-sm-10">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td colspan="3">
                                        <div class="tab-tit-first">
                                            <ul>
                                                <li <j:if test="${'1' eq search}">class="on"</j:if>><a
                                                        href="${ctx}/reserve/saleVenueReport/listOpenRate?search=1">当天</a></li>
                                                <li <j:if test="${'2' eq search}">class="on"</j:if>><a
                                                        href="${ctx}/reserve/saleVenueReport/listOpenRate?search=2">当月</a></li>
                                                <li <j:if test="${'3' eq search}">class="on"</j:if>><a
                                                        href="${ctx}/reserve/saleVenueReport/listOpenRate?search=3">当年</a></li>
                                            </ul>
                                        </div>
                                    </td>
                                    <td><input name="startDate" value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>" type="text" id="startDate"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
                                               value="${reserveVenue.endDate}"/></td>
                                    <td>
                                        <input name="endDate" value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>"  type="text" id="endDate"
                                               maxlength="20"
                                               class="input-medium form-control Wdate "
                                              onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
                                               value="${reserveVenue.endDate}"/>
                                    </td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                                        <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </form>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>项目类型</th>
                                <th>场地名称</th>
                                <th>营业时间</th>
                                <th>占用时间</th>
                                <th>空闲时间</th>
                                <th>场地利用率</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${openRateList}" var="report">
                                <tr style="height: 30px;">
                                    <td>${report.project_name}</td>
                                    <td>${report.venue_name}${report.field_name}</td>
                                    <td>${report.business_time}小时</td>
                                    <td>${report.utilization_time}小时</td>
                                    <td>${report.free_time}小时</td>
                                    <td>${report.percent}</td>
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
<script type="text/javascript">
    $("#btnExport").click(function(){
        $("#searchForm").attr("action","${ctx}/reserve/saleVenueReport/listOpenRateExport");
        $("#searchForm").submit();
        $("#searchForm").attr("action","${ctx}/reserve/saleVenueReport/listOpenRate");
    });
</script>
</body>
</html>

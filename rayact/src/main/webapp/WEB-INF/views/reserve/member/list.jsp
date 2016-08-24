<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>会员统计列表</title>
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
                    <h3>会员统计列表</h3>
                </div>
                <form:form id="searchForm" modelAttribute="reserveMember" action="${ctx}/reserve/reserveMember/list"
                           method="post">
                <div class="breadcrumb form-search">
                    <div class="row">
                        <div class="col-sm-10 col-md-10 col-lg-10">
                            <table class="no-border">
                                <tbody class="no-border-y">
                                <tr>
                                    <td>场馆：</td>
                                    <td> <sys:select cssClass="input-large" name="reserveVenue.id" id="venue"
                                                    cssStyle="width:100%"
                                                    value="${query.reserveVenue.id}"
                                                    items="${venueList}" itemLabel="name" itemValue="id"
                                                    defaultLabel="----请选择-----"
                                                    defaultValue=""></sys:select>
                                    </td>
                                    <td>姓名：</td>
                                    <td><form:input path="name" cssStyle="width:100px;" htmlEscape="false" maxlength="30"
                                                    class="form-control"/></td>


                                    <td>手机号：</td>
                                    <td><form:input path="mobile" cssStyle="width:100px;" htmlEscape="false" class="form-control"/>
                                    </td>

                                    <td>卡号：</td>
                                    <td><form:input path="cardNo" cssStyle="width:100px;" htmlEscape="false" class="form-control"/>
                                    </td>
                                    <td>
                                        <input type="radio" class="icheck"  id="isOwning_all" name="isOwning" value=""/>全部
                                        <input type="radio" class="icheck"  id="isOwning_false" name="isOwning"  value="0"/>储值
                                        <input type="radio" class="icheck"  id="isOwning_true" name="isOwning" value="1"/>欠费
                                    </td>
                                    <td>
                                        <form:radiobuttons class="icheck"  path="cardType" items="${fns:getDictList('card_type')}"
                                                           itemLabel="label"
                                                           itemValue="value" htmlEscape="false"/>
                                    </td>
                                    <td><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                                        <j:if test="${userType==1 || userType==5 || userType==6}">
                                            <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
                                        </j:if>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <sys:message content="${message}"/>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>场馆</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>性别</th>
                                <th>卡号</th>
                                <th>卡号类型</th>
                                <th>余额</th>
                                <th width="300px">备注</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="reserveMember">
                                <tr>
                                    <td>
                                            ${reserveMember.reserveVenue.name}
                                    </td>
                                    <td><a href="${ctx}/reserve/reserveMember/form?id=${reserveMember.id}">
                                            ${reserveMember.name}
                                    </a></td>
                                    <td>
                                            ${fns:hidePhone(reserveMember.mobile)}

                                    </td>
                                    <td>
                                            ${fns:getDictLabel(reserveMember.sex, 'sex', '')}
                                    </td>
                                    <td>
                                            ${reserveMember.cardNo}
                                    </td>
                                    <td>
                                            ${fns:getDictLabel(reserveMember.cardType, 'card_type', '')}
                                    </td>
                                    <td class="remainder">
                                            ${reserveMember.remainder}
                                    </td>
                                    <td>
                                            ${reserveMember.remarks}
                                    </td>
                                    <td>
                                        <a class="btn btn-primary btn-xs"
                                           href="${ctx}/reserve/reserveMember/statements?reserveMember.id=${reserveMember.id}">交易明细</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="6"></td>
                                <td id="remainder_sum"></td>
                                <td colspan="2"></td>
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
                    </div>
                </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('.icheck').iCheck({
            checkboxClass: 'icheckbox_square-blue checkbox',
            radioClass: 'iradio_square-blue'
        });
        var isOwning='${query.isOwning}';
        if(isOwning=='0'){
            $("#isOwning_false").iCheck('check');
        }
        if(isOwning=='1'){
            $("#isOwning_true").iCheck('check');
        }
        if(isOwning==''){
            $("#isOwning_all").iCheck('check');
        }
        $("#btnExport").click(function () {
            $("#searchForm").attr("action", "${ctx}/reserve/reserveMember/listExport");
            $("#searchForm").submit();
            $("#searchForm").attr("action", "${ctx}/reserve/reserveMember/list");
        });
        var remainder = 0;
        $(".remainder").each(function(){
            remainder += parseFloat($(this).html());
        });
        $("#remainder_sum").html(remainder);

    });
</script>
</body>
</html>

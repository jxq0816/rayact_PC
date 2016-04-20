<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>会员管理</title>
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
                    <h3>交易明细</h3>
                </div>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>场馆</th>
                                <th>姓名</th>
                                <th>卡号</th>
                                <th>充值金额</th>
                                <th>消费类型</th>
                                <th>商品名称</th>
                                <th>数量/半小时</th>
                                <th>消费金额</th>
                                <th>会员余额</th>
                                <th>操作员</th>
                                <th>操作时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="statement">
                                <tr>
                                    <td>
                                            ${statement.venue.name}
                                    </td>
                                    <td>
                                            ${statement.reserveMember.name}
                                    </td>
                                    <td>
                                            ${statement.reserveMember.cartno}
                                    </td>
                                    <td>
                                        <j:if test="${statement.transactionType==1 or statement.transactionType==7}">
                                            ${statement.transactionVolume}
                                        </j:if>
                                    </td>
                                    <td>
                                        <j:if test="${statement.transactionType==1}">
                                            充值
                                        </j:if>
                                        <j:if test="${statement.transactionType==2}">
                                            退费
                                        </j:if>
                                        <j:if test="${statement.transactionType==3}">
                                            商品
                                        </j:if>
                                        <j:if test="${statement.transactionType==7}">
                                           次卡充值
                                        </j:if>
                                        <j:if test="${statement.transactionType==8}">
                                            场地售卖
                                        </j:if>
                                        <j:if test="${statement.transactionType==9}">
                                            场次票售卖
                                        </j:if>
                                    </td>
                                    <td>
                                            ${statement.reserveCommodity.name}
                                    </td>
                                    <td>
                                            ${statement.transactionNum}
                                    </td>
                                    <td>
                                        <j:if test="${statement.transactionType!=1 and statement.transactionType!=7}">
                                            ${statement.transactionVolume}
                                        </j:if>
                                    </td>
                                    <td>
                                            ${statement.reserveMember.remainder}
                                    </td>
                                    <td>
                                            ${statement.updateBy.name}
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${statement.updateDate}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/>

                                    </td>
                                </tr>
                            </c:forEach>
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
    });
</script>
</body>
</html>

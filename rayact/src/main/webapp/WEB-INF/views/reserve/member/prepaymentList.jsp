<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="main"/>
    <title>次卡预付款</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/sidebar.jsp">
    <jsp:param name="action" value="timecardMember"></jsp:param>
</jsp:include>
<div class="container-fluid" id="pcont">
    <div class="row">
        <div class="col-md-12">
            <div class="block-flat">
                <div class="header">
                    <h3>次卡预付款列表</h3>
                </div>
                <div class="content">
                    <div class="table-responsive">
                        <table>
                            <thead>
                            <tr>
                                <th>姓名</th>
                                <th>项目</th>
                                <th>剩余次数</th>
                                <th>余额</th>
                                <th>单次价格</th>
                                <th>备注</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.list}" var="prepayment">
                                <tr>
                                    <td>
                                            ${prepayment.reserveMember.name}
                                   </td>
                                    <td>
                                            ${prepayment.reserveProject.name}
                                    </td>
                                    <td>
                                            ${prepayment.remainTime}
                                    </td>
                                    <td>
                                            ${prepayment.remainder}
                                    </td>
                                    <td>
                                            ${prepayment.singleTimePrice}
                                    </td>
                                    <td>
                                            ${prepayment.remarks}
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

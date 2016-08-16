<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="row">
    <div class="col-md-12">
        <div class="content">
            <div class="table-responsive">
                <table>
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>姓名</th>
                        <th>项目</th>
                        <th>剩余次数</th>
                        <th>余额</th>
                        <th>单次价格</th>
                        <th>充值时间</th>
                    <%--    <th>是否失效</th>--%>
                        <th>备注</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.list}" var="prepayment">
                        <tr>
                            <td>
                                    ${prepayment.id}
                            </td>
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

                                <fmt:formatDate value="${prepayment.createDate}" type="both"/>
                            </td>
                       <%--     <td>
                                    ${prepayment.delFlag}
                            </td>--%>
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

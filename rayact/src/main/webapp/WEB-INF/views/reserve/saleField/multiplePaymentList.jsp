<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<div class="content">
    <div class="table-responsive">
        <table>
            <thead>
            <tr>
                <th class="text-center">支付方式</th>
                <th class="text-center">支付金额</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.list}" var="payment">
                <tr>
                    <td class="text-center">${fns:getPayType(payment.payType)}</td>
                    <td class="text-center">${payment.paymentAmount}元</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="row">
            <div class="col-sm-12">

                <div class="pull-right">
                    <div class="dataTables_paginate paging_bs_normal">
                        <sys:javascript_page p="${page}"></sys:javascript_page>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>

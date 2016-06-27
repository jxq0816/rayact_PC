<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="row">
    <table>
        <form:form id="formBean" modelAttribute="commodity"
                   action="${ctx}/reserve/commodity/inStorage" method="post"
                   class="form-horizontal">
            <form:hidden id="id" path="id"/>
            <input type="hidden" id="token" name="token" value="${token}" />
            <div class="tab-pane active" id="home">
                <table id="contentTable" class="table table-bordered">
                    <tr>
                        <td>场馆：</td>
                        <td>
                                ${commodity.reserveVenue.name}
                        </td>
                        <td>商品备注：</td>
                        <td>
                                ${commodity.remarks}
                        </td>
                    </tr>
                    <tr>
                        <td>商品类型：</td>
                        <td>
                                ${commodity.commodityType.name}
                        </td>

                        <td>状态：</td>
                        <td>
                            <c:choose>
                                <c:when test="${commodity.shelvesStatus== '0'}">
                                    下架
                                </c:when>
                                <c:otherwise>
                                    上架
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>

                    <tr>
                        <td>商品编号：</td>
                        <td>
                                ${commodity.commodityId}

                        </td>
                        <td>商品名称：</td>
                        <td>
                                ${commodity.name}
                        </td>
                    </tr>


                    <tr>
                        <td>价格：</td>
                        <td>
                                ${commodity.price}
                        </td>

                        <td>库存数量：</td>
                        <td>
                                ${commodity.repertoryNum}
                        </td>
                    </tr>

                    <tr>
                        <td>单位：</td>
                        <td>
                                ${commodity.unit}
                        </td>

                        <td>快速搜索：</td>
                        <td>
                                ${commodity.quickSearch}
                        </td>
                    </tr>

                    <tr>
                        <td>入库数量：</td>
                        <td>
                            <input id="inRepertoryBoxNum" name="inRepertoryBoxNum" htmlEscape="false"
                                   class="form-control  required"/>
                            <span class="help-inline"><font color="red">*</font> </span>
                        </td>
                        <td>单箱成本金额：</td>
                        <td>
                            <input id="boxPrice" name="boxPrice" htmlEscape="false"
                                   class="form-control  required"/>
                            <span class="help-inline"><font color="red">*</font> </span>
                        </td>
                    </tr>
                    <tr>
                        <td>入库备注：</td>
                        <td>
                            <input  id="remarks" htmlEscape="false"
                                   class="form-control  required"/>
                            <span class="help-inline"><font color="red">*</font> </span>
                        </td>
                        <td>供应商：</td>
                        <td>
                            <sys:select id="supplierId" cssClass="form-control required" name="reserveCommoditySupplier.id"
                                        value="${commodity.reserveCommoditySupplier.id}"
                                        items="${reserveCommoditySupplierList}" itemLabel="name" itemValue="id"
                                        defaultLabel="请选择"
                                        defaultValue=""></sys:select>
                            <span class="help-inline"><font color="red">*</font> </span>
                        </td>
                    </tr>
                </table>
            </div>
        </form:form>
    </table>
</div>
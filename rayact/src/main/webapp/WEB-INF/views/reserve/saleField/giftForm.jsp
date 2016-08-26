<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form id="giftFormBean" class="form-horizontal">
    <input type="hidden" name="id" value="${cos.id}"/>
    <input type="hidden" name="token" value="${token}"/>
    <input type="hidden" name="itemId" value="${item.id}"/>

    <div class="content text-justify" style="text-align: center;vertical-align: middle;">
         <div class="row">
            <div class="col-lg-6  reserve_mid_line">
                <div class="row">
                    <div class="form-group">
                        <label for="consume" class="control-label col-lg-3">预定人:</label>
                        <div class="col-lg-9">
                            <input id="consume" class="form-control" value="${cos.userName}" readonly="readonly"/>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="form-group">
                        <label for="stand" class="col-lg-3 control-label">顾客标准:</label>
                        <div class="col-lg-9">
                            <input id="stand" class="form-control" readonly="readonly" value="<j:ifelse
                                    test="${'1' eq cos.consType}"><j:then>市场价</j:then><j:else>会员</j:else></j:ifelse>">
                            </input>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group">
                        <label for="gift" class="col-lg-3 control-label">添加赠品:</label>
                        <div class="col-lg-9">
                            <select class="select2" style="width: 100%;" id="gift">
                                <option value="">请选择</option>
                                <c:forEach items="${commodityList}" var="commodity">
                                    <option data-name="${commodity.name}" data-unit="${commodity.unit}"
                                            value="${commodity.id}" data-repertory-num="${commodity.repertoryNum}">
                                            ${commodity.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <table>
                        <thead>
                        <th>商品名称</th>
                        <th>库存</th>
                        <th>数量</th>
                        <th>操作</th>
                        </thead>
                        <tbody  id="giftTable">
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="content">
                    <h4>已赠列表</h4>
                    <div class="table-responsive">
                        <table>
                            <thead>
                                <th>商品名称</th>
                                <th>赠送数量</th>
                            </thead>
                            <tbody>
                            <c:forEach items="${giftList}" var="gift">
                                <tr>
                                    <td>${gift.gift.name}</td>
                                    <td>${gift.num}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function () {
        var length = $("#giftTable tr").length;
        $("#gift").on('change', function () {
            var id = $(this).val();
            var name = $(this).find("option:selected").attr("data-name");
            var unit = $(this).find("option:selected").attr("data-unit");
            var repertoryNum = $(this).find("option:selected").attr("data-repertory-num");
            var html = '<tr><td><input type="hidden" name="giftList[' + length + '].modelId" value="${cos.id}"/>' + name + '</td>' +
                    '<td>' + repertoryNum + '</td>\
                    <td><input type="text" name="giftList[' + length + '].num" value="1" class="form-control"></td>\
                    <td> <input type="hidden" name="giftList[' + length + '].gift.id" value="' + id + '"/>\
                    <a class="btn btn-danger delGifTr btn-xs" <i class="fa fa-times"></i>删除</a></td>\
                    <td><input type="hidden" name="giftList[' + length + '].reserveMemberName" value="${cos.userName}"/> </tr>'

            $("#giftTable").append(html);
            length++;
        });
        //赠品
        $("#giftTable").on('click', '.delGifTr', function (event) {
            $(this).parents("tr").remove();
            event.stopPropagation();
        });
    });
</script>
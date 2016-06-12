<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<div class="row">
    <!--用户信息-->
    <div class="col-lg-12 pull-left">
        <div class="col-lg-6 reserve_mid_line">

            <div class="row">
                <div class="form-group">
                    <label class="memberSelect col-lg-2 control-label" for="memberId">会员列表：</label>
                    <div class="col-lg-10 memberSelect">
                        <select style="width: 100%" id="memberId" class="select2" name="member.id">
                            <option value="">--请输入选择--</option>
                            <c:forEach items="${memberList}" var="m">
                                <option value="${m.id}">${m.mobile}-${m.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--支付方式-->
</div>
<script src="${ctxStatic}/cleanzone/js/jquery.select2/select2.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/modules/reserve/js/time_ticket_sell.js" type="text/javascript"></script>
<script>
    $(document).ready(function () {
        $(".select2").select2({
            width: '100%'
        });
        $('.icheck').iCheck({
            checkboxClass: 'icheckbox_square-blue checkbox',
            radioClass: 'iradio_square-blue'
        });
    })
</script>
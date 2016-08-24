<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="reserveFormBean" class="form-horizontal">
    <input id="id" type="hidden" value="${reserveMember.id}">
    <div class="content text-justify" style="text-align: center;vertical-align: middle;">
        <div class="row">
            <div class="col-lg-6  reserve_mid_line">
                <div class="row">
                    <div class="form-group">
                        <label for="memberName" class="col-sm-2 control-label">姓名:</label>
                        <div class="col-lg-4">
                            <input readonly="readonly" id="memberName" class="form-control"
                                   value="${reserveMember.name}"/>
                        </div>
                        <label for="mobile" class="col-sm-2 control-label">手机号:</label>
                        <div class="col-sm-4">
                            <input readonly="readonly" id="mobile" class="form-control"
                                   value="${reserveMember.mobile}"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="reserveVenue" class="col-sm-2 control-label">场馆:</label>
                        <div class="col-sm-4">
                            <input readonly="readonly" id="reserveVenue" class="form-control"
                                   value="${reserveMember.reserveVenue.name}"/>
                        </div>
                        <label for="reserveFieldTicket" class="col-sm-2 control-label">场次票:</label>
                        <div class="col-sm-4">
                            <input readonly="readonly" id="reserveFieldTicket" class="form-control"
                                   value="${reserveMember.timecardSet.name}"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="residue" class="control-label col-lg-2">剩余次数:</label>
                        <div class="col-lg-4">
                            <input type="text" id="residue" readonly="readonly" value="${reserveMember.residue}"
                                   class="form-control"/>
                        </div>
                        <label for="residue" class="control-label col-lg-2">余额:</label>
                        <div class="col-lg-4">
                            <input type="text" id="remainder" readonly="readonly" value="${reserveMember.remainder}"
                                   class="form-control"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">

                <div class="row">
                    <div class="form-group">
                        <label for="rechargeVolume" class="control-label col-lg-2">退还金额:</label>
                        <div class="col-lg-6"><input id="rechargeVolume" name="userName"
                                                     type="text"
                                                     class="form-control input-sm"/></div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="remarks" class="col-sm-2 control-label">备注:</label>
                        <div class="col-lg-6">
                            <input id="remarks" name="remarks" htmlEscape="false" maxlength="30" class="form-control"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function () {
        $('.icheck').iCheck({
            checkboxClass: 'icheckbox_square-blue checkbox',
            radioClass: 'iradio_square-blue'
        });
    });
</script>
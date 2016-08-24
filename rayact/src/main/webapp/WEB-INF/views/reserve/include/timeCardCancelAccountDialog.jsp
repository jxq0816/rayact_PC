<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 次卡销卡Modal -->
<button id="timeCardCancelAccountDialogButton"  style="display: none" class="btn btn-primary btn-large" href="#timeCardCancelAccountDialog"  data-toggle="modal">销卡</button>
<div class="modal fade"  style="width:auto;" id="timeCardCancelAccountDialog" tabindex="-1" style="display: none"  aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 80%">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">销卡</h4>
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="reserve_top_line">

                </div>
                <div class="modal-body form-horizontal" id="timeCardCancelAccountForm">
                    <!--次卡充值-->

                    <!--end 次卡充值-->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="closeTimeCardCancelAccountDialogBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="saveTimeCardCancelAccountDialogBtn" onclick="timeCardCancelAccount()" class="btn btn-primary btn-flat">确定</button>
            </div>
        </div>
    </div>
</div>

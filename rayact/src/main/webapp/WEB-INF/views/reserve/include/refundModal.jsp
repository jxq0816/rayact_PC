<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 大客户退费Modal -->
<button id="refundForVIPDialogBtn"  style="display: none" class="btn btn-primary btn-large" href="#refundForVIPDialog"  data-toggle="modal">预订</button>
<div class="modal fade"  id="refundForVIPDialog"  style="display: none"  aria-hidden="true">
    <div class="modal-dialog" style="width: 80%">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">大客户退费</h4>
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="reserve_top_line">

                </div>
                <div class="modal-body form-horizontal" id="refundForVIPForm">
                    <!--预定表单-->
                    <!--end 预定表单-->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="closeBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="refundSaveForVIPBtn" class="btn btn-primary btn-flat">确定</button>
            </div>
        </div>
    </div>
</div>
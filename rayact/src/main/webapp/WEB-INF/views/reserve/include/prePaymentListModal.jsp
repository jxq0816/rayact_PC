<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 场馆预订Modal -->
<button id="prePaymentDialogButton"  style="display: none" class="btn btn-primary btn-large" href="#prePaymentDialogModal"  data-toggle="modal">预订</button>
<div class="modal fade"  id="prePaymentDialogModal" style="display: none"  aria-hidden="true">
    <div class="modal-dialog" style="width: 80%">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">预付款</h4>
                <button type="button" class="close" data-dismiss ="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="reserve_top_line">

                </div>
                <div class="modal-body form-horizontal" id="prePaymentDialogForm">
                    <!--预付款-->
                    <!--end 预付款-->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="closeBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                    关闭
                </button>
            </div>
        </div>
    </div>
</div>
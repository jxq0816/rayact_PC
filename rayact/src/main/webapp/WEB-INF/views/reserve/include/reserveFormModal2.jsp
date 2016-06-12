<%@ page contentType="text/html;charset=UTF-8" %>
<button id="reserveDialog" class="btn btn-primary btn-flat md-trigger" style="display: none" data-modal="order-primary">
    预订
</button>
<div class="md-modal colored-header  custom-width md-effect-12" id="order-primary">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel">场地预定</h4>
            <button type="button" class="close md-close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body">
            <div class="reserve_top_line">

            </div>
            <div class="modal-body form-horizontal" id="reserveForm">
                <!--预定表单-->
                <!--end 预定表单-->
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" id="closeBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                取消
            </button>
            <button type="button" id="saveBtn" class="btn btn-primary btn-flat">确定</button>
        </div>
    </div>
</div>
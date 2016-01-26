<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--预定-->
<button class="btn btn-primary btn-flat md-trigger" id="reserveBtn" style="display: none" data-modal="form-primary">Basic Form
</button>
<div class="md-modal colored-header warning custom-width md-effect-12" id="form-primary">
    <div class="md-content">
        <div class="modal-header">
            <h5>场地预定</h5>
            <button type="button" class="close md-close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body form-horizontal" id="reserveForm">
            <!--预定表单-->


            <!--end 预定表单-->
        </div>
        <div class="modal-footer">
            <button type="button" id="closeBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                取消
            </button>
            <button type="button" id="saveBtn" class="btn btn-primary btn-flat">确定</button>
        </div>
    </div>
</div>
<!--end 预定-->

<!--取消订单-->
<button class="btn btn-primary btn-flat md-trigger" id="cancelBtn" style="display: none" data-modal="cancel-primary">Basic Form
</button>
<div class="md-modal colored-header warning custom-width md-effect-12" id="cancel-primary">
    <div class="md-content">
        <div class="modal-header">
            <h5>取消预定</h5>
            <button type="button" class="close md-close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body form-horizontal" id="cancelForm">
            <!--预定表单-->


            <!--end 预定表单-->
        </div>
        <div class="modal-footer">
            <button type="button" id="closeCancelBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                取消
            </button>
            <button type="button" id="saveCancelBtn" class="btn btn-primary btn-flat">保存</button>
        </div>
    </div>
</div>
<!--end 取消订单-->

<!--结账-->
<button class="btn btn-primary btn-flat md-trigger" id="settlementBtn" style="display: none" data-modal="settlement-primary">Basic Form
</button>
<div class="md-modal colored-header warning custom-width md-effect-12" id="settlement-primary">
    <div class="md-content">
        <div class="modal-header">
            <h5>结算</h5>
            <button type="button" class="close md-close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body form-horizontal" id="settlementForm">
            <!--预定表单-->


            <!--end 预定表单-->
        </div>
        <div class="modal-footer">
            <button type="button" id="closeSettlementBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                取消
            </button>
            <button type="button" id="saveSettlementBtn" class="btn btn-primary btn-flat">结算订单</button>
        </div>
    </div>
</div>
<!--结账-->

<!--查看详情-->
<button class="btn btn-primary btn-flat md-trigger" id="detailsBtn" style="display: none" data-modal="details-primary">Basic Form
</button>
<div class="md-modal colored-header warning custom-width md-effect-12" id="details-primary">
    <div class="md-content">
        <div class="modal-header">
            <h5>预定详情</h5>
            <button type="button" class="close md-close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body form-horizontal" id="detailsForm">
            <!--预定表单-->


            <!--end 预定表单-->
        </div>
        <div class="modal-footer">
            <button type="button" id="closeDetailsBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                取消
            </button>
        </div>
    </div>
</div>
<!--end 查看详情-->
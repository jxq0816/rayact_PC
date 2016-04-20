<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 场馆预订Modal -->
<button id="reserveDialog"  class="btn btn-primary btn-flat md-trigger" style="display: none" data-modal="order-primary">预订</button>
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
</div>
<!--取消订单-->
<button class="btn btn-primary btn-flat md-trigger" id="cancelBtn" style="display: none" data-modal="cancel-primary">Basic Form
</button>
<div class="md-modal colored-header  custom-width md-effect-12" id="cancel-primary">
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

<%--结账模态--%>
<button id="settlementBtn"  style="display: none" class="btn btn-primary btn-large" href="#settlementDialogModal"  data-toggle="modal">结账</button>
<div class="modal fade"  style="width:auto;" id="settlementDialogModal" tabindex="-1" style="display: none"  aria-labelledby="settlementModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="settlementModalLabel">结算</h4>
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="reserve_top_line">

                </div>
                <div class="modal-body form-horizontal" id="settlementForm">
                    <!--结账模态-->
                    <!--end 结账模态-->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="closeSettlementBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="saveSettlementBtn" class="btn btn-primary btn-flat">确定</button>
            </div>
        </div>
    </div>
</div>
<%--结账模态--%>
<!--查看详情-->
<button class="btn btn-primary btn-flat md-trigger" id="detailsBtn" style="display: none" data-modal="details-primary">Basic Form
</button>
<div class="md-modal colored-header custom-width md-effect-12" id="details-primary">
    <div class="md-content">
        <div class="modal-header">
            <h5>预定详情</h5>
            <button type="button" class="close md-close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body form-horizontal" id="detailsForm">
            <!--预定详情-->


            <!--end 预定详情-->
        </div>
        <div class="modal-footer">
            <button type="button" id="closeDetailsBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                关闭
            </button>
        </div>
    </div>
</div>
<!--end 查看详情-->

<!-- 赠品Modal -->
<button id="giftBtn"  style="display: none" class="btn btn-primary btn-large" href="#giftDialogModal"  data-toggle="modal">预订</button>
<div class="modal fade"  style="width:auto;" id="giftDialogModal" tabindex="-1" style="display: none"  aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="giftModalLabel">赠品</h4>
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="reserve_top_line">

                </div>
                <div class="modal-body form-horizontal" id="giftForm">
                    <!--赠品表单-->
                    <!--end 赠品表单-->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="closeGiftBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="saveGiftBtn" class="btn btn-primary btn-flat">确定</button>
            </div>
        </div>
    </div>
</div>

<!-- 申请优惠Modal -->
<button class="btn btn-primary btn-flat md-trigger" id="applyCutBtn" style="display: none" data-modal="applyCut-primary">Basic Form
</button>
<div class="md-modal colored-header custom-width md-effect-12" id="applyCut-primary">
    <div class="md-content">
        <div class="modal-header">
            <h5>申请优惠</h5>
            <button type="button" class="close md-close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body form-horizontal" id="applyCutForm">
            <!--预定详情-->


            <!--end 预定详情-->
        </div>
        <div class="modal-footer">
            <button type="button" id="saveApplyCutBtn" class="btn btn-default btn-flat">
                确定
            </button>
            <button type="button" id="closeApplyCutBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                关闭
            </button>

        </div>
    </div>
</div>
<script>
    $("#settlementDialogModal").draggable({
        handle: ".modal-header"
    });
</script>
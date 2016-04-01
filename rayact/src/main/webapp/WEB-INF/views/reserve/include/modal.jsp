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

<button id="settlementBtn"  style="display: none" class="btn btn-primary btn-large" href="#settlementDialogModal"  data-toggle="modal">预订</button>
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
                    <!--预定表单-->
                    <!--end 预定表单-->
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


<%--<!--结算详情-->
<button id="settlementDetailBtn"  style="display: none" class="btn btn-primary btn-large" href="#settlementDetailDialogModal"  data-toggle="modal">预订</button>
<div class="modal fade"  style="width:auto;" id="settlementDetailDialogModal" tabindex="-1" style="display: none"  aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-header">
                <h4 class="modal-title" id="settlementDetailLabel">结算</h4>
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="reserve_top_line">

                </div>
                <div class="modal-body form-horizontal" id="settlementDetailForm">
                    <!--结算表单-->
                    <!--end 结算表单-->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="closeSettlementDetailBtn" class="btn btn-default btn-flat md-close" data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="saveSettlementDetailBtn" class="btn btn-primary btn-flat">确定</button>
            </div>
        </div>
    </div>
</div>--%>
<script>
    document.write("<script type='text/javascript' src='${ctxStatic}/modules/reserve/js/reserve_field.js?t=" + Math.random() + "'><\/script>");
    $(document).ready(function () {
        $("#reserveDialogModal").draggable({
            handle: ".modal-header",
            cursor: 'move',
            refreshPositions: true
        });
        $("#giftDialogModal").draggable({
            handle: ".modal-header",
            cursor: 'move',
            refreshPositions: true
        });
        $("#settlementDetailDialogModal").draggable({
            handle: ".modal-header",
            cursor: 'move',
            refreshPositions: true
        });
    });
</script>
<%@ page contentType="text/html;charset=UTF-8" %>
<!--取消订单-->
<button class="btn btn-primary btn-large" id="cancelBtn" style="display: none" href="#cancelDialogModal" data-toggle="modal">取消订单</button>
<div class="modal fade" id="cancelDialogModal" tabindex="-1" style="display: none;" aria-labelledby="cancelDialogModal"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
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
                <button type="button" id="closeCancelBtn" class="btn btn-default btn-flat md-close"
                        data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="saveCancelBtn" class="btn btn-primary btn-flat">保存</button>
            </div>
        </div>
    </div>
</div>
<!--end 取消订单-->

<%--结账模态--%>
<button id="settlementBtn" style="display: none" class="btn btn-primary btn-large" href="#settlementDialogModal" data-toggle="modal">结算
</button>
<div class="modal fade" id="settlementDialogModal" tabindex="-1" style="display: none;"
     aria-labelledby="settlementModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 80%">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="settlementModalLabel">结算</h4>
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
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
                <button type="button" id="closeSettlementBtn" class="btn btn-default btn-flat md-close"
                        data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="saveSettlementBtn" class="btn btn-primary btn-flat">确定</button>
            </div>
        </div>
    </div>
</div>
<%--结账模态--%>

<!-- 订场详情modal -->
<button id="detailsBtn" style="display: none" class="btn btn-primary btn-large" href="#detaisDialogModal"
        data-toggle="modal">订场详情
</button>
<div class="modal fade" style="width:auto;" id="detaisDialogModal" style="display: none" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">订场详情</h4>
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="reserve_top_line">

                </div>
                <div class="modal-body form-horizontal" id="detailsForm">
                    <!--订场详情-->
                    <!--end 订场详情-->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="closeDetailsBtn" class="btn btn-default btn-flat md-close"
                        data-dismiss="modal">
                    确定
                </button>
            </div>
        </div>
    </div>
</div>
<!-- 赠品Modal -->
<button id="giftBtn" style="display: none" class="btn btn-primary btn-large" href="#giftDialogModal"
        data-toggle="modal">赠品
</button>
<div class="modal fade" style="width:auto;" id="giftDialogModal" style="display: none" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="giftModalLabel">赠品</h4>
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
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
                <button type="button" id="closeGiftBtn" class="btn btn-default btn-flat md-close"
                        data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="saveGiftBtn" class="btn btn-primary btn-flat">确定</button>
            </div>
        </div>
    </div>
</div>
<%--多方式付款详情模态--%>
<button id="multiplePaymentsBtn" style="display: none" class="btn btn-primary btn-large"
        href="#multiplePaymentsDialogModal" data-toggle="modal">多方式付款详情
</button>
<div class="modal fade" id="multiplePaymentsDialogModal" style="display: none;" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">多方式付款详情</h4>
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="reserve_top_line">

                </div>
                <div class="modal-body form-horizontal" id="multiplePaymentsForm">
                    <!--结账模态-->
                    <!--end 结账模态-->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="closeMultiplePaymentsBtn" class="btn btn-default btn-flat md-close"
                        data-dismiss="modal">
                    确定
                </button>
            </div>
        </div>
    </div>
</div>
<%--结账模态--%>

<button id="settlementResultBtn" style="display: none" class="btn btn-primary btn-large"
        href="#settlementResultDialogModal" data-toggle="modal">付款成功
</button>
<div class="modal fade" id="settlementResultDialogModal" style="display: none;" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">付款成功</h4>
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="reserve_top_line">

                </div>
                <div class="modal-body form-horizontal" id="settlementResultForm">
                    <!--付款成功模态-->
                    <!--end 付款成功模态-->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="closeSettlementResultBtn"
                        class="btn btn-default btn-flat md-close" data-dismiss="modal">
                    关闭
                </button>
            </div>
        </div>
    </div>
</div>
<button id="inStorageDialogBtn" style="display: none" class="btn btn-primary btn-large" href="#inStorageDialogModal"
        data-toggle="modal">商品入库
</button>
<div class="modal fade" id="inStorageDialogModal" style="display: none;" aria-hidden="true">
    <div class="modal-dialog" style="width:80%">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">商品入库</h4>
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
                <div class="reserve_top_line">

                </div>
                <div class="modal-body form-horizontal" id="inStorageForm">
                    <!--商品入库模态-->
                    <!--end 商品入库模态-->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="closeInStorageBtn" class="btn btn-default btn-flat md-close"
                        data-dismiss="modal">
                    取消
                </button>
                <button type="button" id="saveInStorageBtn" class="btn btn-primary btn-flat">确定</button>
            </div>
        </div>
    </div>
</div>
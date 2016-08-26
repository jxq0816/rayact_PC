<%@ page contentType="text/html;charset=UTF-8" %>
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
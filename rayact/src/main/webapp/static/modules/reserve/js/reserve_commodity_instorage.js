$(document).ready(function () {
    $(".instorageBtn").on('click', function () {
        var id = $(this).attr("data-id");
        jQuery.postItems({
            url: ctx+'/reserve/commodity/inStorageForm',
            data: {id: id},
            success: function (result) {
                if (result) {
                    $("#inStorageForm").html(result);
                    $("#inStorageDialogBtn").click();
                    $("#inStorageForm .select2").select2({
                        width: '100%'
                    });
                    $('#reserveForm .icheck').iCheck({
                        checkboxClass: 'icheckbox_square-blue checkbox',
                        radioClass: 'iradio_square-blue'
                    });
                }
            }
        });
    });
    //保存
    $("#saveInStorageBtn").on('click', function () {
        var id = $("#id").val();
        var token=$("#token").val();
        var inRepertoryBoxNum=$("#inRepertoryBoxNum").val();
        var boxPrice=$("#boxPrice").val();
        var supplierId=$("#supplierId").val();
        var remarks=$("#remarks").val();

        if(inRepertoryBoxNum==null || inRepertoryBoxNum==undefined || inRepertoryBoxNum=='') {
            errorLoding('请输入箱数!');
            return;
        }else if(isNaN(inRepertoryBoxNum)){
            errorLoding('入库量必须为数字!');
            return;
        }
        if(boxPrice==null || boxPrice==undefined || boxPrice=='') {
            errorLoding('请输入单箱价格!');
            return;
        }else if(isNaN(boxPrice)){
            errorLoding('单箱价格必须为数字!');
            return;
        }
        if(!supplierId){
            errorLoding('请选择供应商!');
            return;
        }
        jQuery.postItems({
            url: ctx+'/reserve/commodity/inStorage',
            data: {
                id: id,
                token: token,
                inRepertoryBoxNum:inRepertoryBoxNum,
                boxPrice:boxPrice,
                supplierId:supplierId,
                remarks:remarks
            },
            success: function (result) {
                if (result == "success") {
                    formLoding('保存成功!');
                    location.reload("true");
                } else {
                    formLoding('保存出错!');
                }
            }
        });
    });
});
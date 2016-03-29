/**
 * Created by lenovo on 2016/1/11.
 */
$(document).ready(function() {

    $("#btnSubmit").click(function () {
        if (checkChildRelation() == true) {
            var id=$("#id").val();
            var childFieldId = $("#childFieldId").val();
            var parentFieldId = $("#parentFieldId").val();
            jQuery.postItems({
                url: ctx + '/reserve/reserveFieldRelation/save',
                data: {
                    id:id,
                    childFieldId:childFieldId,
                    parentFieldId:parentFieldId
                },
                success: function (result) {
                    if(result=="success"){
                        successLoding("保存成功");
                        window.location=ctx + '/reserve/reserveFieldRelation/list';
                    }
                }
            });

        }
    });
})
function checkChildRelation() {
    var id=$("#id").val();
    var childFieldId = $("#childFieldId").val();
    var rs=false;
    jQuery.postItems({
        url: ctx + '/reserve/reserveFieldRelation/checkChildRelation',
        data: {
            id:id,
            childFieldId:childFieldId
        },
        success: function (result) {
            if(result=="false"){
                errorLoding("该半场已存在主场");
                rs=false;
            }
            else{
               rs=true;
            }
        }
    });
    return rs;
}
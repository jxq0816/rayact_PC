/**
 * Created by lenovo on 2016/1/11.
 */
function checkCommodityId() {
    var id=$("#id").val();
    var commodityId = $("#commodityId").val();
    var rs=false;
    jQuery.postItems({
        url: ctx + '/reserve/commodity/checkCommodityId',
        data: {
            id:id,
            commodityId:commodityId
        },
        success: function (result) {
            if(result=="unavailable") {
                alert("商品编号已经存在");
                rs=false;
            }else{
                rs=true;
            }
        }
    });
    return rs;
}
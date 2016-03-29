/**
 * Created by lenovo on 2016/1/11.
 */
function checkChildRelation() {
    var data = $("#inputForm").serializeArray();
    jQuery.postItems({
        url: ctx + '/reserve/reserveFieldRelation/checkChildRelation',
        data: {
            id:id,
            date:data
        },
        success: function (result) {
           return result;
        }
    });
}
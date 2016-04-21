function multiple_payments(orderId){
    var rs=false;
    jQuery.postItems({
        url: ctx + '/reserve/reserveMultiplePayment/list',
        data: {
            orderId:orderId
        },
        success: function (result) {
            $("#multiplePaymentsForm").html(result);
            $("#multiplePaymentsBtn").click();
        }
    });
    return rs;
}
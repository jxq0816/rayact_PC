function addToCart(vsId,venueId){
        jQuery.postItems({
            url: ctx+'/reserve/reserveVenueOrder/form?math='+Math.random(),
            data: {vsId: vsId,venueId:venueId},
            success: function (result) {
                if (result) {
                    $("#reserveForm").html(result);
                    $("#timeTicketBtn").click();
                    $("#reserveForm .select2").select2({
                        width: '100%'
                    });
                    $('#reserveForm .icheck').iCheck({
                        checkboxClass: 'icheckbox_square-blue checkbox',
                        radioClass: 'iradio_square-blue'
                    });
                }
            }
        });
}
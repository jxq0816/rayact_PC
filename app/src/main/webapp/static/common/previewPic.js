$.ajaxSetup({
    async : false
});
var previewHeight = 100;
var previewWidth = 100;
function previewPic(dom,preClass){
    $(preClass).html("");
    for(var i = 0 ; i<dom.files.length ; i++){
        var reader = new FileReader();
        var img;
        reader.onload = function(e){
            img = document.createElement("img")
            img.src = e.target.result;
            $(img).load(function(){
                var size = autoSize(this.naturalWidth, this.naturalHeight);
                $(this).css({
                    width: size.width,
                    height: size.height,
                    margin: 2,
                    top: (previewHeight - size.height) / 2,
                    left: (previewWidth - size.width) / 2
                }).show();
            });
            $(preClass).append(img);
        }
        reader.readAsDataURL(dom.files[i]);
    }
}
function autoSize(width, height) {
    var scale = width / height;
    width = previewHeight * scale;
    height = previewHeight;
    return {
        width: width,
        height: height
    }
}
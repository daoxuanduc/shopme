$(document).ready(function() {
    $("#fileImage").change(function() {
        fileSize = this.files[0].size;
        //alert("File size: " + fileSize);
        if (fileSize > 5242880) {
            this.setCustomValidity("You must choose an image less than 5MB!");
            this.reportValidity();
        } else {
            this.setCustomValidity("");
            showImageThumbnail(this);
        }
    });
});

function showImageThumbnail(fileInput) {
    var file = fileInput.files[0];
    var reader = new FileReader();
    reader.onload = function(e) {
        $("#thumbnail").attr("src", e.target.result);
    }

    reader.readAsDataURL(file);
}
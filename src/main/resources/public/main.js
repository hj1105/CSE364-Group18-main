document.getElementById("home").focus();

$(document).ready(function() {
    $('.js-example-basic-multiple').select2();
});

function openForm() {
    document.getElementById("titleForm").style.display = "block";
}

function closeForm() {
    document.getElementById("titleForm").style.display = "none";
}
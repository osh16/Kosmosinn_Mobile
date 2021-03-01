$(document).ready(function() {
    var id;
    var click = 0;
    $(".edit-text-area").hide();
    $("#add-comment-box").hide();
    $("#add-comment-text").click(function() {
        click += 1
        if (click % 2 == 0) {
            $("#add-comment-text").text("comment");
            $("#add-comment-box").hide();
        } else {
            $("#add-comment-text").text("cancel");
            $("#add-comment-box").show();
        }
    });
    $(".edit-comment").click(function() {
        id = this.getAttribute("id").substr(3);
        ($("#eta-"+id)[0]).getElementsByTagName("textarea")[0].innerText = $("#ct-"+id)[0].innerHTML;
        $(".edit-text-area").hide();
        $("#ct-"+id).hide();
        $("#eta-"+id).show();
        $(this).hide()
    });

    var topicId;
    var topicClick = 0;
    $(".edit-topic").click(function() {
        id = this.getAttribute("id").substr(5);
        console.log(this.getAttribute("id").substr(5));
        console.log(id);
        $(".topic-header").load("/topic/" + id + "/edit");
    });
    //$(".cancel-comment").click(function() {
    //    $(this.previousElementSibling).hide();
    //    $("#eta"+$id).show();
    //    id = null;
    //});


});
$(document).ready(function() {
    var id;
    var tmp;
    $(".edit-topic").click(function() {
        id = this.getAttribute("id").substr(5);
        $("#topic-"+id).load("topic/" + id + "/edit");
    });
});


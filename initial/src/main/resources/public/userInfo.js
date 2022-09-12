

// $(document).ready(function() {
//     $(id="userinfoform").submit(function() {
//         $.ajax({
//             url: "http://localhost:8080/user"
//         }).then(function(data) {
//         $('.emailID').append(data.emailID);
//         $('.zipcode').append(data.zipcode);
//         })
//     }
// });

$(document).ready(function() {

    $("#userinfoform").on("submit", function(e) {
       
        e.preventDefault();
        var emailId = $("#email").val();
        alert("Received email Id " + emailId);

        $.get('/user?emailID=' + emailId, function(data, status) {
            $("#resultEmail").html(data.emailID);
            $("#resultZip").html(data.zipcode);
        });
});

    // $.ajax({
    //     type: "GET",
    //     url:  "/user",

    //     data: emailId}).then(function(opdata) {
    //         $('.emailID').append(data.emailID);
    //         $('.zipcode').append(data.zipcode);
    //     })
});




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
        var zipCode = $("#zip").val();
        var userid = $("#userid").val();

        alert("Received email Id=" + emailId + ", zipcode=" + zipCode);

        $.get('/user?emailID=' + emailId + "&zipCode=" + zipCode, function(data, status) {
            $("#resultEmail").html(data.emailID);
            $("#resultZip").html(data.zipcode);

            if (data.newUser == true) {
                alert("new user");
            } else {
                alert("existing user");
            }

            // $("#resultEmail").html(data.emailID);
            // $("#resultZip").html(data.zipcode);

            var userid = data.userid;

            // redirect to profile page
            $(location).attr('href', "/profile.html?userid=" + userid);
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


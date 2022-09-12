function GetURLParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
}

$(document).ready(function() {

    // var userid = $(this).data('userid');
    var userid = GetURLParameter('userid');

    $("#booksinputform").on("submit", function(e) {
       
        e.preventDefault();
        var booksWanted = $("#booksWanted").val();
        var booksForSale = $("#booksForSale").val();

        alert ("User ID is " + userid);
        alert ("booksWanted is " + booksWanted);
        alert ("booksForSale is " + booksForSale);

        $("#userid").html(userid);

        alert("here 1");

        var test = '/books?userid=' + userid + '&bwisbns=' + booksWanted
        + '&bnisbns=' + booksForSale;
        alert(test);

        $.get('/books?userid=' + userid + '&bwisbns=' + booksWanted
        + '&bnisbns=' + booksForSale,
        function(data, status) {
            alert("done");
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


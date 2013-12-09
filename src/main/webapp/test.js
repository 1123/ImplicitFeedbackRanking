function click(userName, password) {
    var feedBack = { chosen : 3, page : [1,2,3,4,5] };
    $.ajax({
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: '/webresources/graph/click',
        dataType: 'json',
        async: false,
        data: JSON.stringify(feedBack),
        success: function () {
            console.log("Thanks!");
        }
    });
}

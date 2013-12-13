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

function search(number, tag) {
    $.getJSON(
        '/api/graph/search_flickr?number=' + number + '&tag=' + tag,
        function(urls) {
            $('#image_list').append('<table><tr>');
            for (key in urls) {
                if (key % 3 == 0) $('#image_list').append('</tr><tr>');
                $('#image_list').append(
                    '<td><a href="' + urls[key] + '"><img width="100" src=' + urls[key] + '></img></a></td>');
            }
            $('#image_list').append('</tr></table>');
        }
    );
}
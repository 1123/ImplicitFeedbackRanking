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

/*
 * This method populates an html table with images retrieved from the local search web service.
 * Pure.js is used as a template rendering engine.
 */
function fill_image_table(urls) {
    $('#image_table').append('<table><tr>');
    var directive = {
        '.image_link@href':'url',
        '.image@src':'url'
    };
    for (key in urls) {
        var renderData = { url : urls[key] };
        var rendered = $('td.template').first().clone().render(renderData, directive);
        if (key % 3 == 0) $('#image_table').append('</tr><tr>');
        $('#image_table').append(rendered);
    }
    $('#image_table').append('</tr></table>');
}

/* This method calls the local web service to get a list of image urls for the given search tags */
function search(number, tag) {
    $.getJSON(
        '/api/graph/search_flickr?number=' + number + '&tag=' + tag,
        function(urls) { fill_image_table(urls); }
    );
}

$(document).ready(function() {
    search(18, 'Katze');
});

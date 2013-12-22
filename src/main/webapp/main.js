function submit_click(chosen) {
    alert("in function click");
    var feedBack = {
        chosen : chosen,
        page : gather_image_urls()
    };
    $.ajax({
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: '/api/graph/click',
        dataType: 'text',
        async: true,
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError)
            alert("error");
        },
        data: JSON.stringify(feedBack),
        success: function () {
            alert: ("success");
        }
    });
    alert("transferred");
}

/*
 * This method populates an html table with images retrieved from the local search web service.
 * Pure.js is used as a template rendering engine.
 */
function fill_image_table(urls) {
    $('#image_table').empty();
    $('#image_table').append('<table><tr>');
    var directive = {
        '.image@src':'url',
        '.image@onclick' : 'onclick'
    };
    for (key in urls) {
        var renderData = {
            url : urls[key],
            onclick : "submit_click(this.src)"
        };
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
    $('#search_button').click(function() {
        tags = $('#tags_input').val();
        console.log(tags);
        search(18, tags);
    });
});


function gather_image_urls() {
    var list = $(".image").map(function(){return $(this).attr("src");}).get();
    return list;
}
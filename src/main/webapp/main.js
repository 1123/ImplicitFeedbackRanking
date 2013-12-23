var urlPairsCached = [];

function submit_click(chosen) {
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
}

/*
 * This method populates an html table with images retrieved from the local search web service.
 * Pure.js is used as a template rendering engine.
 */
function fill_image_table(urlPairs) {
    urlPairsCached = urlPairs;
    $('#image_table').empty();
    $('#image_table').append('<table><tr>');
    var directive = {
        '.image_link@href': 'secondUrl',
        '.image@src':'firstUrl',
        '.image@onclick' : 'onclick',
        '.image_link@data-lightbox' : 'key'
    };
    for (key in urlPairs) {
        var renderData = {
            key : key,
            firstUrl : urlPairs[key]['first'],
            secondUrl : urlPairs[key]['second'],
            onclick : "submit_click(this.src)"
        };
        var rendered = $('td.template').first().clone().render(renderData, directive);
        if (key % 4 == 0) $('#image_table').append('</tr><tr>');
        $('#image_table').append(rendered);
    }
    $('#image_table').append('</tr></table>');
}

/* This method calls the local web service to get a list of image urls for the given search tags */
function search(number, tag) {
    $.getJSON(
        '/api/graph/search_flickr?number=' + number + '&tag=' + tag,
        function(urlPairs) { fill_image_table(urlPairs); }
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
var urlsCached = [];
var searchResultColumns = 4;
var searchResults = 100;

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
        url: '/api/feedback/click',
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
function fill_image_table(urls) {
    urlsCached = urls;
    $('#image_table').empty();
    $('#image_table').append('<table><tr>');
    var directive = {
        '.image_link@href': 'bigUrl',
        '.image@title': 'title',
        '.image@src': 'smallUrl',
        '.image@onclick' : 'onclick',
        '.image_link@data-lightbox' : 'key'
    };
    for (key in urls) {
        /*
         * * key      is a unique value for light-box
         * * title    identifies the image and serves as the node values in the graph
         * * smallUrl is for display in the search results
         * * bigUrl   is for enlarged display of single images.
         */
        var renderData = {
            key : key,
            title : urls[key],
            smallUrl : urls[key] + "_q.jpg",
            bigUrl : urls[key] + "_b.jpg",
            onclick : "submit_click(this.title)"
        };
        var rendered = $('td.template').first().clone().render(renderData, directive);
        if (key % searchResultColumns === 0) $('#image_table').append('</tr><tr>');
        $('#image_table').append(rendered);
    }
    $('#image_table').append('</tr></table>');
}

/* This method calls the local web service to get a list of image urls for the given search tags */
function search(number, tags) {
    $.getJSON(
        '/api/searcher/search_flickr?number=' + number + '&tags=' + tags,
        function(urlPairs) { fill_image_table(urlPairs); }
    );
}

$(document).ready(function() {
    $('#search_button').click(function() {
        tags = $('#tags_input').val();
        search(searchResults, tags);
    });
});


function gather_image_urls() {
    var list = $(".image").map(function(){return $(this).attr("title");}).get();
    return list;
}
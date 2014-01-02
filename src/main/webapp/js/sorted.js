var urlsCached = [];
var searchResultColumns = 4;
var numResults = 20;

function fill_image_table(urls) {
    urlsCached = urls;
    $('#image_table').empty();
    $('#image_table').append('<table><tr>');
    var directive = {
        '.image@src': 'url',
    };
    for (key in urls) {
        var renderData = {
            url : urls[key] + '_q.jpg'
        };
        var rendered = $('td.template').first().clone().render(renderData, directive);
        if (key % searchResultColumns === 0) $('#image_table').append('</tr><tr>');
        $('#image_table').append(rendered);
    }
    $('#image_table').append('</tr></table>');
}

function sort(tags) {
    $.getJSON(
        '/api/sorter/sort?tags=' + tags,
        function(urls) { fill_image_table(urls); }
    );
}

$(document).ready(function() {
    $('#sort_button').click(function() {
        tags = $('#sort_input').val();
        sort(tags);
    });
});

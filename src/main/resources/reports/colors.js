/*
0    red
0.25 yellow
0.5  green
0.75 cyan
1    blue
*/

var colorize = function(value) {
    console.log("colorizing value: " + value)
    var red = { name: "red", r: 0xff, b: 0x00, g: 0x00, wert : 1.0 };
    var yellow = { name: "yellow", r: 0xff, b: 0xff, g: 0x00, wert : 0.75 };
    var blue = { name: "blue", r: 0x00, b: 0xff, g: 0x00, wert : 0.50 };
    var cyan = { name: "cyan", r: 0x00, b: 0xff, g: 0xff, wert : 0.25 };
    var green = { name: "green", r: 0x00, b: 0x00, g: 0xff, wert : 0.0 };

    var colors = [green, cyan, blue, yellow, red];

    var lower_color = colors[0];
    var upper_color = colors[1];
    for (var index = 1; index < colors.length - 1; index++) {
        if (value > (colors[index].wert)) {
            lower_color = colors[index];
            upper_color = colors[index + 1];
        }
    }
    console.log("upper: " + upper_color.name + "; lower " + lower_color.name);
    var result = interpolate(value, lower_color, upper_color);
    return color2hex(result);
};

var interpolate = function(value, lower_color, upper_color) {

    var distance = upper_color.wert - lower_color.wert;
    var below = value - lower_color.wert;

    var above = distance - below;

    var result = {
        r: below * lower_color.r + above * upper_color.r,
        b: below * lower_color.b + above * upper_color.b,
        g: below * lower_color.g + above * upper_color.g
    };

    return result;
};

var color2hex = function(color) {
    result = color.g + 0x100 * color.b + 0x10000 * color.r;
    return result;
}

var hsv2rgb = function(h, s, v) {
	var i = 0;
	var f, p, q, t = 0.0;
	var r, b, g = 0;

	if( s == 0 ) {
		// achromatic (grey)
		r = g = b = v;
		return;
	}

	h /= 60;			// sector 0 to 5
	i = floor( h );
	f = h - i;			// factorial part of h
	p = v * ( 1 - s );
	q = v * ( 1 - s * f );
	t = v * ( 1 - s * ( 1 - f ) );

	switch( i ) {
		case 0:
			r = v;
			g = t;
			b = p;
			break;
		case 1:
			r = q;
			g = v;
			b = p;
			break;
		case 2:
			r = p;
			g = v;
			b = t;
			break;
		case 3:
			r = p;
			g = q;
			b = v;
			break;
		case 4:
			r = t;
			g = p;
			b = v;
			break;
		default:		// case 5:
			r = v;
			g = p;
			b = q;
			break;
	}

}

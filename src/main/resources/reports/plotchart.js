var scene = new THREE.Scene();
var camera = new THREE.PerspectiveCamera(75,window.innerWidth/window.innerHeight, 0.1, 1000);
var renderer = new THREE.WebGLRenderer({ alpha : true });
var material = new THREE.LineBasicMaterial({ side: THREE.DoubleSide, color: 0x0000ff });

var maximum_matrix_value = 0.0;

controls = new THREE.TrackballControls( camera );
controls.target.set( 10, 10, 10)

var axis = function(from, to) {
    var geometry = new THREE.Geometry();
    geometry.vertices.push( from );
    geometry.vertices.push( to );
    var line = new THREE.Line( geometry, material );
    scene.add( line );
}

var render = function () {
    requestAnimationFrame( render );
    controls.update();
    renderer.render(scene, camera);
};

var line_element = function(from, to, height) {
    var geometry = new THREE.Geometry();
    var average_z = (to.z + from.z) * 180 / height;
    var computed_color = tinycolor({ h: average_z, s: 100, v: 100 }).toRgbString();
    var lineMaterial = new THREE.LineBasicMaterial({
        color: computed_color,
        linewidth: 10
    });
    geometry.vertices.push( from );
    geometry.vertices.push( to );
    var line = new THREE.Line( geometry, lineMaterial );
    scene.add( line );
};

var setup = function(matrix) {
    renderer.setSize(window.innerWidth - 100, window.innerHeight - 100);
    document.body.appendChild(renderer.domElement);
    camera.position.set(matrix.length * 1.1, 0.0 - matrix[0].length * 0.6, matrix.length);
    camera.up = new THREE.Vector3(0,0,1);
    camera.lookAt(new THREE.Vector3(0,0,0));
}

var write = function(x,y,z,text) {
    var textGeo = new THREE.TextGeometry(
        text,
        {
            size: 0.5, height: 0.1,
        }
    );
    var text = new THREE.Mesh(textGeo, material);
    text.translateX(x);
    text.translateY(y);
    text.translateZ(z);
    scene.add(text);
}

var plot_plane = function(matrix) {
    length = matrix.length - 1;
    var geometry = new THREE.PlaneGeometry( length, length, length, length );
    var material = new THREE.MeshBasicMaterial( {color: 0xcccccc, side: THREE.DoubleSide, wireframe: true} );
    var plane = new THREE.Mesh( geometry, material );
    plane.translateX(length / 2.0);
    plane.translateY(length / 2.0);
    scene.add( plane );

    write(0           ,-1          , 0 , "" + matrix.minY);
    write(length + 0.5,0           , 0 , "" + matrix.minX);
    write(length - 0.5,-1          , 0 , "" + matrix.maxY);
    write(length + 0.5,length + 0.5, 0 , "" + matrix.maxX);
    write(length      , 0          , -1, "0");
    write(length      , 0          , length / 2.0, maximum_matrix_value);

}

var plot_matrix = function(matrix) {
    setup(matrix);
    plot_axes(matrix);
    plot_plane(matrix);

    var height = matrix.length / 2.0;
    for (var row = 0; row < matrix.length; row++) {
        for (var column = 0; column < matrix[row].length - 1; column++) {
            line_element(
                new THREE.Vector3(row, column, matrix[row][column]),
                new THREE.Vector3(row, column+1, matrix[row][column+1]),
                height
            );
        }
    }
    for (var column = 0; column < matrix[0].length; column++) {
        for (var row = 0; row < matrix.length - 1; row++) {
            line_element(
                new THREE.Vector3(row, column, matrix[row][column]),
                new THREE.Vector3(row+1, column, matrix[row+1][column]),
                height
            );
        }
    }
};


var plot_axes = function(matrix) {
    var lengthXY = matrix.length - 1;
    var lengthZ = matrix.length / 2.0;
    // x-axis
    axis(new THREE.Vector3( 0, 0, 0 ), new THREE.Vector3( lengthXY, 0, 0 ));
    axis(new THREE.Vector3( 0, lengthXY, 0 ), new THREE.Vector3( lengthXY, lengthXY, 0 ));
    axis(new THREE.Vector3( 0, lengthXY, lengthZ ), new THREE.Vector3( lengthXY, lengthXY, lengthZ ));
    axis(new THREE.Vector3( 0, 0, lengthZ ), new THREE.Vector3( lengthXY, 0, lengthZ ));
    // y-axis
    axis(new THREE.Vector3( 0, 0, 0 ), new THREE.Vector3( 0, lengthXY, 0 ));
    axis(new THREE.Vector3( lengthXY, 0, 0 ), new THREE.Vector3( lengthXY, lengthXY, 0 ));
    axis(new THREE.Vector3( lengthXY, 0, lengthZ ), new THREE.Vector3( lengthXY, lengthXY, lengthZ ));
    axis(new THREE.Vector3( 0, 0, lengthZ ), new THREE.Vector3( 0, lengthXY, lengthZ ));
    // z-axis
    axis(new THREE.Vector3( 0, 0, 0 ), new THREE.Vector3( 0, 0, lengthZ ));
    axis(new THREE.Vector3( lengthXY, lengthXY, 0 ), new THREE.Vector3( lengthXY, lengthXY, lengthZ ));
    axis(new THREE.Vector3( 0, lengthXY, 0 ), new THREE.Vector3( 0, lengthXY, lengthZ ));
    axis(new THREE.Vector3( lengthXY, 0, 0 ), new THREE.Vector3( lengthXY, 0, lengthZ ));


};

var find_maximum = function(matrix) {
    var max = 0.0;
    for (var i = 0; i < matrix.length; i++) {
        for (var j = 0; j < matrix[i].length; j++) {
            if (matrix[i][j] > max) {
                max = matrix[i][j];
            }
        }
    }
    // set global variable which is used by plotting function. We should encapsulate this somehow;
    maximum_matrix_value = max;
    return max;
};

var normalize = function(matrix) {
    height = matrix.length / 2.0;
    var max = find_maximum(matrix);
    var normalized = [];
    for (var i = 0; i < matrix.length; i++) {
        normalized[i] = []
        for (var j = 0; j < matrix[i].length; j++) {
            normalized[i][j] = matrix[i][j] * height / max;
        }
    }
    return normalized;
};


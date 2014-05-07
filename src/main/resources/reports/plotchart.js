var scene = new THREE.Scene();
var camera = new THREE.PerspectiveCamera(75,window.innerWidth/window.innerHeight, 0.1, 1000);
var renderer = new THREE.WebGLRenderer({ alpha : true });
renderer.setSize(window.innerWidth - 100, window.innerHeight - 100);
document.body.appendChild(renderer.domElement);
camera.position.set(14,6,10);
camera.up = new THREE.Vector3(0,0,1);
camera.lookAt(new THREE.Vector3(0,0,0));

var geometry = new THREE.PlaneGeometry( 10, 10, 10, 10 );
var material = new THREE.MeshBasicMaterial( {color: 0xcccccc, side: THREE.DoubleSide, wireframe: true} );
var plane = new THREE.Mesh( geometry, material );
plane.translateX(5);
plane.translateY(5);
scene.add( plane );

var material = new THREE.LineBasicMaterial({ side: THREE.DoubleSide, color: 0x0000ff });

var axis = function(from, to) {
    var geometry = new THREE.Geometry();
    geometry.vertices.push( from );
    geometry.vertices.push( to );
    var line = new THREE.Line( geometry, material );
    scene.add( line );
}

var render = function () {
    requestAnimationFrame(render);
    renderer.render(scene, camera);
};

var lineMaterial = new THREE.LineBasicMaterial({
    color: 0x00ff00,
    linewidth: 10
});

var line_element = function(from, to) {
    var geometry = new THREE.Geometry();
    geometry.vertices.push( from );
    geometry.vertices.push( to );
    var line = new THREE.Line( geometry, lineMaterial );
    scene.add( line );
};

var plot_matrix = function(matrix) {
    for (var row = 0; row < matrix.length; row++) {
        for (var column = 0; column < matrix[row].length - 1; column++) {
            console.log("row: " + row + " column: " + column + " value: " + matrix[row][column]);
            line_element(
                new THREE.Vector3(row, column, matrix[row][column]),
                new THREE.Vector3(row, column+1, matrix[row][column+1])
            );
        }
    }
    for (var column = 0; column < matrix[0].length; column++) {
        for (var row = 0; row < matrix.length - 1; row++) {
            line_element(
                new THREE.Vector3(row, column, matrix[row][column]),
                new THREE.Vector3(row+1, column, matrix[row+1][column])
            );
        }
    }
};


var plot_axes = function() {
    // x-axis
    console.log("drawing x-axis");
    axis(new THREE.Vector3( 0, 0, 0 ), new THREE.Vector3( 10, 0, 0 ));
    // y-axis
    console.log("drawing y-axis");
    axis(new THREE.Vector3( 0, 0, 0 ), new THREE.Vector3( 0, 10, 0 ));
    // z-axis
    console.log("drawing z-axis");
    axis(new THREE.Vector3( 0, 0, 0 ), new THREE.Vector3( 0, 0, 10 ));
};

var normalize = function(matrix) {
    var max = 0.0;
    for (var i = 0; i < matrix.length; i++) {
        for (var j = 0; j < matrix[i].length; j++) {
            if (matrix[i][j] > max) {
                max = matrix[i][j];
                console.log("max: " + max);
            }
        }
    }
    var normalized = [];
    for (var i = 0; i < matrix.length; i++) {
        normalized[i] = []
        for (var j = 0; j < matrix[i].length; j++) {
            normalized[i][j] = matrix[i][j] * 5 / max;
        }
    }
    console.log(normalized);
    return normalized;
};
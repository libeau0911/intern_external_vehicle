// var listener1;
// var position = new naver.maps.LatLng(35.893000, 128.607000);
// var map = new naver.maps.Map('map', {
//     center: position,
//     zoom: 10
// });
// var marker1 = new naver.maps.Marker({
//     position: position,
//     map: map
// });
// listener1 = naver.maps.Event.addListener(map, 'click', function (e) {
//     // if (sel_type == "marker2")
//     marker1.setPosition(e.coord);
// });
//
function move_mainCursor(data){
    // alert(data.x + " " + data.y);
    marker1.setPosition(new naver.maps.LatLng(data.x, data.y))
}

var listener1, listener2;
var position = new naver.maps.LatLng(35.893000, 128.607000);
var position2 = new naver.maps.LatLng(35.890000, 128.610088);
var map = new naver.maps.Map('map', {
    center: position,
    zoom: 10
});
var marker1 = new naver.maps.Marker({
    position: position,
    map: map
});
var marker2 = new naver.maps.Marker({
    position: position2,
    map: map
});
listener1 = naver.maps.Event.addListener(map, 'click', function (e) {
    // if (sel_type == "marker2")
    marker1.setPosition(e.coord);
});



var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            // showGreeting(JSON.parse(greeting.body).content);
            var jsondata = JSON.parse(greeting.body)
            if (jsondata.type == "marker") {
                move_mainCursor(jsondata);
            }
            else if (jsondata.type == "info") {
                process_JSONdata(jsondata.data);
            }
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    var x1 = marker1.position.lng()
    var y1 = marker1.position.lat()
    var x2 = marker2.position.lng()
    var y2 = marker2.position.lat()
    var data = {
        "x1": x1,
        "y1": y1,
        "x2": x2,
        "y2": y2,
        'text': $("#name").val()
    }
    stompClient.send("/app/hello", {}, JSON.stringify(data));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
});


function process_JSONdata(jsondata) {
    $("#greetings").empty()
    for (var i = 0; i < jsondata.length; i++) {
        var obj = jsondata[i];
        $("#greetings").append("<tr>");
        $("#greetings").append("<td>"
            + obj.road_name_text +
            "</td>");
        $("#greetings").append("<td>"
            + obj.travel_time +
        "</td>");
        $("#greetings").append("<td>"
            + obj.sectionNm +
            "</td>");
        $("#greetings").append("<tr>");
    }


}

let stomp = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);

    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }

    $('#output').val('');
    $("#responses").html("");
}

function connect() {
    console.log('connect...')
    stomp = webstomp.over(new SockJS('/ws'));

    stomp.connect({}, function (frame) {
        setConnected(true);
        console.log('Client connected: ' + frame);

        const subscription = stomp.subscribe('/queue/responses', function (response) {
            log(response, 'table-success');
        });

        stomp.subscribe('/queue/errors', function (response) {
            log(response, 'table-danger');

            console.log('Client unsubscribes: ' + subscription);
            subscription.unsubscribe({});
        });

        stomp.subscribe('/topic/new-round', function (response) {
            log(response, 'table-info', 'new');
        });

        stomp.subscribe('/topic/finish-round', function (response) {
            log(response, 'table-info', 'end');
        });

        stomp.subscribe('/topic/bet-quantity', function (response) {
            log(response, 'table-info', 'quantity');
        });
    });
}

function disconnect() {
    if (stomp !== null) {
        stomp.disconnect(function () {
            setConnected(false);
            console.log("Client disconnected");
        });
        stomp = null;
    }
}

function send() {
    let betRequest = {
        user: $("#username").val(),
        betPrice: $("#betPrice").val(),
        mail: $("#email").val(),
        phoneNumber: $("#phoneNumber").val()
    }
    $.ajax({
        type: "POST",
        url: "http://localhost:7000/bet/create",
        data: JSON.stringify(betRequest),
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        dataType: "json",
        success: function (data, status, jqXHR) {
            console.log(data);
        },

        error: function (jqXHR, status) {
            // error handler
            console.log(jqXHR.responseJSON);
            let response = jqXHR.responseJSON;
            alert('Error: ' + response.message);
        }
    });
}

function log(response, clazz, type) {
    console.log('log')
    if (type === 'new') {
        let data = JSON.parse(response.body);
        $("#responses").prepend("<tr class='" + clazz + "'><td><b>Round number " + data['id'] + " started</b></td><td>Actual Price: <b>" + data['initialBtcPrice'] + "</b></td></tr>");
        $("#send").prop("disabled", false);
        setTimeout(function () {
            console.log("setTimeout");
            $("#send").prop("disabled", true);
        }, Number(data['durationForBetsInSeconds']) * 1000);
    }
    if (type === 'end') {
        $("#betsNumber").html("<b>Quantity of bets: 0</b>");
        let data = JSON.parse(response.body);
        if (data.length === 0) {
            $("#responses").prepend("<tr class='table-success'><td>Without participants.</td><td></td></tr>");
            $("#responses").prepend("<tr class='table-success'><td>Round finished</td><td></td></tr>");
        } else {
            console.log(JSON.stringify(data));
            for (let i = 0; i < data.length; i++) {
                let winnerList = "";
                if (i == data.length - 1) {
                    $("#responses").prepend("<tr class='table-success'><td>The winners are:</td><td>" + data[i]['user'] + "</td></tr>");
                    $("#responses").prepend("<tr class='table-success'><td>Round finished - " + data[i]['roundOfBetId'] + "</td><td></td></tr>");
                } else {
                    $("#responses").prepend("<tr class='table-success'><td></td><td>" + data[i]['user'] + "</td></tr>");
                }
            }
        }
    }
    if (type === 'quantity') {
        $("#betsNumber").html("<b>" + response.body + "</b>");
    }

}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#send").click(function () {
        send();
    });
});

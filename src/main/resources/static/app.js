var app = (function () {

    var IdBlueprint=null;
    class Point{
        constructor(x,y){
            this.x=x;
            this.y=y;
        }        
    }

    var stompClient = null;

    var addPointToCanvas = function (point) {
        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");
        ctx.beginPath();
        ctx.arc(point.x, point.y, 1, 0, 2 * Math.PI);
        ctx.stroke();
    };

    var poligono = function (points) {
        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");
        ctx.beginPath();
        ctx.moveTo(points[0].x,points[0].y);
        for (var i = 1;i < points.length;i++){
            ctx.lineTo(points[i].x,points[i].y);
        }
        ctx.closePath();
        ctx.fill();
        ctx.stroke();
    };

    var makePoint = function (canvas,event) {
        var ctx = canvas.getContext("2d");
        var rect = canvas.getBoundingClientRect();
        var coorX= event.clientX - rect.left;
        var coorY= event.clientY - rect.top;
        var point = new Point(coorX,coorY);
        stompClient.send("/app/newpoint."+IdBlueprint, {}, JSON.stringify(point));

    }


    
    var getMousePosition = function (evt) {
        canvas = document.getElementById("canvas");
        var rect = canvas.getBoundingClientRect();
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    };


    var connectAndSubscribe = function () {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        
        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/newpoint.'+IdBlueprint, function (eventbody) {
                var x = JSON.parse(eventbody.body).x;
                var y = JSON.parse(eventbody.body).y;
                var point = new Point(x,y);
                addPointToCanvas(point);
            });
            stompClient.subscribe('/topic/newpolygon.'+IdBlueprint, function (message) {
                var puntos = JSON.parse(message.body);
                poligono(puntos);
            });
        });
    };

    return {


        init: function () {
            var can = document.getElementById("canvas");
            //websocket connection
            //connectAndSubscribe();
            if (window.PointerEvent) {
                canvas.addEventListener("pointerdown", function (e) {
                    makePoint(can, e);
                });
            } else {
                //Provide fallback for user agents that do not support Pointer Events
                canvas.addEventListener("mousedown", function (e) {
                    makePoint(can, e);
                });
            }

        },

        publishPoint: function (px, py) {
            var pt = new Point(px, py);
            console.info("publishing point at " + pt);
            addPointToCanvas(pt);
        },

        disconnect: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            IdBlueprint=null;
            console.log("Disconnected");
        },

        connect: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            IdBlueprint= document.getElementById("idBlueprint").value;
            connectAndSubscribe();
            var canvas = document.getElementById("canvas");
            var c = canvas.getContext("2d");
            c.clearRect(0, 0, canvas.width, canvas.height);
            c.beginPath();
        }
    };
})();

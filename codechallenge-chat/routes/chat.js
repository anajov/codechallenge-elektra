var express = require('express');
var router = express.Router();
var http = require('http');
var socketIO = require('socket.io');

router.attachServerToSocketIO = function (server) {
    router.io = socketIO.listen(server);
};

router.route('/').get(function (req, res, next) {
    res.render('chat');
});

router.route('/recent').get(function (req, res, next) {
    var options = {
        host: '192.168.59.103',
        path: '/messages/recent',
        port: '8080'
    };

    callback = function (response) {
        var body = "";
        response.on('data', function (message) {
            body += message;
        });
        response.on('end', function () {
            var jsonString = JSON.parse(body);
            res.json(jsonString);
        });
        response.on('error', function (e) {
            console.log('Problem with request: ' + e.message);
        })
    };

    http.request(options, callback).end();
});

router.route('/names').post(function (req, res, next) {
    var jsonString = '{ "content": "' + req.body.message + '" }';

    //TODO(ajo): JSON.stringify is not safe! Change it.
    var jsonMessage = JSON.stringify(eval('(' + jsonString + ')'));

    var options = {
        host: '192.168.59.103',
        path: '/messages/names',
        port: '8080',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    };

    callback = function (response) {
        var body = "";
        response.on('data', function (message) {
            body += message;
        });
        response.on('end', function () {
            //notify client browsers about the new message
            //so they can update their recent messages
            router.io.sockets.emit('newMessageReceived');

            var jsonString = JSON.parse(body);
            res.json(jsonString);
        });
        response.on('error', function (e) {
            console.log('Problem with request: ' + e.message);
        })
    };

    var postReq = http.request(options, callback);
    postReq.write(jsonMessage);
    postReq.end();
});

module.exports = router;
var express = require('express');
var router = express.Router();
var http = require('http');
var socketIO = require('socket.io');
var constants = require('../global-constants');

router.attachServerToSocketIO = function (server) {
    router.io = socketIO.listen(server);
};

router.route('/').get(function (req, res, next) {
    res.render('chat');
});

router.route('/recent').get(function (req, res, next) {
    var options = {
        host: constants.javaBackendIP,
        path: '/messages/recent',
        port: constants.javaBackendPort
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

    var jsonMessage = JSON.stringify(eval('(' + jsonString + ')'));

    var options = {
        host: constants.javaBackendIP,
        path: '/messages/names',
        port: constants.javaBackendPort,
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
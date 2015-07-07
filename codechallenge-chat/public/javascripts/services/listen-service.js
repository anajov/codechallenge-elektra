var ioClient = require("socket.io-client");

module.exports = function($rootScope) {
    //TODO(ajo): This shouldn't be hard-coded!
    var socket = ioClient.connect('http://localhost:3000');
    return {
        on: function (eventName, callback) {
            socket.on(eventName, function () {
                var args = arguments;
                $rootScope.$apply(function () {
                    callback.apply(socket, args);
                });
            });
        },
        emit: function (eventName, data, callback) {
            socket.emit(eventName, data, function () {
                var args = arguments;
                $rootScope.$apply(function () {
                    if (callback) {
                        callback.apply(socket, args);
                    }
                });
            })
        },
        off: function (eventName) {
            socket.removeAllListeners(eventName);
        }
    };
};
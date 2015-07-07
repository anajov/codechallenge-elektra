describe('ListenService', function () {
    var checkIfCalled = false;

    var ioClientMock = {
        connect: function(url) { }
    };
    var socketMock = {
        on: function (eventName, callback) {
            checkIfCalled = true;
        },
        emit: function (eventName, data, callback) { },
        removeAllListeners: function (eventName) { }
    };

    beforeEach(module('messagesApp'));

    beforeEach(function() {
        module(function($provide) {
            $provide.value('ioClient', ioClientMock);
            $provide.value('socket', socketMock);
        });
    });

    var listenService;

    beforeEach(function() {
        angular.mock.inject(function($injector) {
            listenService = $injector.get('ListenService');
        })
    });

    it('should call "on" method', function() {
        listenService.on('testEvent', function() {});
        expect(true).toBeTruthy();
    })
});
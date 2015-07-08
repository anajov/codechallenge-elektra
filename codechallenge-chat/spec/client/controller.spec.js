describe('MessageController', function () {
    var fakeMessagesJson = {
        'messageCount': 2,
        'lastMessage': '2015-07-01T15:26:29Z',
        'messages': [
            {'message': {'content': 'fake message 1'}},
            {'message': {'content': 'fake message 2'}}
        ]
    };

    var fakeContent = 'fake';

    var messagesServiceMock = {
        postMessage: function (content, callback) {
            callback(fakeContent);
        },
        getRecentMessages: function (callback) {
            callback(fakeMessagesJson);
        }
    };

    var listenServiceMock = {
        on: function (eventName, callback) {
            callback();
        }
    };

    beforeEach(module('messagesApp'));

    beforeEach(function () {
        module(function ($provide) {
            $provide.value('MessagesService', messagesServiceMock);
            $provide.value('ListenService', listenServiceMock);
        });
    });

    var controller, scope;

    beforeEach(function () {
        spyOn(listenServiceMock, 'on');
    });

    beforeEach(inject(function ($rootScope, $controller, MessagesService, ListenService) {
        scope = $rootScope.$new();
        controller = $controller('MessagesController', {
            '$scope': scope,
            'MessagesService': MessagesService,
            'ListenService': ListenService
        });
    }));

    it('should call postMessage from MessageService mock', function () {
        scope.message = 'testing';
        scope.postMessage();
        expect(scope.message).toEqual(fakeContent);
    });

    it('should call getRecentMessages from MessageService mock', function () {
        scope.getRecentMessages();
        expect(scope.messages.messageCount).toEqual(fakeMessagesJson.messageCount);
        expect(scope.messages.lastMessage).toEqual(fakeMessagesJson.lastMessage);
        expect(scope.messages.messages).toEqual(fakeMessagesJson.messages);
    });

    it('should have called "on" method from ListenService mock', function () {
        expect(listenServiceMock.on).toHaveBeenCalled();
    })

});
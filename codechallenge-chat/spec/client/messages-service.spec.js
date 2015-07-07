describe('MessageService', function () {
    beforeEach(module('messagesApp'));

    var httpBackendMock, service;

    beforeEach(inject(function ($injector) {
        httpBackendMock = $injector.get('$httpBackend');
        service = $injector.get('MessagesService');
    }));

    it('should send a POST request', function () {
        var content = 'test';
        httpBackendMock.expectPOST('/messages/names', {message: content})
            .respond({'message': {'content': 'ana'}});

        service.postMessage(content, function (fakeContent) {
            content = fakeContent;
        });

        httpBackendMock.flush();
        expect(content).toEqual('');
    });

    it('should send a GET request', function() {
        var testRecentMessages = {};
        var fakeRecentMessages = {
            'messageCount': 2,
            'lastMessage': '2015-07-01T15:26:29Z',
            'messages': [{
                'message': {
                    'content': 'fake message 1'
                },
                'message': {
                    'content': 'fake message 2'
                }
            }]
        };

        httpBackendMock.expectGET('/messages/recent')
            .respond(fakeRecentMessages);

        service.getRecentMessages(function(recentMessages) {
            testRecentMessages = recentMessages;
        });

        httpBackendMock.flush();
        expect(testRecentMessages).toEqual(fakeRecentMessages.messages);
    })
});
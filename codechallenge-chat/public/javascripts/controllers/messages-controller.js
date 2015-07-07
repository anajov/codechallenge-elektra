'use strict';

module.exports = function ($scope, MessagesService, ListenService) {
    $scope.postMessage = function () {
        MessagesService.postMessage($scope.message, function(content) {
            $scope.message = content;
        });
    };

    $scope.getRecentMessages = function() {
        MessagesService.getRecentMessages(function(recentMessages) {
            $scope.messages = recentMessages;
        });
    };

    ListenService.on('newMessageReceived', function() {
        $scope.getRecentMessages();
    });

    $scope.getRecentMessages();
};
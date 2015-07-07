'use strict';

//sending http request from client browser to Node.js
module.exports = function ($http) {
    return {
        postMessage: function (content, updateMessageField) {
            $http.post('/messages/names', { message: content }).
                success(function(data, status, headers, config) {
                    updateMessageField('');
                }).
                error(function(data, status, headers, config) {
                    console.log('Error occurred. Status code: ' + status);
                    updateMessageField(content);
                });
        },
        getRecentMessages: function (updateRecentMessages) {
            $http.get('/messages/recent').
                success(function(data, status, headers, config) {
                    updateRecentMessages(data.messages);
                }).
                error(function(data, status, headers, config) {
                    console.log('Error occurred. Status code: ' + status);
                    var emptyMessagesArray = [];
                    updateRecentMessages(emptyMessagesArray);
                });
        }
    }
};
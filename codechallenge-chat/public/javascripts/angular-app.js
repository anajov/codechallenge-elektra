var angular = require("angular");

var app = angular.module('messagesApp', []);

app.controller('MessagesController', require('./controllers/messages-controller'));
app.service('MessagesService', require('./services/messages-service'));
app.service('ListenService', require('./services/listen-service'));
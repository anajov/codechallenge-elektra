var nock = require('nock');
var request = require('supertest');
var app = require('../../app');
var chat = require('../../routes/chat');
var hock = require('hock');
var http = require('http');
var constants = require('../../global-constants');

describe('Server testing', function () {
    var javaBackendUrl = 'http://' + constants.javaBackendIP + ':' + constants.javaBackendPort;

    it('should render home page', function (done) {
        request(app)
            .get('/messages')
            .expect(200)
            .end(function (err, res) {
                if (err) {
                    done.fail(err);
                } else {
                    done();
                }
            });
    });

    it('should return recent messages', function (done) {
        var javaBackendMock = nock(javaBackendUrl)
            .get('/messages/recent')
            .reply(200, {
                'messageCount': 2,
                'lastMessage': '2015-07-01T15:26:29Z',
                'messages': [
                    {'message': {'content': 'fake message 1'}},
                    {'message': {'content': 'fake message 2'}}
                ]
            });

        request(app)
            .get('/messages/recent')
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function (err, res) {
                if (err) {
                    console.log(err);
                    done.fail(err);
                } else {
                    done();
                }
            });
    });

    it('should post a message', function (done) {
        var javaBackendMock = nock(javaBackendUrl, {
            reqheaders: {
                'Content-Type': 'application/json'
            }
        })
            .post('/messages/names', {
                'content': 'ana'
            })
            .reply(200, {
                'message': {'content': 'ana'}
            }, {
                'Content-Type' : 'application/json'
            });

        var mock = hock.createHock();
        var server = http.createServer(mock.handler);
        chat.attachServerToSocketIO(server);


        request(app)
            .post('/messages/names')
            .send({message: 'ana'})
            .expect('Content-Type', /json/)
            .expect(200)
            .end(function (err, res) {
                if (err) {
                    console.log(err);
                    done.fail(err);
                } else {
                    done();
                }
            });
    });
});

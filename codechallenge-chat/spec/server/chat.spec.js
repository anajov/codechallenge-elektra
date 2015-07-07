var request = require(['request']);
var baseUrl = 'http://localhost:3000';

/**
 * Dummy test just to try out 'require'
 */
describe('Server testing', function() {
    it('should render the page with status code 200', function () {
        request.get(baseUrl + '/messages', function (error, response, body) {
            expect(response.statusCode).toBe(200);
            done();
        });
    });
});

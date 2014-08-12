describe('oauth2 iframe', function () {

    var $scope, compile, element;

    beforeEach(module('directives.oauth2Iframe'));

    beforeEach(inject(function ($rootScope, $controller, $compile) {
        $scope = $rootScope.$new();
        compile = $compile;
    }));

    it('should be create iframe for oauth2 and add attr ng-src or src', function () {
        element = compile('<oauth2-iframe authorize-url="http://localhost:8081/uaa/oauth/authorize" redirect-url="http://localhost:8090/oauth2.html" client-id="implicit-client" scope="read"></oauth2-iframe>')($scope);
        $scope.$digest();

        var url = element.children().attr('src');
        expect(GetURLParameter(url,'scope')).toBe('read');
        expect(GetURLParameter(url, 'client_id')).toBe('implicit-client');
        expect(GetURLParameter(url,'response_type')).toBe('token');
        expect(GetURLParameter(url,'redirect_uri')).toBe('http%3A%2F%2Flocalhost%3A8090%2Foauth2.html');
    });
});
angular.module('directives.oauth2Iframe', []).directive('oauth2Iframe', function () {
    return {
        template: '<iframe ng-src="{{oauth2Url}}" class="span12" style="border: none"></iframe>',
        restrict:'E',
        link:function (scope, tElement, tAttr, ctrl) {
            scope.state = (Math.floor(1024*1024*1024*Math.random())).toString(36);
            scope.oauth2Url = tAttr['authorizeUrl'] +
                '?redirect_uri=' + encodeURIComponent(tAttr['redirectUrl']) +
                '&client_id=' + encodeURIComponent(tAttr['clientId']) +
                '&scope=' + encodeURIComponent(tAttr['scope']) +
                '&state=' + encodeURIComponent(scope.state) +
                '&response_type=token';
        }
    };
});

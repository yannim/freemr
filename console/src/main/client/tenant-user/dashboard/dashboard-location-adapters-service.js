angular.module('location-adapters', []).factory('LocationAdaptersService', [ '$http', function ($http) {


    var listAdapters = function (locationIdentifier) {
        return $http.get('monitor/locations/' + locationIdentifier + '/adapters/?page.page=0&page.size=10000'
            ).then(function (value) {
                return value.data.content;
            });
    }
    var findLocation = function (locationIdentifier) {
        return $http.get('monitor/locations/' + locationIdentifier + "/").then(function (value) {
            return value.data;
        });
    };


    return {
        listAdapters: listAdapters,
        findLocation: findLocation
    };

}
])
;
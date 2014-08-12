angular.module('monitor-locations', []).factory('MonitorLocationsService', [ '$http', function ($http) {

    var listLocations = function () {
        return $http.get('monitor/locations/?page.page=0&page.size=10000').then(function (value) {
            return value.data.content;
        });
    }

    return {
        listLocations: listLocations
    };

}]);
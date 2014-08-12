angular.module('global-dashboard', []).factory('GlobalDashboardService', [ '$http', function ($http) {
    var listBundleStatus = function () {
        return $http.get('global-monitor/bundles/'
            ).then(function (value) {
                return value.data;
            });
    }
    return {
        listBundleStatus: listBundleStatus
    };

}
]);
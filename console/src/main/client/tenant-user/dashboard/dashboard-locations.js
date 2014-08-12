var MonitorLocationsCtrl = [ '$scope', '$routeParams', 'MonitorLocationsService', 'LocationService', 'LookupTableService', 'TenantService', function ($scope, $routeParams, MonitorLocationsService, LocationService, LookupTableService, TenantService) {
    $scope.locations = MonitorLocationsService.listLocations();
} ];

var MonitorLocationCtrl = [ '$scope', function ($scope) {

    $scope.isAgent = function () {
        return $scope.location.locationType == 'Agent';
    };

} ];

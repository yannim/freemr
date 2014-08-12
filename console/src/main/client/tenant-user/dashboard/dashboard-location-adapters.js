var MonitorLocationAdaptersCtrl = [ '$scope', '$routeParams', 'LocationAdaptersService', function ($scope, $routeParams, LocationAdaptersService) {

    $scope.location = LocationAdaptersService.findLocation($routeParams.locationIdentifier);

    $scope.adapters = LocationAdaptersService.listAdapters($routeParams.locationIdentifier);


} ];

var MonitorLocationAdapterCtrl = [ '$scope', function ($scope) {
    var state = 'viewing';

    $scope.isDetailing = function () {
        return state === 'detailing';
    };

    $scope.isViewing = function () {
        return state === 'viewing';
    };

    $scope.detail = function () {
        state = 'detailing';
    };

    $scope.fold = function () {
        state = 'viewing';
    };

} ];
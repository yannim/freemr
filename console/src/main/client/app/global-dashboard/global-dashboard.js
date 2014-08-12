var GlobalDashboardCtrl = [ '$scope', '$routeParams', 'GlobalDashboardService', function ($scope, $routeParams, GlobalDashboardService) {
    $scope.bundleStatuses = GlobalDashboardService.listBundleStatus();
} ];
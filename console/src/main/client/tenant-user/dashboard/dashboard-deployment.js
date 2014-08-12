var MonitorTenantDeploymentsCtrl = [ '$scope', '$routeParams', 'MonitorDeploymentsService', 'LookupTableService', function ($scope, $routeParams, MonitorTenantDeploymentsService, LookupTableService) {

    $scope.deployments = MonitorTenantDeploymentsService.list();

    $scope.locations = MonitorTenantDeploymentsService.listLocations();

    $scope.lookupTables = LookupTableService.list();

} ];

var MonitorTenantDeploymentCtrl = [ '$scope', 'MonitorDeploymentsService', 'ErrorHandleService', function ($scope, MonitorDeploymentsService, ErrorHandleService) {

    $scope.getLocationName = function (identifier, locations) {
        var n;
        angular.forEach(locations, function (location) {

            if (location.identifier === identifier) {
                n = location.name;
            }
        });
        return n;
    }

    $scope.showEndpointTable = function (protocol, endpoints) {
        var show = false;
        angular.forEach(endpoints, function (endpoint) {
            if (endpoint.indexOf(protocol) != -1) {
                show = true;
            }
        });
        return show;
    }

    $scope.getLookupTableName = function (identifier, lookupTables) {
        var l;
        if (lookupTables) {
            angular.forEach(lookupTables.content, function (lookupTable) {
                if (lookupTable.identifier === identifier) {
                    l = lookupTable.name;
                }
            });
        }
        return l;
    }

    $scope.submit = function (deployment) {
        state = 'updating';
        var resultPromise = MonitorDeploymentsService.changeDeploymentStatus(deployment);

        if (resultPromise) {
            resultPromise.then(function (result) {
                state = 'viewing';
                if (deployment.running) {
                    deployment.running = false;
                } else {
                    deployment.running = true;
                }
            }, function (result) {
                state = 'viewing';
                $scope.errorMessage = result.data.detail;
            });
        }
    };

    $scope.clearError = function () {
        $scope.errorMessage = undefined;
        state = 'viewing';
    }

    var state = 'viewing';

    $scope.isDetailing = function () {
        return state === 'detailing';
    };

    $scope.isUpdating = function () {
        return state === 'updating';
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

    $scope.waitingForActivateConfirmation = function () {
        return state === 'confirmActivate';
    };

    $scope.waitingForDeactivateConfirmation = function () {
        return state === 'confirmDeactivate';
    };

    $scope.activate = function () {
        state = 'confirmActivate';
    };

    $scope.deactivate = function () {
        state = 'confirmDeactivate';
    };

    $scope.cancelConfirm = function () {
        state = 'viewing';
    };

} ];

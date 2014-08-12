var CloudCtrl = [ '$scope', '$routeParams', 'CloudService', 'TenantService', function ($scope, $routeParams, CloudService, TenantService) {
    $scope.tenants = TenantService.listArchived(false);
    $scope.clouds = CloudService.listAll().then(function (data) {
        angular.forEach(data.content, function (cloud) {
            $scope.setTenantName(cloud);
        });
        return data;
    });
    $scope.new = function (clouds) {
        clouds.splice(0, 0, {
            name: '',
            description: '',
            archived: false
        });
    };

    $scope.setTenantName = function(cloud) {
        $scope.tenants.then(function (tenantDatas) {
            angular.forEach(tenantDatas.content, function (tenant) {
                if (cloud.tenantIdentifier === tenant.identifier) {
                    cloud.tenantName = tenant.name;
                }
            });
        });
    }
} ];

var CloudEditCtrl = ['$scope', '$routeParams', 'CloudService', 'ErrorHandleService', function ($scope, $routeParams, CloudService, ErrorHandleService) {
    var state = 'viewing';

    $scope.isEditing = function () {
        return state === 'editing';
    };

    $scope.isViewing = function () {
        return state === 'viewing';
    };

    $scope.edit = function () {
        state = 'editing';
        $scope.l = angular.copy($scope.cloud);
    };

    $scope.waitingForDeleteConfirmation = function () {
        return state === 'confirmDelete';
    };

    $scope.archive = function () {
        state = 'confirmDelete';
    };

    $scope.cancelArchive = function () {
        state = 'viewing';
    };

    $scope.isUpdating = function () {
        return state === 'updating' || state === 'deleting';
    };

    $scope.cancel = function (clouds) {
        if ($scope.l.identifier) {
            state = 'viewing';
            $scope.l = null;
        } else {
            clouds.splice($scope.$index, 1);
        }
    };

    $scope.clearError = function () {
        $scope.errorMessage = null;
        if ($scope.l) {
            state = 'editing';
        } else {
            state = 'viewing';
        }
    };

    $scope.submit = function (clouds, tenants) {
        var resultPromise = null;
        if (state === 'confirmDelete') {
            $scope.archiving = true;
            state = 'updating';
            resultPromise = CloudService.archive($scope.cloud);
        } else if (state === 'editing') {
            state = 'updating';
            resultPromise = CloudService.save($scope.l, $routeParams.tenantIdentifier);
        }

        if (resultPromise) {
            resultPromise.then(function (result) {
                state = 'viewing';

                //is archive
                if ($scope.archiving) {
                    $scope.cloud.archived = true;
                    $scope.archiving = false;
                    return;
                }

                //is create
                if ($scope.l && !$scope.l.identifier) {
                    $scope.l.identifier = result.data;
                    $scope.setTenantName($scope.cloud);
                }

                // is modify
                for (var p in $scope.l) {
                    $scope.cloud[p] = $scope.l[p];
                }
                $scope.l = null;
            }, function (result) {
                state = ErrorHandleService.handle(clouds, !$scope.l, $scope, result);
            });
        }
    };

    if (!$scope.cloud.identifier) {
        $scope.edit();
    }
}];
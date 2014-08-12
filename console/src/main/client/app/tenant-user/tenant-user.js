var TenantUserCtrl = [ '$scope', '$routeParams', 'TenantUserService', 'TenantService', function ($scope, $routeParams, TenantUserService, TenantService) {
    $scope.tenants = TenantService.list();
    $scope.users = TenantUserService.list().then(function (data) {
        angular.forEach(data.content, function (agent) {
            $scope.setTenantName(agent);
        });
        return data;
    });

    $scope.newUser = function (users) {
        users.splice(0, 0, {
            tenantIdentifier: '',
            description: ''
        });
    };

    $scope.setTenantName = function(user) {
        $scope.tenants.then(function (tenantDatas) {
            angular.forEach(tenantDatas.content, function (tenant) {
                if (user.tenantIdentifier === tenant.identifier) {
                    user.tenantName = tenant.name;
                }
            });
        });
    }
} ];

var TenantUserEditCtrl = [ '$scope', 'TenantUserService', 'ErrorHandleService', function ($scope, TenantUserService, ErrorHandleService) {
    var state = 'viewing';

    $scope.isEditing = function () {
        return state === 'editing';
    };

    $scope.isViewing = function () {
        return state === 'viewing';
    };

    $scope.edit = function () {
        state = 'editing';
        $scope.u = angular.copy($scope.user);
    };

    $scope.waitingForDeleteConfirmation = function () {
        return state === 'confirmDelete';
    };

    $scope.isUpdating = function () {
        return state === 'updating' || state === 'deleting';
    };

    $scope.cancel = function (users) {
        if ($scope.u.identifier) {
            state = 'viewing';
            $scope.u = null;
        } else {
            users.splice($scope.$index, 1);
        }
    };

    $scope.clearError = function () {
        $scope.errorMessage = null;
        if ($scope.u) {
            state = 'editing';
        } else {
            state = 'viewing';
        }
    };

    $scope.submit = function (users, tenants) {
        var resultPromise = null;
        if (state === 'editing') {
            resultPromise = TenantUserService.save($scope.u);
        }
        state = 'updating';
        if (resultPromise) {
            resultPromise.then(function (result) {
                state = 'viewing';
                if ($scope.u && !$scope.u.identifier) {
                    $scope.u.identifier = result.data;
                }
                for (var p in $scope.u) {
                    $scope.user[p] = $scope.u[p];
                }
                $scope.setTenantName($scope.user);
                $scope.u = null;
            }, function (result) {
                state = ErrorHandleService.handle(users, !$scope.u, $scope, result);
            });
        }
    };

    if (!$scope.user.identifier) {
        $scope.edit();
    }

} ];
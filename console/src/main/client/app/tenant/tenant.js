var TenantCtrl = [ '$scope', 'TenantService', function ($scope, TenantService) {

    $scope.tenants = TenantService.list();

    $scope.newTenant = function (tenants) {
        tenants.splice(0, 0, {
            name: '',
            archived: false,
            description: ''
        });
    };

} ];

var TenantEditCtrl = [ '$rootScope', '$scope', 'TenantService', 'ErrorHandleService', function ($rootScope, $scope, TenantService, ErrorHandleService) {
    var state = 'viewing';

    $scope.isEditing = function () {
        return state === 'editing';
    };

    $scope.isViewing = function () {
        return state === 'viewing';
    };

    $scope.edit = function () {
        state = 'editing';
        $scope.t = angular.copy($scope.tenant);
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
        return state === 'updating';
    };

    $scope.cancel = function (tenants) {
        if ($scope.t.identifier) {
            state = 'viewing';
            $scope.t = null;
        } else {
            tenants.splice($scope.$index, 1);
        }
    };

    $scope.clearError = function () {
        $scope.errorMessage = null;
        if ($scope.t) {
            state = 'editing';
        } else {
            state = 'viewing';
        }
    };

    $scope.submit = function (tenants) {
        var resultPromise = null;
        if (state === 'confirmDelete') {
            $scope.archiving = true;
            resultPromise = TenantService.archive($scope.tenant);
        } else if (state === 'editing') {
            resultPromise = TenantService.save($scope.t);
        }
        state = 'updating';

        if (resultPromise) {
            resultPromise.then(function (result) {
                state = 'viewing';
                //is archive
                if ($scope.archiving) {
                    $scope.tenant.archived = true;
                    $scope.archiving = false;
                    return;
                }

                // is create
                if ($scope.t && !$scope.t.identifier) {
                    $scope.t.identifier = result.data;
                }

                // is modify
                for (var p in $scope.t) {
                    $scope.tenant[p] = $scope.t[p];
                }
                $scope.t = null;
            }, function (result) {
                state = ErrorHandleService.handle(tenants, !$scope.t, $scope, result);
            });
        }
    };

    $scope.switchRole = function (tenant) {
        $rootScope.switchMenuItem('dashboard', false, '/dashboard', tenant.identifier);
    };

    if (!$scope.tenant.identifier) {
        $scope.edit();
    }

} ];
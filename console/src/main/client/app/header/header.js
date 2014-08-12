var HeaderCtrl = [ '$scope', '$rootScope', '$route', '$http', '$location', 'WorkspaceService', '$dialog', 'TenantService', function ($scope, $rootScope, $route, $http, $location, WorkspaceService, $dialog, TenantService) {
    $scope.menuItems = {};
    $scope.menuItems.tenant = "active";
    $scope.tenantStack = new Array();
    $scope.tenantStackTmp = "";

    $scope.initMenuState = function (value) {
        if (value) {
            clearMenuState();
            if (value.indexOf('app/cloud') != -1) {
                $scope.menuItems.cloud = 'active';
            } else if (value.indexOf('app/agent') != -1) {
                $scope.menuItems.agent = 'active';
            } else if (value.indexOf('app/node-definition') != -1) {
                $scope.menuItems.resources = 'active';
            } else if (value.indexOf('app/tenant-user') != -1) {
                $scope.menuItems.resources = 'active';
            } else if (value.indexOf('tenant-user/workspace-deployment') != -1) {
                $scope.menuItems.workspace = 'active';
            } else if (value.indexOf('tenant-user/credential-store/') != -1 || value.indexOf('tenant-user/lookup-table') != -1) {
                $scope.menuItems.resources = 'active';
            } else if (value.indexOf('tenant-user/project') != -1 || value.indexOf('tenant-user/lookup-table') != -1 || value.indexOf('usable-adapter') != -1) {
                $scope.menuItems.resources = 'active';
            } else if (value.indexOf('tenant-user/adapter-configuration') != -1) {
                $scope.menuItems.adapter = 'active';
            } else if (value.indexOf('tenant-user/agent-group') != -1) {
                $scope.menuItems.resources = 'active';
            } else if (value.indexOf('tenant-user/agent-group-member') != -1) {
                $scope.menuItems.resources = 'active';
            } else if (value.indexOf('tenant-user/dashboard') != -1) {
                $scope.menuItems.dashboard = 'active';
            } else if (value.indexOf('app/available-adapter-definition') != -1) {
                $scope.menuItems.resources = 'active';
            } else if (value.indexOf('app/adapter-definition') != -1) {
                $scope.menuItems.resources = 'active';
            } else if(value.indexOf('app/global-dashboard')!=-1){
                $scope.menuItems.globalDashboard = 'active';
            }  else if (value.indexOf('app/tenant-protocol-adapter') != -1) {
                $scope.menuItems.resources = 'active';
            } else {
                $scope.menuItems.tenant = "active";
            }
        }
    }

    $rootScope.runAsTenant = false;

    $scope.$watch(function () {
        return $route.current.templateUrl;
    }, function (newValue, oldValue) {
        $scope.initMenuState($route.current.templateUrl);
    });

    TenantService.querySecurity().then(function (data) {
        $rootScope.runAsTenant = data.tenantIdentifier != null;
        $rootScope.loginedTenant = data;
        if (data.tenantIdentifier) {
            $scope.workspaces = WorkspaceService.list();
            $scope.tenantStack.splice(0, 0, data.tenantIdentifier);
        }
    });

    $rootScope.switchToTenant = function (tenantIdentifier) {
        if (!tenantIdentifier && !$rootScope.runAsTenant) {
            tenantIdentifier = $scope.tenantStack.splice(0, 1);
            if (tenantIdentifier.length == 0) {
                tenantIdentifier = $scope.tenantStackTmp;
            } else {
                $scope.tenantStackTmp = tenantIdentifier;
            }
            return $http.get("tenants/run-as-tenant/" + tenantIdentifier + "/").then(function () {
                $rootScope.runAsTenant = true;
                $scope.workspaces = WorkspaceService.list();
                $rootScope.loginedTenant = TenantService.querySecurity();
            });
        } else if (tenantIdentifier) {
            $scope.tenantStack.splice(0, 0, tenantIdentifier);
            return $http.get("tenants/run-as-tenant/" + tenantIdentifier + "/").then(function () {
                $rootScope.runAsTenant = true;
                $scope.workspaces = WorkspaceService.list();
                $rootScope.loginedTenant = TenantService.querySecurity();
            });
        }
    }

    $rootScope.switchToAdmin = function () {
        return $http.get("tenants/run-as-admin/").then(function () {
            $rootScope.runAsTenant = false;
        });
    }

    function clearMenuState() {
        for (var item in $scope.menuItems) {
            $scope.menuItems[item] = '';
        }
    }

    $rootScope.switchMenuItem = function (itemId, switchToAdmin, path, tenantIdentifier) {
        var resultPromise;
        if (switchToAdmin && $rootScope.runAsTenant) {
            resultPromise = $rootScope.switchToAdmin();
        } else if (!switchToAdmin && !$rootScope.runAsTenant) {
            resultPromise = $rootScope.switchToTenant(tenantIdentifier);
        }

        if (resultPromise) {
            resultPromise.then(function (data) {
                $location.path(path);
            });
        } else {
            $location.path(path);
        }
    }

    $scope.logout = function () {
        window.location.href = "logout";
    };

    $scope.openDialog = function () {
        $rootScope.workspaceToEdit = {};
        var d = $dialog.dialog({
            backdrop: true,
            keyboard: true,
            dialogFade: true,
            backdropClick: false,
            templateUrl: 'tenant-user/workspace/workspace.edit.template.html',
            controller: 'WorkspaceEditCtrl'
        });
        d.open().then(function () {
        });
    };

    $rootScope.$on("workspaceAddedEvent", function (event, workspace) {
        if (!$scope.workspaces || !$scope.workspaces.then) {
            $scope.workspaces = {};
            $scope.workspaces.splice(0, 0, workspace);
            return;
        }
        $scope.workspaces.then(function (datas) {
            datas.splice(0, 0, workspace);
        });
    });

    $rootScope.$on("workspaceModifiedEvent", function (event, workspace) {
        if (!$scope.workspaces || !$scope.workspaces.then) {
            angular.forEach($scope.workspaces, function (data) {
                if (data.identifier === workspace.identifier) {
                    data.name = workspace.name;
                    data.description = workspace.description;
                }
            });
            return;
        }
        $scope.workspaces.then(function (datas) {
            angular.forEach(datas, function (data) {
                if (data.identifier === workspace.identifier) {
                    data.name = workspace.name;
                    data.description = workspace.description;
                }
            });
            return datas;
        });
    });

    $rootScope.$on("workspaceArchivedEvent", function (event, workspace) {
        if (!$scope.workspaces || !$scope.workspaces.then) {
            var i = 0;
            angular.forEach($scope.workspaces, function (data) {
                if (data.identifier === workspace.identifier) {
                    data.archived = true;
                    $scope.workspaces.splice(i, 1);
                }
                i++;
            });
            return;
        }
        $scope.workspaces.then(function (datas) {
            var i = 0;
            angular.forEach(datas, function (data) {
                if (data.identifier === workspace.identifier) {
                    data.archived = true;
                    datas.splice(i, 1);
                }
                i++;
            });
            return datas;
        });
    });
} ];
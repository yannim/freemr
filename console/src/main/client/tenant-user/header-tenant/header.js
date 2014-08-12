var TenantHeaderCtrl = [ '$scope', '$rootScope', '$route', 'WorkspaceService', '$dialog', function ($scope, $rootScope, $route, WorkspaceService, $dialog) {
    $scope.menuItems = {};
    $scope.menuItems.dashboard = "";

    function clearMenuState() {
        for (var item in $scope.menuItems) {
            $scope.menuItems[item] = '';
        }
    }

    $scope.initState = function (value) {
        if (value) {
            clearMenuState();
            if (value.indexOf('tenant-user/workspace-deployment') != -1) {
                $scope.menuItems.workspace = 'active';
            } else if (value.indexOf('tenant-user/credential-store/') != -1 || value.indexOf('tenant-user/lookup-table') != -1) {
                $scope.menuItems.resources = 'active';
            } else if (value.indexOf('tenant-user/project') != -1) {
                $scope.menuItems.resources = 'active';
            } else if (value.indexOf('tenant-user/adapter-configuration') != -1) {
                $scope.menuItems.adapter = 'active';
            } else if (value.indexOf('tenant-user/agent-group') != -1) {
                $scope.menuItems.resources = 'active';
            } else if (value.indexOf('tenant-user/agent-group-member') != -1) {
                $scope.menuItems.resources = 'active';
            } else if (value.indexOf('tenant-user/usable-adapter') != -1) {
                $scope.menuItems.resources = 'active';
            } else if ($route.current.templateUrl.indexOf('tenant-user/dashboard') != -1) {
                $scope.menuItems.dashboard = 'active';
            } else {
                $scope.menuItems.dashboard = 'active';
            }
        }
    }
    $scope.$watch(function () {
        return $route.current.templateUrl;
    }, function (newValue, oldValue) {
        $scope.initState($route.current.templateUrl);
    });
    $scope.logout = function () {
        window.location.href = "logout";
    };

    $scope.workspaces = WorkspaceService.list();

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
        $scope.workspaces.then(function (datas) {
            datas.splice(0, 0, workspace);
        });
    });

    $rootScope.$on("workspaceModifiedEvent", function (event, workspace) {
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
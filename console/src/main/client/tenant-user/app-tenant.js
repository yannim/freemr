angular.module('app-tenant', [ 'ui', 'ui.bootstrap', 'ui.bootstrap.modal', 'templates', 'directives.jasny-bootstrap', 'errorHandle', 'ngSanitize', 'compile', 'credential-store' , 'workspace', 'workspace-deployment', 'tenant',
    'project', 'project-deployment', 'location', 'lookup-table', 'lookup-table-entry', 'adapter-configuration', 'usable-adapter', 'usable-adapter', 'adapter-configuration', 'agent-group', 'agent-group-member', 'monitor-deployments',
    'monitor-locations', 'location-adapters','monitor-deployment-endpoints']);
angular.module('app-tenant').config([ '$routeProvider', function ($routeProvider) {
    $routeProvider.when('/tenant', {
        templateUrl: 'app/tenant/tenant.template.html',
        controller: TenantCtrl
    }).when('/workspaces/:workspaceIdentifier/deployments', {
            templateUrl: 'tenant-user/workspace-deployment/template.html',
            controller: WorkspaceDeploymentsCtrl
        }).when('/credentials', {
            templateUrl: 'tenant-user/credential-store/credential-store.template.html',
            controller: CredentialStoreCtrl
        }).when('/lookup-tables', {
            templateUrl: 'tenant-user/lookup-tables/lookup-table.template.html',
            controller: LookupTableCtrl
        }).when('/lookup-tables/:lookupTableIdentifier/entries', {
            templateUrl: 'tenant-user/lookup-table-entry/lookup-table-entry.template.html',
            controller: LookupTableEntryCtrl
        }).when('/projects', {
            templateUrl: 'tenant-user/project/project.template.html',
            controller: ProjectCtrl
        }).when('/projects/:projectIdentifier/deployments', {
            templateUrl: 'tenant-user/project-deployment/project-deployment.template.html',
            controller: ProjectDeploymentCtrl
        }).when('/usable-adapters', {
            templateUrl: 'tenant-user/usable-adapter/usable-adapter.template.html',
            controller: UsableAdapterCtrl
        }).when('/adapter-configurations', {
            templateUrl: 'tenant-user/adapter-configuration/adapter-configuration.template.html',
            controller: AdapterConfigurationCtrl
        }).when('/agent-group', {
            templateUrl: 'tenant-user/agent-group/agent-group.template.html',
            controller: AgentGroupCtrl
        }).when('/agent-group/:groupIdentifier/members', {
            templateUrl: 'tenant-user/agent-group-member/agent-group-member.template.html',
            controller: AgentGroupMemberCtrl
        }).when('/dashboard', {
            templateUrl: 'tenant-user/dashboard/dashboard.template.html'
        }).when('/monitor/locations/:locationIdentifier/adapters', {
            templateUrl: 'tenant-user/dashboard/dashboard-location-adapters.template.html',
            controller: MonitorLocationAdaptersCtrl
        }).when('/dashboard/deployments/:deploymentIdentifier/endpoints', {
            templateUrl: 'tenant-user/dashboard-deployment-endpoint/dashboard-deployment-endpoints.template.html',
            controller: MonitorTenantDeploymentEndpointsCtrl
        }).otherwise({
            redirectTo: '/dashboard'
        });
} ]);

angular.module('app-tenant').filter('newLineToBreak', function () {
    return function (information, html) {
        if (information) {
            return information.replace(new RegExp("\\n", "gi"), "<br>");
        } else {
            return information;
        }
    };
});

angular.module('app-tenant').config([ '$httpProvider', function ($httpProvider) {
    $httpProvider.responseInterceptors.push([ '$q', function ($q) {
        return function (promise) {
            return promise.then(function (response) {
                return response;
            }, function (response) {
                if (response.status == 401) {
                    window.location.reload(true);
                    return;
                }
                return $q.reject(response);
            });
        };
    } ]);
} ]);
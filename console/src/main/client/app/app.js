angular.module('app', [ 'ui', 'ui.bootstrap', 'ui.bootstrap.modal', 'templates', 'directives.jasny-bootstrap', 'tenant', 'workspace',
    'agent', 'workspace-deployment', 'location', 'ngSanitize', 'errorHandle', 'credential-store', 'agent-certificate',
    'node-definition', 'available-adapter-definition', 'adapter-definition', 'compile', "tenant-protocol-adapter", "tenant-user", "value-comparator", "cloud",
    'project', 'project-deployment', 'location', 'lookup-table', 'lookup-table-entry', 'adapter-configuration', 'usable-adapter', 'usable-adapter', 'adapter-configuration', 'agent-group', 'agent-group-member',
    'monitor-deployments', 'monitor-locations', 'location-adapters', 'monitor-deployment-endpoints','global-dashboard']);

var runAsAdmin = ['$http', '$rootScope', function ($http, $rootScope) {
    if ($rootScope.runAsTenant) {
        return $http.get("tenants/run-as-admin/").then(function () {
            $rootScope.runAsTenant = false;
        });
    }
}];

var runAsTenant = ['$rootScope', function ($rootScope) {
    if ($rootScope.switchToTenant) {
        return $rootScope.switchToTenant();
    }
}];

angular.module('app').config([ '$routeProvider', function ($routeProvider) {
    $routeProvider.when('/tenant', {
        templateUrl: 'app/tenant/tenant.template.html',
        controller: TenantCtrl,
        resolve: {
            runAsAdmin: runAsAdmin
        }
    }).when('/agent', {
            templateUrl: 'app/agent/agent.template.html',
            controller: AgentCtrl,
            resolve: {
                runAsAdmin: runAsAdmin
            }
        }).when("/agent/:agentIdentifier/certificate", {
            templateUrl: 'app/agent-certificate/agent-certificate.template.html',
            controller: AgentCertificateCtrl,
            resolve: {
                runAsAdmin: runAsAdmin
            }
        }).when('/workspaces/:tenantIdentifier/:workspaceName/deployments', {
            templateUrl: 'app/workspace-deployment/workspace-deployment.template.html',
            controller: WorkspaceDeploymentCtrl,
            resolve: {
                runAsAdmin: runAsAdmin
            }
        }).when("/tenants/:tenantIdentifier/credential-stores", {
            templateUrl: 'app/credential-store/credential-store.template.html',
            controller: CredentialStoreCtrl,
            resolve: {
                runAsAdmin: runAsAdmin
            }
        }).when("/node-definition", {
            templateUrl: 'app/node-definition/node-definition.template.html',
            controller: NodeDefinitionCtrl,
            resolve: {
                runAsAdmin: runAsAdmin
            }
        }).when("/adapter-definition", {
            templateUrl: 'app/adapter-definition/adapter-definition.template.html',
            controller: AdapterDefinitionCtrl,
            resolve: {
                runAsAdmin: runAsAdmin
            }
        }).when("/available-adapter-definition", {
            templateUrl: 'app/available-adapter-definition/available-adapter-definition.template.html',
            controller: AvailableAdapterDefinitionCtrl,
            resolve: {
                runAsAdmin: runAsAdmin
            }
        }).when("/created-adapters/:adapterName/tenants", {
            templateUrl: 'app/tenant-protocol-adapter/tenant-protocol-adapter.template.html',
            controller: TenantProtocolAdapterCtrl,
            resolve: {
                runAsAdmin: runAsAdmin
            }
        }).when("/tenant-user", {
            templateUrl: 'app/tenant-user/tenant-user.template.html',
            controller: TenantUserCtrl,
            resolve: {
                runAsAdmin: runAsAdmin
            }
        }).when("/cloud", {
            templateUrl: 'app/cloud/cloud.template.html',
            controller: CloudCtrl,
            resolve: {
                runAsAdmin: runAsAdmin
            }
        }).when("/tenant-user/tenant", {
            templateUrl: 'app/tenant/tenant.template.html',
            controller: TenantCtrl,
            resolve: {
                runAsAdmin: runAsAdmin
            }
        }).when('/global-dashboard', {
            templateUrl: 'app/global-dashboard/global-dashboard.template.html',
            controller: GlobalDashboardCtrl,
            resolve: {
                runAsAdmin: runAsAdmin
            }
        }).when('/workspaces/:workspaceIdentifier/deployments', {//tenant user begin
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
            controller: ProjectCtrl,
            resolve: {
                runAsTenant: runAsTenant
            }
        }).when('/projects/:projectIdentifier/deployments', {
            templateUrl: 'tenant-user/project-deployment/project-deployment.template.html',
            controller: ProjectDeploymentCtrl,
            resolve: {
                runAsTenant: runAsTenant
            }
        }).when('/usable-adapters', {
            templateUrl: 'tenant-user/usable-adapter/usable-adapter.template.html',
            controller: UsableAdapterCtrl,
            resolve: {
                runAsTenant: runAsTenant
            }
        }).when('/adapter-configurations', {
            templateUrl: 'tenant-user/adapter-configuration/adapter-configuration.template.html',
            controller: AdapterConfigurationCtrl,
            resolve: {
                runAsTenant: runAsTenant
            }
        }).when('/agent-group', {
            templateUrl: 'tenant-user/agent-group/agent-group.template.html',
            controller: AgentGroupCtrl,
            resolve: {
                runAsTenant: runAsTenant
            }
        }).when('/agent-group/:groupIdentifier/members', {
            templateUrl: 'tenant-user/agent-group-member/agent-group-member.template.html',
            controller: AgentGroupMemberCtrl,
            resolve: {
                runAsTenant: runAsTenant
            }
        }).when('/dashboard', {
            templateUrl: 'tenant-user/dashboard/dashboard.template.html',
            resolve: {
                runAsTenant: runAsTenant
            }
        }).when('/monitor/locations/:locationIdentifier/adapters', {
            templateUrl: 'tenant-user/dashboard/dashboard-location-adapters.template.html',
            controller: MonitorLocationAdaptersCtrl,
            resolve: {
                runAsTenant: runAsTenant
            }
        }).when('/dashboard/deployments/:deploymentIdentifier/endpoints', {
            templateUrl: 'tenant-user/dashboard-deployment-endpoint/dashboard-deployment-endpoints.template.html',
            controller: MonitorTenantDeploymentEndpointsCtrl,
            resolve: {
                runAsTenant: runAsTenant
            }
        }).otherwise({
            redirectTo: '/tenant'
        });
} ]);

angular.module('app').filter('newLineToBreak', function () {
    return function (information, html) {
        if (information) {
            return information.replace(new RegExp("\\n", "gi"), "<br>");
        } else {
            return information;
        }
    };
});

angular.module('app').config([ '$httpProvider', function ($httpProvider) {
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

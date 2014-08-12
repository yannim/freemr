angular.module('tenant', []).factory('TenantService', [ '$http', function ($http) {

    var get = function (tenantIdentifier) {
        return $http.get('tenants/' + tenantIdentifier).then(function (value) {
            return value.data;
        });
    };

    var querySecurity = function () {
        return $http.get('tenants/query-security/').then(function (value) {
            return value.data;
        });
    }

    var list = function () {
        return $http.get("tenants/?page.page=0&page.size=10000&page.sort=name&page.sort.dir=ASC").then(function (value) {
            return angular.fromJson(value.data);
        });
    };

    var archive = function (tenant) {
        return $http({
            method: 'DELETE',
            url: 'tenants/' + tenant.identifier
        });
    };

    var save = function (tenant) {
        var method = 'PUT';
        if (!tenant.identifier) {
            method = 'POST';
        }
        var tenantTmp = angular.copy(tenant);
        delete tenantTmp.archived;
        if (tenantTmp.identifier) {
            delete tenantTmp.identifier;
        }
        return $http({
            method: method,
            url: 'tenants/' + (tenant.identifier || ''),
            data: tenantTmp
        });
    };

    var listArchived = function (isArchived) {
        return $http.get("tenants/archived/" + isArchived + "/?page.page=0&page.size=10000&page.sort=name&page.sort.dir=ASC").then(function (value) {
            return angular.fromJson(value.data);
        });
    }

    return {
        get: get,
        list: list,
        save: save,
        archive: archive,
        listArchived: listArchived,
        querySecurity: querySecurity
    };
} ]);
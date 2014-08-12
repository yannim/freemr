angular.module('tenant-user', []).factory('TenantUserService', [ '$http', function ($http) {

    var list = function (tenantIdentifier) {
        return $http.get("tenant-users/?page.page=0&page.size=10000&page.sort=account&page.sort.dir=ASC").then(function (value) {
            return angular.fromJson(value.data);
        });
    };

    var save = function (user) {
        var method = 'POST';
        var userTmp = angular.copy(user);
        delete userTmp.confirmPassword;
        return $http({
            method: method,
            url: 'tenant-users/',
            data: userTmp
        });
    };

    return {
        list: list,
        save: save
    };
} ]);
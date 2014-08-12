angular.module('cloud', []).factory('CloudService', [ '$http', function ($http) {
    var get = function (cloudIdentifier) {
        return $http.get('clouds/' + cloudIdentifier + "/").then(function (value) {
            return value.data;
        })
    }

    var listAll = function () {
        return $http.get("clouds/?page.page=0&page.size=10000&page.sort=name&page.sort.dir=ASC").then(function (value) {
                return value.data;
            }
        );
    };

    var archive = function (cloud) {
        return $http({
            method: 'DELETE',
            url: 'clouds/' + cloud.identifier + "/"
        });
    }

    var save = function (cloud, tenantIdentifier) {
        var method = 'PUT';
        var url = 'clouds/' + (cloud.identifier || '') + "/";
        if (!cloud.identifier) {
            method = 'POST';
        }
        var cloudTmp = angular.copy(cloud);
        delete cloudTmp.tenantName;
        delete cloudTmp.archived;
        delete cloudTmp.tenantArchived;
        if (cloudTmp.identifier) {
            delete cloudTmp.identifier;
            delete cloudTmp.tenantIdentifier;
        }

        return $http({
            method: method,
            url: url,
            data: cloudTmp
        });
    };

    return {
        get: get,
        listAll: listAll,
        save: save,
        archive: archive
    };
} ])
;
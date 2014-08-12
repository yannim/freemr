angular.module('monitor-deployments', []).factory('MonitorDeploymentsService', [ '$http', function ($http) {

    var list = function () {
        return $http.get('monitor/deployments/?page.page=0&page.size=10000&page.sort=name&page.sort.dir=ASC'
            ).then(function (value) {
                return value.data.content;
            });
    };
    var listLocations=function(){
        return $http.get('locations/?page.page=0&page.size=10000&page.sort=name&page.sort.dir=ASC').then(function (value) {
            return value.data.content;
        });
    }

    var changeDeploymentStatus = function (deployment) {
        var url = "";
        var method = "PUT";
        if (deployment.running) {
            method = "DELETE";
            url = "monitor/deployments/" + deployment.id + "/";
        } else {
            method = "PUT";
            url = "monitor/deployments/" + deployment.id + "/";
        }
        return $http({
            method: method,
            url: url
        });
    }



    var get = function (deploymentIdentifier) {
        return $http.get('monitor/deployments/' + deploymentIdentifier + "/").then(function (value) {
            return value.data;
        });
    };

    return {
        list: list,
        get: get,
        changeDeploymentStatus: changeDeploymentStatus,
        listLocations:listLocations
    };

}
])
;
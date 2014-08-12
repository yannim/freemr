angular.module('errorHandle', []).factory('ErrorHandleService', function () {
    var handle = function (datas, isDelete, scope, result) {
        var state = "viewing";
        if (result.status == 410) {
            if (isDelete) {
                datas.splice(scope.index, 1);
                return 'viewing';
            }
        }
        if (!result.data.detail) {
            scope.errorMessage = "You submitted a invalid data!";
        } else {
            scope.errorMessage = result.data.detail;
        }
        return "error";
    }
    return {
        handle: handle
    };
});
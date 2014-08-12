angular.module('directives.jasny-bootstrap', []).directive('fileuploader', function () {
    return {
        template: '<form action="{{url}}" method="post" enctype="multipart/form-data" class="{{layoutClass}} {{spanClass}}"><div class="alert alert-error" ng-show="error"><button type="button" class="close" ng-click="error=undefined">&times;</button><p>{{error}}</p></div><div ng-show="!error&&!uploadPercent" class="fileupload fileupload-new {{layoutClass}}" data-provides="fileupload"><div class="input-append"><div class="uneditable-input {{spanClass}}"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div><span class="btn btn-file"><span class="fileupload-new">{{selectFileLabel}}</span><span class="fileupload-exists">Change</span><input type="file" name="file" accept="{{accept}}" /></span><button type="button" class="btn fileupload-exists" ng-click="upload()">Upload</button><a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Remove</a></div></div><div ui-if="uploadPercent" class="progress"><div class="bar" style="width:{{uploadPercent}}"></div><div class="percent">{{uploadPercent}}</div></div></form>',
        replace: true,
        scope: true,
        restrict: 'E',
        link: function (scope, tElement, tAttr) {
            scope.selectFileLabel = tAttr.selectfilelabel;
            scope.layoutClass = tAttr.layoutclass;
            scope.spanClass = tAttr.spanclass;
            scope.accept = tAttr.accept;
            tAttr.$observe('url', function (value) {
                scope.url = tAttr.url;
            });
            scope.upload = function uploadFile() {
                var filename = tElement[0].file.value;
                if (filename.substring(filename.length - scope.accept.length) != scope.accept) {
                    scope.error = "Project must be a jar";
                    return;
                }
                $(tElement[0]).ajaxSubmit({
                        beforeSend: function () {
                            scope.uploadPercent = '0%';
                        },
                        uploadProgress: function (event, position, total, percentComplete) {
                            if (percentComplete < 90) {
                                scope.uploadPercent = percentComplete + '%';
                                scope.$digest();
                            }
                        },
                        dataType: "json",
                        success: function (data) {
                            var percentComplete = 90;
                            var setProgress = function () {
                                if (percentComplete < 100) {
                                    percentComplete = percentComplete + 2;
                                    scope.uploadPercent = percentComplete + '%';
                                    scope.$digest();
                                }
                            };
                            var resetFunction = function () {
                                clearInterval(interval);
                                $('.fileupload').fileupload('reset');
                                delete scope.uploadPercent;
                                scope.data = data;
                                if (tAttr.onsuccess) {
                                    scope.$eval(tAttr.onsuccess);
                                    scope.$parent.$digest();
                                }
                            };
                            var interval = setInterval(setProgress, 1000);
                            setTimeout(resetFunction, 5000);
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            delete scope.uploadPercent;
                            try {
                                scope.error = $.parseJSON(jqXHR.responseText).detail;
                            } catch (e) {
                                scope.error = 'Upload failed: ' + jqXHR.status + ' ' + jqXHR.statusText;
                            }
                            scope.$digest();
                        }
                    }
                );
            }
        }
    };
});
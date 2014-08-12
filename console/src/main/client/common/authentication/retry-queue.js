angular.module('authentication.retryQueue', []).factory('AuthenticationRetryQueue', [ '$q', function($q) {
	var retryQueue = [];
	var service = {
		push : function(retryItem) {
			retryQueue.push(retryItem);
		},
		pushPromiseFn : function(promiseFn, reason) {
			var deferred = $q.defer();
			var retryItem = {
				reason : reason,
				retry : function() {
					promiseFn().then(function(value) {
						deferred.resolve(value);
					});
				},
				cancel : function() {
					deferred.reject();
				}
			};
			service.push(retryItem);
			return deferred.promise;
		},
		hasMore : function() {
			return retryQueue.length > 0;
		},
		getReason : function() {
			if (service.hasMore()) {
				return retryQueue[0].reason;
			}
		},
		getNext : function() {
			return retryQueue.shift();
		},
		cancel : function() {
			while (service.hasMore()) {
				service.getNext().cancel();
			}
		},
		retry : function() {
			while (service.hasMore()) {
				service.getNext().retry();
			}
		}
	};
	return service;
} ]);
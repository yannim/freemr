/* This http interceptor listens for authentication failures and broadcast unauthorized event if not logged in */
angular.module('authentication.interceptor', []).factory('AuthenticationInterceptor', ['$rootScope', '$q', '$injector', 'AuthenticationRetryQueue', function($rootScope, $q, $injector, queue) {
	  var $http; // To be lazy initialized to prevent circular dependency
	  return function(promise) {
	    $http = $http || $injector.get('$http');
	    
	    // Intercept failed requests
	    return promise.then(null, function(originalResponse) {
	      if(originalResponse.status === 401) {
	        // The request bounced because it was not authorized - add a new request to the retry queue
	        promise = queue.pushPromiseFn(function() { return $http(originalResponse.config); }, 'unauthorized-server');
	      }
	      if(originalResponse.status === 503) {
		        // The request could not be processed, server error
		        promise = queue.pushPromiseFn(function() { return $http(originalResponse.config); }, 'service-unavailable');
		  }	   
	      return promise;
	    });
	  };
}]);

/* We have to add the interceptor to the queue as a string because the interceptor depends upon service instances that are not available in the config block. */
angular.module('authentication.interceptor').config(['$httpProvider', function($httpProvider) {
  $httpProvider.responseInterceptors.push('AuthenticationInterceptor');
}]);
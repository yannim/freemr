/* Authentication Current User Service */
angular.module('authentication.currentUser', []).factory('currentUser', function() {
	var userInfo = null;
	var currentUser = {
		update : function(info) {
			userInfo = info;
		},
		clear : function() {
			userInfo = null;
		},
		info : function() {
			return userInfo;
		},
		isAuthenticated : function() {
			return !!userInfo;
		}
		/*,
		isAdmin : function() {
			return !!(userInfo && userInfo.admin);
		}*/
	};
	return currentUser;
});
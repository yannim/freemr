/* AuthenticationService */
angular.module('authentication', [
    'authentication.currentUser',
    'authentication.interceptor',
    'authentication.retryQueue'
]);

angular.module('authentication').factory('AuthenticationService',
    [ '$rootScope', '$http', '$location', '$q', 'currentUser', 'AuthenticationRetryQueue', function ($rootScope, $http, $location, $q, currentUser, queue) {

        // TODO: We need a way to refresh the page to clear any data that has been loaded when the user logs out
        // a simple way would be to redirect to the root of the application but this feels a bit inflexible.
        function redirect(url) {
            url = url || '/';
            $location.path(url);
        }

        function updateCurrentUser(user) {
            currentUser.update(user);
            if (!!user) {
                queue.retry();
            }
        }

        var service = {
            isLoginRequired:function () {
                return queue.hasMore();
            },

            getLoginReason:function () {
                return queue.getReason();
            },

            showLogin:function () {
                // Push a no-op onto the queue to create a manual login
                queue.push({ retry:function () {
                }, cancel:function () {
                }, reason:'user-request' });
            },
            login:function () {
                return $http.post('/lab4apo-api/auth/initiate').then(function () {
                    // retrieve current user after succesful authentication
                    return service.requestCurrentUser().then(function () {
                        return 'login-success';
                    });
                }, function (response) {
                    if (response.data.message) {
                        console.log(response.status + ':' + response.data.message);
                        return response.data.message;
                    }
                    console.log(response.status);
                    return 'no-client-token';
                });
            },
            cancelLogin:function (redirectTo) {
                queue.cancel();
                redirect(redirectUrl);
            },
            requestCurrentUser:function () {

                if (currentUser.isAuthenticated()) {
                    console.log('request');
                    return $q.when(currentUser);
                } else {
                    return $http.get('http://localhost:8082/resource-server/resource/readuser').then(function (response) {
                        console.log('response:' + response);
                        updateCurrentUser(response.data);
                        return currentUser;
                    });
                }
            },
            logout:function (redirectUrl) {
                return $http.post('/lab4apo-api/auth/logout').then(function () {
                    currentUser.clear();
                    redirect(redirectUrl);
                });
            }
        };
        return service;
    } ]);

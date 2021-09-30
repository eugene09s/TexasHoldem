app.controller('LobbyController', ['$scope', '$rootScope', '$http', function ($scope, $rootScope, $http) {
    $scope.lobbyTables = [];
    $scope.newScreenName = '';

    $http({
        url: '/poker?command=lobby-data',
        method: 'GET'
    }).success(function (data, status, headers, config) {
        console.log(data)
        for (tableId in data) {
            $scope.lobbyTables[tableId] = data[tableId];
        }
    });

    $scope.register = function () {
        // If there is some trimmed value for a new screen name
        socket.emit('register', $scope.newScreenName, function (response) {
            if (response.success) {
                $rootScope.username = response.username;
                $rootScope.totalChips = response.totalChips;
                $scope.registerError = '';
                $rootScope.$digest();
            } else if (response.message) {
                $scope.registerError = response.message;
            }
            $scope.$digest();
        });
    }
}]);
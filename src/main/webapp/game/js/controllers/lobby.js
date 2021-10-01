app.controller('LobbyController', ['$scope', '$rootScope', '$http', function ($scope, $rootScope, $http) {
    $scope.lobbyTables = [];

    $http({
        url: '/poker?command=lobby-data',
        method: 'GET'
    }).success(function (data, status, headers, config) {
        for (tableId in data) {
            $scope.lobbyTables[tableId] = data[tableId];
        }
    });
}]);

function register() {
    socket.emit('register', "", function (response) {
        if (response.success) {
            $rootScope.username = response.username;
            $rootScope.totalChips = response.totalChips;
            $rootScope.img = response.img;
            $scope.registerError = '';
            $rootScope.$digest();
        } else if (response.message) {
            $scope.registerError = response.message;
        }
        $scope.$digest();
    });
}
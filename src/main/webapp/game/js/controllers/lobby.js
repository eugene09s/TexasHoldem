app.controller('LobbyController', ['$scope', '$rootScope', '$http', function( $scope, $rootScope, $http ) {
	$scope.lobbyTables = [];
	$scope.username = '';

	$http({
		url: '/poker?command=lobby-data',
		method: 'GET'
	}).success(function ( data, status, headers, config ) {
		for( tableId in data ) {
			$scope.lobbyTables[tableId] = data[tableId];
		}
	});

	$scope.register = function() {
		if( $scope.username === '') {
			socket.emit( 'register', $scope.username, function( response ){
				if( response.success ){
					$rootScope.username = response.username;
					$rootScope.totalChips = response.totalChips;
					$scope.registerError = '';
					$rootScope.$digest();
				}
				else if( response.message ) {
					$scope.registerError = response.message;
				}
				$scope.$digest();
			});
		}
	}
}]);

/**
 * The table controller. It keeps track of the data on the interface,
 * depending on the replies from the server.
 */
app.controller( 'ChatController', ['$scope', function( $scope ) {
	/**
	 * Chat
	 */
	$scope.sendMessage = function() {
		if ( $scope.messageText.trim() ) {
			let message = $scope.messageText.trim();
			let messageBox = document.querySelector('#messages');
			socket.emit( 'sendMessage', message );

			let messageElement = angular.element( '<p class="message"><b>You</b>: ' + htmlEntities( message ) + '</p>' );
			angular.element( messageBox ).append( messageElement );
			messageBox.scrollTop = messageBox.scrollHeight;
			$scope.messageText = '';
		}
	}

	socket.on( 'receiveMessage', function( data ) {
		let messageBox = document.querySelector('#messages');
		let messageElement = angular.element( '<p class="message">' +
			'<img src="/images/photo/' + data.img + '" id="chatPhotoGambler" alt="photo">' +
			'<b>' + data.sender + '</b>: ' + data.message + '</p>' );
		angular.element( messageBox ).append( messageElement );
		messageBox.scrollTop = messageBox.scrollHeight;
	});

	function htmlEntities(str) {
	    return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
	}
}]);
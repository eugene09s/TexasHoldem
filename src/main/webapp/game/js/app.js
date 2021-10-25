class Socket {
	constructor() {
		this.connected = false;
	}

	emit(event, data, callback) {
		this.ws.send(JSON.stringify({
			event: event,
			data: data
		}));
		if (callback !== undefined) {
			this.on(event, callback);
		}
	}

	async onOpenSocket() {
		console.log("Websocket connected!");
		return new Promise((resolve) => resolve());
	}

	getStateSocket() {
		return this.ws.readyState;
	}

	onClose() {

	}

	onError(err) {
		console.log("!!!WebSocket error: " + err);
	}

	removeAllListeners() {
		this.events.length = 0;
	}

	on(event, callback) {
		this.events[event] = callback;
	}

	onMessage(msg) {
		this.events[msg.event](msg.data);
	}
	async connect() {
		return new Promise(resolve => {
			if (this.connected) return resolve();
			this.ws = new WebSocket(`ws://${location.host}/game-poker`);
			this.ws.onopen = () => {
				this.connected = true;
				resolve();
			}
			this.ws.onmessage = (e) => this.onMessage(JSON.parse(e.data));
			this.ws.onclose = this.onClose;
			this.ws.onerror = (err) => this.onError(err);
			this.events = {};
		});
	}
}

const socket = new Socket();

let app = angular.module( 'app', ['ngRoute'] ).config( function( $routeProvider, $locationProvider ) {
	$routeProvider.when('/table-10/:tableId', {
		templateUrl: '/game/partials/table-10-handed.html',
		controller: 'TableController',
	});

	$routeProvider.when('/table-6/:tableId', {
		templateUrl: '/game/partials/table-6-handed.html',
		controller: 'TableController',
	});

	$routeProvider.when('/table-2/:tableId', {
		templateUrl: '/game/partials/table-2-handed.html',
		controller: 'TableController',
	});

	$routeProvider.when('/', {
		templateUrl: '/game/partials/lobby.html',
		controller: 'LobbyController',
	});

	$routeProvider.otherwise({redirectTo: "/"});

	$locationProvider.html5Mode(true).hashPrefix('!');
});

app.run( function( $rootScope ) {
	$rootScope.username = '';
	$rootScope.totalChips = 0;
	$rootScope.sittingOnTable = '';
});

(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('JugadorDetailController', JugadorDetailController);

    JugadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Jugador', 'Posicion', 'Categoria'];

    function JugadorDetailController($scope, $rootScope, $stateParams, previousState, entity, Jugador, Posicion, Categoria) {
        var vm = this;

        vm.jugador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaApp:jugadorUpdate', function(event, result) {
            vm.jugador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

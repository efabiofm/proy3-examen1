(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('JugadorDetailController', JugadorDetailController);

    JugadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Jugador', 'Categoria', 'Posicion'];

    function JugadorDetailController($scope, $rootScope, $stateParams, previousState, entity, Jugador, Categoria, Posicion) {
        var vm = this;

        vm.jugador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaApp:jugadorUpdate', function(event, result) {
            vm.jugador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

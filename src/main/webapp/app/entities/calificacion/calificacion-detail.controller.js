(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('CalificacionDetailController', CalificacionDetailController);

    CalificacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Calificacion', 'Jugador', 'Entrenamiento'];

    function CalificacionDetailController($scope, $rootScope, $stateParams, previousState, entity, Calificacion, Jugador, Entrenamiento) {
        var vm = this;

        vm.calificacion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaApp:calificacionUpdate', function(event, result) {
            vm.calificacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

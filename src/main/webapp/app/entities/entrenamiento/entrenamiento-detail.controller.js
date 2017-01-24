(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('EntrenamientoDetailController', EntrenamientoDetailController);

    EntrenamientoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entrenamiento', 'Horario', 'Entrenador'];

    function EntrenamientoDetailController($scope, $rootScope, $stateParams, previousState, entity, Entrenamiento, Horario, Entrenador) {
        var vm = this;

        vm.entrenamiento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaApp:entrenamientoUpdate', function(event, result) {
            vm.entrenamiento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('HorarioDetailController', HorarioDetailController);

    HorarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Horario', 'Categoria'];

    function HorarioDetailController($scope, $rootScope, $stateParams, previousState, entity, Horario, Categoria) {
        var vm = this;

        vm.horario = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaApp:horarioUpdate', function(event, result) {
            vm.horario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

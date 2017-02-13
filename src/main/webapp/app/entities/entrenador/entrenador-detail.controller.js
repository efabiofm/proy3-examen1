(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('EntrenadorDetailController', EntrenadorDetailController);

    EntrenadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entrenador'];

    function EntrenadorDetailController($scope, $rootScope, $stateParams, previousState, entity, Entrenador) {
        var vm = this;

        vm.entrenador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaApp:entrenadorUpdate', function(event, result) {
            vm.entrenador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

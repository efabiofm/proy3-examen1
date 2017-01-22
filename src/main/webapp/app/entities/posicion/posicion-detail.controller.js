(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('PosicionDetailController', PosicionDetailController);

    PosicionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Posicion'];

    function PosicionDetailController($scope, $rootScope, $stateParams, previousState, entity, Posicion) {
        var vm = this;

        vm.posicion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaApp:posicionUpdate', function(event, result) {
            vm.posicion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

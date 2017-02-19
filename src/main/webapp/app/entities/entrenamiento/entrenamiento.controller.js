(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('EntrenamientoController', EntrenamientoController);

    EntrenamientoController.$inject = ['$scope', '$state', 'entrenamientos'];

    function EntrenamientoController ($scope, $state, entrenamientos) {
        var vm = this;

        vm.entrenamientos = entrenamientos;
        //obtenerEntrenamientoMasCercano();
    }
})();

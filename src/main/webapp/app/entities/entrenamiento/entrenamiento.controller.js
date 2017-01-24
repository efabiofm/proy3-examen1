(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('EntrenamientoController', EntrenamientoController);

    EntrenamientoController.$inject = ['$scope', '$state', 'Entrenamiento'];

    function EntrenamientoController ($scope, $state, Entrenamiento) {
        var vm = this;

        vm.entrenamientos = [];

        loadAll();

        function loadAll() {
            Entrenamiento.query(function(result) {
                vm.entrenamientos = result;
                vm.searchQuery = null;
            });
        }
    }
})();

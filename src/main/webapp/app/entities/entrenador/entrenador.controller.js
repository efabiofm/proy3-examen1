(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('EntrenadorController', EntrenadorController);

    EntrenadorController.$inject = ['$scope', '$state', 'Entrenador'];

    function EntrenadorController ($scope, $state, Entrenador) {
        var vm = this;

        vm.entrenadors = [];

        loadAll();

        function loadAll() {
            Entrenador.query(function(result) {
                vm.entrenadors = result;
                vm.searchQuery = null;
            });
        }
    }
})();

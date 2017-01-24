(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('PosicionController', PosicionController);

    PosicionController.$inject = ['$scope', '$state', 'Posicion'];

    function PosicionController ($scope, $state, Posicion) {
        var vm = this;

        vm.posicions = [];

        loadAll();

        function loadAll() {
            Posicion.query(function(result) {
                vm.posicions = result;
                vm.searchQuery = null;
            });
        }
    }
})();

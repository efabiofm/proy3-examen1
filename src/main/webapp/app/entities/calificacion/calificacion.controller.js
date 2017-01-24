(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('CalificacionController', CalificacionController);

    CalificacionController.$inject = ['$scope', '$state', 'Calificacion'];

    function CalificacionController ($scope, $state, Calificacion) {
        var vm = this;

        vm.calificacions = [];

        loadAll();

        function loadAll() {
            Calificacion.query(function(result) {
                vm.calificacions = result;
                vm.searchQuery = null;
            });
        }
    }
})();

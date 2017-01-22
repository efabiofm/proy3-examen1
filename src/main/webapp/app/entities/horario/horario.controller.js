(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('HorarioController', HorarioController);

    HorarioController.$inject = ['$scope', '$state', 'Horario'];

    function HorarioController ($scope, $state, Horario) {
        var vm = this;

        vm.horarios = [];

        loadAll();

        function loadAll() {
            Horario.query(function(result) {
                vm.horarios = result;
                vm.searchQuery = null;
            });
        }
    }
})();

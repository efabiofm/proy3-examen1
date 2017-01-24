(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('EntrenadorDeleteController',EntrenadorDeleteController);

    EntrenadorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Entrenador'];

    function EntrenadorDeleteController($uibModalInstance, entity, Entrenador) {
        var vm = this;

        vm.entrenador = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Entrenador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

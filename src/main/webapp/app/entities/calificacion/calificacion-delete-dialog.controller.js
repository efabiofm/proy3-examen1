(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('CalificacionDeleteController',CalificacionDeleteController);

    CalificacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Calificacion'];

    function CalificacionDeleteController($uibModalInstance, entity, Calificacion) {
        var vm = this;

        vm.calificacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Calificacion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

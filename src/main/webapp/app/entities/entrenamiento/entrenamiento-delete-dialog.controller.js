(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('EntrenamientoDeleteController',EntrenamientoDeleteController);

    EntrenamientoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Entrenamiento'];

    function EntrenamientoDeleteController($uibModalInstance, entity, Entrenamiento) {
        var vm = this;

        vm.entrenamiento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Entrenamiento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

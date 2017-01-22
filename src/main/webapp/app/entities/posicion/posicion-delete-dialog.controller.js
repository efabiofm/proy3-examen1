(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('PosicionDeleteController',PosicionDeleteController);

    PosicionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Posicion'];

    function PosicionDeleteController($uibModalInstance, entity, Posicion) {
        var vm = this;

        vm.posicion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Posicion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

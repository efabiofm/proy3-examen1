(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('HorarioDeleteController',HorarioDeleteController);

    HorarioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Horario'];

    function HorarioDeleteController($uibModalInstance, entity, Horario) {
        var vm = this;

        vm.horario = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Horario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('HorarioDialogController', HorarioDialogController);

    HorarioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Horario', 'Categoria'];

    function HorarioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Horario, Categoria) {
        var vm = this;

        vm.horario = entity;
        vm.clear = clear;
        vm.save = save;
        vm.categorias = Categoria.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.horario.id !== null) {
                Horario.update(vm.horario, onSaveSuccess, onSaveError);
            } else {
                Horario.save(vm.horario, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escuelitaApp:horarioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

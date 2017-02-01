(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('CalificacionDialogController', CalificacionDialogController);

    CalificacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Calificacion', 'jugadors'];

    function CalificacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Calificacion, jugadors) {
        var vm = this;

        vm.calificacion = entity;
        vm.calificacion.entrenamientoId = $stateParams.entrenamientoId;
        vm.jugadors = jugadors;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.calificacion.id !== null) {
                Calificacion.update(vm.calificacion, onSaveSuccess, onSaveError);
            } else {
                Calificacion.save(vm.calificacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escuelitaApp:calificacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

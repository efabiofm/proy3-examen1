(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('EntrenadorDialogController', EntrenadorDialogController);

    EntrenadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entrenador'];

    function EntrenadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Entrenador) {
        var vm = this;

        vm.entrenador = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.entrenador.id !== null) {
                Entrenador.update(vm.entrenador, onSaveSuccess, onSaveError);
            } else {
                Entrenador.save(vm.entrenador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escuelitaApp:entrenadorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaNacimiento = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

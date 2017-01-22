(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('PosicionDialogController', PosicionDialogController);

    PosicionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Posicion'];

    function PosicionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Posicion) {
        var vm = this;

        vm.posicion = entity;
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
            if (vm.posicion.id !== null) {
                Posicion.update(vm.posicion, onSaveSuccess, onSaveError);
            } else {
                Posicion.save(vm.posicion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escuelitaApp:posicionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

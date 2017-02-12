(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('JugadorDialogController', JugadorDialogController);

    JugadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Jugador', 'Categoria', 'Posicion'];

    function JugadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Jugador, Categoria, Posicion) {
        var vm = this;

        vm.jugador = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.categorias = Categoria.query();
        vm.posicions = Posicion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.jugador.id !== null) {
                Jugador.update(vm.jugador, onSaveSuccess, onSaveError);
            } else {
                Jugador.save(vm.jugador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escuelitaApp:jugadorUpdate', result);
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

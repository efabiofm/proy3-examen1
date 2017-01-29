(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('CalificacionDialogController', CalificacionDialogController);

    CalificacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Calificacion', 'Jugador'];

    function CalificacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Calificacion, Jugador) {
        var vm = this;

        vm.calificacion = entity;
        vm.calificacion.entrenamientoId = parseInt($stateParams.id);
        vm.clear = clear;
        vm.save = save;
        vm.jugadors = Jugador.query({filter: 'calificacion-is-null'});
        $q.all([vm.calificacion.$promise, vm.jugadors.$promise]).then(function() {
            if (!vm.calificacion.jugadorId) {
                return $q.reject();
            }
            return Jugador.get({id : vm.calificacion.jugadorId}).$promise;
        }).then(function(jugador) {
            vm.jugadors.push(jugador);
        });

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

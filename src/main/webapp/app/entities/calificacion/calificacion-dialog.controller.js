(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('CalificacionDialogController', CalificacionDialogController);

    CalificacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Calificacion', 'Jugador', 'Entrenamiento'];

    function CalificacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Calificacion, Jugador, Entrenamiento) {
        var vm = this;

        vm.calificacion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.jugadors = Jugador.query({filter: 'calificacion-is-null'});
        $q.all([vm.calificacion.$promise, vm.jugadors.$promise]).then(function() {
            if (!vm.calificacion.jugador || !vm.calificacion.jugador.id) {
                return $q.reject();
            }
            return Jugador.get({id : vm.calificacion.jugador.id}).$promise;
        }).then(function(jugador) {
            vm.jugadors.push(jugador);
        });
        vm.entrenamientos = Entrenamiento.query({filter: 'calificacion-is-null'});
        $q.all([vm.calificacion.$promise, vm.entrenamientos.$promise]).then(function() {
            if (!vm.calificacion.entrenamiento || !vm.calificacion.entrenamiento.id) {
                return $q.reject();
            }
            return Entrenamiento.get({id : vm.calificacion.entrenamiento.id}).$promise;
        }).then(function(entrenamiento) {
            vm.entrenamientos.push(entrenamiento);
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

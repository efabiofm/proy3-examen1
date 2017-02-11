(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('EntrenamientoDialogController', EntrenamientoDialogController);

    EntrenamientoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Entrenamiento', 'Horario', 'Entrenador'];

    function EntrenamientoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Entrenamiento, Horario, Entrenador) {
        var vm = this;

        vm.entrenamiento = entity;
        vm.clear = clear;
        vm.save = save;
        vm.horarios = Horario.query({filter: 'entrenamiento-is-null'});
        $q.all([vm.entrenamiento.$promise, vm.horarios.$promise]).then(function() {
            if (!vm.entrenamiento.horarioId) {
                return $q.reject();
            }
            return Horario.get({id : vm.entrenamiento.horarioId}).$promise;
        }).then(function(horario) {
            vm.horarios.push(horario);
        });
        vm.entrenadors = Entrenador.query();
        vm.setNombreEntrenamiento = setNombreEntrenamiento;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.entrenamiento.id !== null) {
                Entrenamiento.update(vm.entrenamiento, onSaveSuccess, onSaveError);
            } else {
                Entrenamiento.save(vm.entrenamiento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escuelitaApp:entrenamientoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function setNombreEntrenamiento () {
            var horario = vm.horarios.find(function(horario){
               if (horario.id === vm.entrenamiento.horarioId){
                   return horario;
               }
            });
            var entrenador = vm.entrenadors.find(function(entrenador){
               if (entrenador.id === vm.entrenamiento.entrenadorId){
                   return entrenador;
               }
            });
            vm.entrenamiento.nombre = (entrenador ? entrenador.nombre + "-" : "") + (vm.entrenamiento.descripcion ? vm.entrenamiento.descripcion + "-" : "") + (horario ? horario.dia + "-" + horario.horaInicio : "")
        }
    }
})();

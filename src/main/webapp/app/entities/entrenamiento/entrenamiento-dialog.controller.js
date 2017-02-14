(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('EntrenamientoDialogController', EntrenamientoDialogController);

    EntrenamientoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Entrenamiento', 'Horario', 'Entrenador', 'user'];

    function EntrenamientoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Entrenamiento, Horario, Entrenador, user) {
        var vm = this;

        vm.entrenamiento = entity;
        vm.entrenamiento.entrenadorId = user.id;
        vm.entrenamiento.entrenadorName = user.firstName + " " + user.lastName;
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

        setNombreEntrenamiento();
        function setNombreEntrenamiento () {
            var horario = vm.horarios.find(function(horario){
               if (horario.id === vm.entrenamiento.horarioId){
                   return horario;
               }
            });
            vm.entrenamiento.nombre = user.firstName + "-" + (vm.entrenamiento.descripcion ? vm.entrenamiento.descripcion + "-" : "") + (horario ? horario.dia + "-" + horario.horaInicio : "")
        }
    }
})();

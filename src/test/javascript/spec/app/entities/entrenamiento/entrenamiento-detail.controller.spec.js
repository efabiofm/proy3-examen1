'use strict';

describe('Controller Tests', function() {

    describe('Entrenamiento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEntrenamiento, MockHorario, MockEntrenador;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEntrenamiento = jasmine.createSpy('MockEntrenamiento');
            MockHorario = jasmine.createSpy('MockHorario');
            MockEntrenador = jasmine.createSpy('MockEntrenador');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Entrenamiento': MockEntrenamiento,
                'Horario': MockHorario,
                'Entrenador': MockEntrenador
            };
            createController = function() {
                $injector.get('$controller')("EntrenamientoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'escuelitaApp:entrenamientoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

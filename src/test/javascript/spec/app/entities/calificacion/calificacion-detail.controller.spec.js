'use strict';

describe('Controller Tests', function() {

    describe('Calificacion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCalificacion, MockJugador, MockEntrenamiento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCalificacion = jasmine.createSpy('MockCalificacion');
            MockJugador = jasmine.createSpy('MockJugador');
            MockEntrenamiento = jasmine.createSpy('MockEntrenamiento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Calificacion': MockCalificacion,
                'Jugador': MockJugador,
                'Entrenamiento': MockEntrenamiento
            };
            createController = function() {
                $injector.get('$controller')("CalificacionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'escuelitaApp:calificacionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

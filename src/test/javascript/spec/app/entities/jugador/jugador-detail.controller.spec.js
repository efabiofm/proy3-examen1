'use strict';

describe('Controller Tests', function() {

    describe('Jugador Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockJugador, MockCategoria, MockPosicion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockJugador = jasmine.createSpy('MockJugador');
            MockCategoria = jasmine.createSpy('MockCategoria');
            MockPosicion = jasmine.createSpy('MockPosicion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Jugador': MockJugador,
                'Categoria': MockCategoria,
                'Posicion': MockPosicion
            };
            createController = function() {
                $injector.get('$controller')("JugadorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'escuelitaApp:jugadorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

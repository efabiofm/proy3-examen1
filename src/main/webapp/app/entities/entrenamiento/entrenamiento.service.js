(function() {
    'use strict';
    angular
        .module('escuelitaApp')
        .factory('Entrenamiento', Entrenamiento);

    Entrenamiento.$inject = ['$resource'];

    function Entrenamiento ($resource) {
        var resourceUrl =  'api/entrenamientos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'queryByEntrenador': { method: 'GET', isArray: true, url:'api/entrenamientos/entrenador/:id'}
        });
    }
})();

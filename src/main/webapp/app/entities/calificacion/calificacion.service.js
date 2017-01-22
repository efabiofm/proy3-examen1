(function() {
    'use strict';
    angular
        .module('escuelitaApp')
        .factory('Calificacion', Calificacion);

    Calificacion.$inject = ['$resource'];

    function Calificacion ($resource) {
        var resourceUrl =  'api/calificacions/:id';

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
            'update': { method:'PUT' }
        });
    }
})();

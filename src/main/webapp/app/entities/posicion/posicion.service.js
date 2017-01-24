(function() {
    'use strict';
    angular
        .module('escuelitaApp')
        .factory('Posicion', Posicion);

    Posicion.$inject = ['$resource'];

    function Posicion ($resource) {
        var resourceUrl =  'api/posicions/:id';

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

(function() {
    'use strict';
    angular
        .module('escuelitaApp')
        .factory('Entrenador', Entrenador);

    Entrenador.$inject = ['$resource', 'DateUtils'];

    function Entrenador ($resource, DateUtils) {
        var resourceUrl =  'api/entrenadors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaNacimiento = DateUtils.convertLocalDateFromServer(data.fechaNacimiento);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaNacimiento = DateUtils.convertLocalDateToServer(copy.fechaNacimiento);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaNacimiento = DateUtils.convertLocalDateToServer(copy.fechaNacimiento);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();

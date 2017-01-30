(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    var jugadors = ['$stateParams', 'Horario', 'Jugador', function ($stateParams, Horario, Jugador) {
        return  Horario.get({id:$stateParams.horarioId}).$promise.then(function (horario) {
            return Jugador.getByCategoria({id:horario.categoriaId});
        });
    }];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entrenamiento-detail.calificacion-detail', {
            parent: 'entrenamiento-detail',
            url: '/calificacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaApp.calificacion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/calificacion/calificacion-detail.html',
                    controller: 'CalificacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('calificacion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Calificacion', function($stateParams, Calificacion) {
                    return Calificacion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'calificacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('entrenamiento-detail.calificacion-detail.edit', {
            parent: 'entrenamiento-detail.calificacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calificacion/calificacion-dialog.html',
                    controller: 'CalificacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Calificacion', function(Calificacion) {
                            return Calificacion.get({id : $stateParams.id}).$promise;
                        }],
                        jugadors: jugadors
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entrenamiento-detail.calificacion-new', {
            parent: 'entrenamiento-detail',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calificacion/calificacion-dialog.html',
                    controller: 'CalificacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descripcion: null,
                                nota: null,
                                id: null
                            };
                        },
                        jugadors: jugadors
                    }
                }).result.then(function() {
                    $state.go('entrenamiento-detail', null, { reload: 'entrenamiento-detail' });
                }, function() {
                    $state.go('entrenamiento-detail');
                });
            }],
        })
        .state('entrenamiento-detail.calificacion-edit', {
            parent: 'entrenamiento-detail',
            url: '/calificacion/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calificacion/calificacion-dialog.html',
                    controller: 'CalificacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Calificacion', function(Calificacion) {
                            return Calificacion.get({id : $stateParams.id}).$promise;
                        }],
                        jugadors: jugadors
                    }
                }).result.then(function() {
                    $state.go('entrenamiento-detail', null, { reload: 'entrenamiento-detail' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entrenamiento-detail.calificacion-delete', {
            parent: 'entrenamiento-detail',
            url: '/calificacion/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calificacion/calificacion-delete-dialog.html',
                    controller: 'CalificacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Calificacion', function(Calificacion) {
                            return Calificacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entrenamiento-detail', null, { reload: 'entrenamiento-detail' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

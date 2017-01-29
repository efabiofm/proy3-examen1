(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entrenamiento', {
            parent: 'entity',
            url: '/entrenamiento',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaApp.entrenamiento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entrenamiento/entrenamientos.html',
                    controller: 'EntrenamientoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entrenamiento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('entrenamiento-detail', {
            parent: 'entity',
            url: '/entrenamiento/{id}/{horarioId}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaApp.entrenamiento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entrenamiento/entrenamiento-detail.html',
                    controller: 'EntrenamientoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entrenamiento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Entrenamiento', function($stateParams, Entrenamiento) {
                    return Entrenamiento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'entrenamiento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }],
                calificaciones: ["$stateParams", "Calificacion", function($stateParams, Calificacion) {
                    return Calificacion.getByEntrenamiento({id: $stateParams.id}).$promise;
                }]
            }
        })
        .state('entrenamiento-detail.edit', {
            parent: 'entrenamiento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrenamiento/entrenamiento-dialog.html',
                    controller: 'EntrenamientoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entrenamiento', function(Entrenamiento) {
                            return Entrenamiento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entrenamiento.new', {
            parent: 'entrenamiento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrenamiento/entrenamiento-dialog.html',
                    controller: 'EntrenamientoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                descripcion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('entrenamiento', null, { reload: 'entrenamiento' });
                }, function() {
                    $state.go('entrenamiento');
                });
            }]
        })
        .state('entrenamiento.edit', {
            parent: 'entrenamiento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrenamiento/entrenamiento-dialog.html',
                    controller: 'EntrenamientoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entrenamiento', function(Entrenamiento) {
                            return Entrenamiento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entrenamiento', null, { reload: 'entrenamiento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entrenamiento.delete', {
            parent: 'entrenamiento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrenamiento/entrenamiento-delete-dialog.html',
                    controller: 'EntrenamientoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Entrenamiento', function(Entrenamiento) {
                            return Entrenamiento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entrenamiento', null, { reload: 'entrenamiento' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

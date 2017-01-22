(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entrenador', {
            parent: 'entity',
            url: '/entrenador',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaApp.entrenador.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entrenador/entrenadors.html',
                    controller: 'EntrenadorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entrenador');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('entrenador-detail', {
            parent: 'entity',
            url: '/entrenador/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaApp.entrenador.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entrenador/entrenador-detail.html',
                    controller: 'EntrenadorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entrenador');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Entrenador', function($stateParams, Entrenador) {
                    return Entrenador.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'entrenador',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('entrenador-detail.edit', {
            parent: 'entrenador-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrenador/entrenador-dialog.html',
                    controller: 'EntrenadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entrenador', function(Entrenador) {
                            return Entrenador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entrenador.new', {
            parent: 'entrenador',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrenador/entrenador-dialog.html',
                    controller: 'EntrenadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                apellido: null,
                                fechaNacimiento: null,
                                telefono: null,
                                correo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('entrenador', null, { reload: 'entrenador' });
                }, function() {
                    $state.go('entrenador');
                });
            }]
        })
        .state('entrenador.edit', {
            parent: 'entrenador',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrenador/entrenador-dialog.html',
                    controller: 'EntrenadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entrenador', function(Entrenador) {
                            return Entrenador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entrenador', null, { reload: 'entrenador' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entrenador.delete', {
            parent: 'entrenador',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrenador/entrenador-delete-dialog.html',
                    controller: 'EntrenadorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Entrenador', function(Entrenador) {
                            return Entrenador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entrenador', null, { reload: 'entrenador' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

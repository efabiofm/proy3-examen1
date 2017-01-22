(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('calificacion', {
            parent: 'entity',
            url: '/calificacion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaApp.calificacion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/calificacion/calificacions.html',
                    controller: 'CalificacionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('calificacion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('calificacion-detail', {
            parent: 'entity',
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
        .state('calificacion-detail.edit', {
            parent: 'calificacion-detail',
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
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('calificacion.new', {
            parent: 'calificacion',
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
                        }
                    }
                }).result.then(function() {
                    $state.go('calificacion', null, { reload: 'calificacion' });
                }, function() {
                    $state.go('calificacion');
                });
            }]
        })
        .state('calificacion.edit', {
            parent: 'calificacion',
            url: '/{id}/edit',
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
                        }]
                    }
                }).result.then(function() {
                    $state.go('calificacion', null, { reload: 'calificacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('calificacion.delete', {
            parent: 'calificacion',
            url: '/{id}/delete',
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
                    $state.go('calificacion', null, { reload: 'calificacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

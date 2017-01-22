(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('posicion', {
            parent: 'entity',
            url: '/posicion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaApp.posicion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/posicion/posicions.html',
                    controller: 'PosicionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('posicion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('posicion-detail', {
            parent: 'entity',
            url: '/posicion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaApp.posicion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/posicion/posicion-detail.html',
                    controller: 'PosicionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('posicion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Posicion', function($stateParams, Posicion) {
                    return Posicion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'posicion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('posicion-detail.edit', {
            parent: 'posicion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/posicion/posicion-dialog.html',
                    controller: 'PosicionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Posicion', function(Posicion) {
                            return Posicion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('posicion.new', {
            parent: 'posicion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/posicion/posicion-dialog.html',
                    controller: 'PosicionDialogController',
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
                    $state.go('posicion', null, { reload: 'posicion' });
                }, function() {
                    $state.go('posicion');
                });
            }]
        })
        .state('posicion.edit', {
            parent: 'posicion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/posicion/posicion-dialog.html',
                    controller: 'PosicionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Posicion', function(Posicion) {
                            return Posicion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('posicion', null, { reload: 'posicion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('posicion.delete', {
            parent: 'posicion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/posicion/posicion-delete-dialog.html',
                    controller: 'PosicionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Posicion', function(Posicion) {
                            return Posicion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('posicion', null, { reload: 'posicion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

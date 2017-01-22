(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('horario', {
            parent: 'entity',
            url: '/horario',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaApp.horario.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/horario/horarios.html',
                    controller: 'HorarioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('horario');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('horario-detail', {
            parent: 'entity',
            url: '/horario/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaApp.horario.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/horario/horario-detail.html',
                    controller: 'HorarioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('horario');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Horario', function($stateParams, Horario) {
                    return Horario.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'horario',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('horario-detail.edit', {
            parent: 'horario-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/horario/horario-dialog.html',
                    controller: 'HorarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Horario', function(Horario) {
                            return Horario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('horario.new', {
            parent: 'horario',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/horario/horario-dialog.html',
                    controller: 'HorarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                horaInicio: null,
                                horaFin: null,
                                dia: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('horario', null, { reload: 'horario' });
                }, function() {
                    $state.go('horario');
                });
            }]
        })
        .state('horario.edit', {
            parent: 'horario',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/horario/horario-dialog.html',
                    controller: 'HorarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Horario', function(Horario) {
                            return Horario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('horario', null, { reload: 'horario' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('horario.delete', {
            parent: 'horario',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/horario/horario-delete-dialog.html',
                    controller: 'HorarioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Horario', function(Horario) {
                            return Horario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('horario', null, { reload: 'horario' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

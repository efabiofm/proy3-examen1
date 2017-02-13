(function() {
    'use strict';

    angular
        .module('escuelitaApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Entrenamiento', 'User'];

    function HomeController ($scope, Principal, LoginService, $state, Entrenamiento, User) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {

            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                User.get({login: account.login}).$promise.then(function (user) {
                    Entrenamiento.getByEntrenador({id: user.id }).$promise.then(function (result) {
                        vm.algo = result.resultado;
                    });

                });

            });
        }
        function register () {
            $state.go('register');
        }



    }
})();

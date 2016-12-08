var application = angular.module('home', [ 'ui.router', 'ui.bootstrap' ]);
var domain = 'http://localhost:8080/hrms';
application.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider.state('resignation', {
		url : '/resignation',
		templateUrl : 'resources/js/resignation.html',
		controller : 'homeController'
	}).state('approval', {
		url : '/approval',
		templateUrl : 'resources/js/approval.html',
		controller : 'approvalController'
	}).state('approvalAction', {
		url : '/approvalAction',
		templateUrl : 'resources/js/approvalActionModal.html',
		controller : 'approvalController'
			//approvalController
	}).state('exit', {
		url : '/exit',
		templateUrl : 'resources/js/exit.html',
		controller : 'homeController'
	}).state('exitemp', {
		url : '/exitemp',
		templateUrl : 'resources/js/exitemp.html',
		controller : 'homeController'
	}).state('exitemprate', {
		url : '/exitemprate',
		templateUrl : 'resources/js/exitemprate.html',
		controller : 'homeController'
	}).state('exitemphr', {
		url : '/exitemphr',
		templateUrl : 'resources/js/exitemphr.html',
		controller : 'homeController'
	}).state('hr_approval',{
		url:'/hrapproval',
		templateUrl:'resources/js/hr_approval.html',
		controller:'hrApprovalController'
	}).state('hr_ApprovalModal',{
		url:'/hrApprovalModal',
		templateUrl:'resources/js/hrApprovalModal.html',
		controller:'hrApprovalController'
	})
.state('noduesit',{
	url:'/IT_NODUES',
	templateUrl:'resources/js/noduesit/noduesit.html',
	controller:'homeController'
})
.state('noduesitmodal',{
	url:'/IT_NODUES_MODAL',
	templateUrl:'resources/js/noduesit/noduesitmodal.html',
	controller:'homeController'
})
.state('noduesinfra',{
	url:'/INFRA_NODUES',
	templateUrl:'resources/js/noduesinfra.html',
	controller:'homeController'
})
.state('noduesaccounts',{
	url:'/ACCOUNTS_NODUES',
	templateUrl:'resources/js/noduesaccounts.html',
	controller:'homeController'
})
.state('noduesrm',{
	url:'/RM_NODUES',
	templateUrl:'resources/js/noduesrm.html',
	controller:'homeController'
})
.state('nodueshr',{
	url:'/HR_NODUES',
	templateUrl:'resources/js/nodueshr.html',
	controller:'homeController'
	});
});
application.controller('homeController', function($scope, $http) {
	$scope.getLinks = function() {
		$http.get(domain + '/getPages').success(
				function(data, status, headers, config) {
					/*alert(JSON.stringify({
						data : data
					}));*/
					$scope.pageList = data.pages;
				}).error(function(data, status, headers, config) {
					alert('Error');
		})
	}
});
var application = angular.module('home', [ 'ui.router', 'ui.bootstrap' ]);
//var domain = 'http://localhost:8080/hrms';
var domain = '/hrms';
application.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider.state('resignation', {
		url : '/resignation',
		templateUrl : 'resources/js/resignation/resignation.html',
		controller : 'homeController'
	}).state('approval', {
		url : '/approval',
		templateUrl : 'resources/js/rmapproval/approval.html',
		controller : 'approvalController'
	}).state('approvalAction', {
		url : '/approvalAction',
		templateUrl : 'resources/js/rmapproval/approvalActionModal.html',
		controller : 'approvalController'
			//approvalController
/*	}).state('exit', {
		url : '/exit',
		templateUrl : 'resources/js/exit.html',
		controller : 'homeController'*/
	}).state('exitemp', {
		url : '/exitemp',
		templateUrl : 'resources/js/exitemp.html',
		controller : 'homeController'
	}).state('exitemprate', {
		url : '/exitemprate',
		templateUrl : 'resources/js/empfeddback/exitemprate.html',
		controller : 'homeController'
	}).state('exitemphr', {
		url : '/exitemphr',
		templateUrl : 'resources/js/hrfeedback/exitemphr.html',
		controller : 'homeController'
	}).state('hr_approval',{
		url:'/hrapproval',
		templateUrl:'resources/js/hrapproval/hr_approval.html',
		controller:'hrApprovalController'
	}).state('hr_ApprovalModal',{
		url:'/hrApprovalModal',
		templateUrl:'resources/js/hrapproval/hrApprovalModal.html',
		controller:'hrApprovalController'
	}).state('noduesit',{
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
		templateUrl:'resources/js/noduesinfra/noduesinfra.html',
		controller:'homeController'
	})
	.state('noduesaccounts',{
		url:'/ACCOUNTS_NODUES',
		templateUrl:'resources/js/noduesaccounts/noduesaccounts.html',
		controller:'homeController'
			})
	.state('noduesaccountsmodal',{
		url:'/IT_ACCOUNTS_MODAL',
		templateUrl:'resources/js/noduesaccounts/noduesaccountsmodal.html',
		controller:'homeController'
	}).state('noduesrm',{
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
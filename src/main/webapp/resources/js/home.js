var application = angular.module('home', [ 'ui.router', 'ui.bootstrap','angular-loading-bar','cfp.loadingBar',
                                           'ngMaterial','angular-spinkit']);
//var domain = 'http://localhost:8080/hrms';
var domain = '/hrms';
application.config(function($stateProvider, $urlRouterProvider, cfpLoadingBarProvider) {
	cfpLoadingBarProvider.includeSpinner = false;
	$stateProvider.state('Resignation', {
		url : '/Resignation',
		templateUrl : 'resources/js/resignation/resignation.html',
		controller : 'homeController'
	}).state('ManagerApproval', {
		url : '/ManagerApproval',
		templateUrl : 'resources/js/rmapproval/approval.html',
		controller : 'approvalController'
	}).state('approvalAction', {
		url : '/approvalAction',
		templateUrl : 'resources/js/rmapproval/approvalActionModal.html',
		controller : 'approvalController'
	
	}).state('EmployeeFeedback', {
		url : '/EmployeeFeedback',
		templateUrl : 'resources/js/empfeedback/exitemp.html',
		controller : 'exitempcontroller'
	}).state('EmployeeRating', {
		url : '/EmployeeRating',
		templateUrl : 'resources/js/empfeedback/exitemprate.html',
		controller : 'homeController'
	}).state('HRFeedback', {
		url : '/HRFeedback',
		templateUrl : 'resources/js/hrfeedback/exitemphr.html',
		controller : 'homeController'
	}).state('exithrmodal', {
		url : '/exithrmodal',
		templateUrl : 'resources/js/hrfeedback/exithrmodal.html',
		controller : 'homeController'
	}).state('HRApproval',{
		url:'/HRApproval',
		templateUrl:'resources/js/hrapproval/hr_approval.html',
		controller:'hrApprovalController'
	}).state('hr_ApprovalModal',{
		url:'/hrApprovalModal',
		templateUrl:'resources/js/hrapproval/hrApprovalModal.html',
		controller:'hrApprovalController'
	}).state('ITNodues',{
		url:'/IT_NODUES',
		templateUrl:'resources/js/noduesit/noduesit.html',
		controller:'homeController'
	})
	.state('noduesitmodal',{
		url:'/IT_NODUES_MODAL',
		templateUrl:'resources/js/noduesit/noduesitmodal.html',
		controller:'noduesitmodaljscontroller'
	})
	.state('InfraNodues',{
		url:'/INFRA_NODUES',
		templateUrl:'resources/js/noduesinfra/noduesinfra.html',
		controller:'noduesinfracontroller'
	})
	.state('noduesinframodal',{
		url:'/INFRA_NODUES_MODAL',
		templateUrl:'resources/js/noduesinfra/noduesinframodal.html',
		controller:'noduesinframodalcontroller'					
	})
	.state('AccountNodues',{
		url:'/ACCOUNTS_NODUES',
		templateUrl:'resources/js/noduesaccounts/noduesaccounts.html',
		controller:'noduesaccountscontroller'
			})
	.state('noduesaccountsmodal',{
		url:'/IT_ACCOUNTS_MODAL',
		templateUrl:'resources/js/noduesaccounts/noduesaccountsmodal.html',
		controller:'nodueshrmodalcontroller'
	}).state('DocumentUpload',{
		url:'/DocumentUpload',
		templateUrl:'resources/js/document/userDetailUpload.html',
		controller:'documentManagement'
	}).state('DocumentDownload',{
		url:'/DocumentDownload',
		templateUrl:'resources/js/dowmloadDocument/download.html',
		controller:'documentDownload'
	}).state('MangerNodues',{
		url:'/RM_NODUES',
		templateUrl:'resources/js/noduesrm/noduesrm.html',
		controller:'noduesrmcontroller'
			
	}).state('noduesrmmodal',{
		url:'/RM_NODUES_MODAL',
		templateUrl:'resources/js/noduesrm/noduesrmmodal.html',
		controller:'noduesrmmodalcontroller'
	})
	.state('HRNodues',{
		url:'/HR_NODUES',
		templateUrl:'resources/js/nodueshr/nodueshr.html',
		controller:'nodueshrcontroller'
	}).state('nodueshrmodal',{
		url:'/HR_NODUES_modal',
		templateUrl:'resources/js/nodueshr/nodueshrmodal.html',
		controller:'nodueshrmodalcontroller'
			
	}).state('Query',{
		url:'/Query',
		templateUrl:'resources/js/query/query.html',
		controller:'queryController'
	}).state('EmployeeQuery',{
		url:'/EmployeeQuery',
		templateUrl:'resources/js/managerQuery/queryList.html',
		controller:'MangerQueryController'
	}).state('Tracking',{
		url:'/Tracking',
		templateUrl:'resources/js/tracking/tracking.html',
		controller:'trackingController'
			
	}).state('ExEmployeeRegister',{
		url:'/ExEmployeeRegister',
		templateUrl:'resources/js/exemployee/register.html',
		controller:'registercontroller'

	}).state('Search',{
		url:'/Search',
		templateUrl:'resources/js/search/search.html',
		controller:'searchcontroller'
	})
});
application.controller('homeController', function($scope, $http,$log) {
	$scope.getLinks = function() {
		$http.get(domain + '/getPages').success(
				
				function(data, status, headers, config) {
					/*alert(JSON.stringify({
						data : data
					}));*/
					$scope.pageList = data.pages;
					$scope.userLogin=data.lastLogin;
					$scope.roleId=data.roleId;
					$log.info($scope.userLogin)
					$log.info($scope.roleId)
					
				}).error(function(data, status, headers, config) {
					
		})
	}
});

application.directive('hrmsBlockLoader',function($window,$timeout){
	return {
		restrict:'AEC',
		templateUrl:'resources/js/directives/hrms-spinner.htm',
		link:function(scope,element,attrs){
			angular.element(element).css('display','none');
			angular.element(element).css({'z-index':'2000','background':'white','opacity':'0.5','position':'absolute','height':'100%','width':'100%'});
			scope.$on('cfpLoadingBar:started',function(event,data){
				angular.element(element).css('display','block');
			});
			
			scope.$on('cfpLoadingBar:completed',function(event,data){				
				angular.element(element).css('display','none');		
			});
		}
	}
});

/*application.directive('customAlertsInitiator',function($mdToast){
	return{
	  	restrict:'AEC',
	    scope:{
	    	hide:'@',
	      position:'@'
	    },
	    link:function(scope,elem,attrs){
	    	window.alert = function(message){
	          var toast = $mdToast.simple()
	          .content(message)
	          .action('Ok')
	          .highlightAction(true);

	          toast._options.position = scope.position;
	          toast._options.hideDelay = parseInt(scope.hide);
	          $mdToast.show(toast);
	          $timeout(function(){
	        	  return;
	          },2000)
	      	}    
	    }
	  }
	});*/

application.directive("datepicker1", function () {
    return {
        restrict: "A",
        require: "ngModel",
        scope:{
        	format:"@",
        	mindate:"@"
        },
        link: function (scope, elem, attrs, ngModelCtrl) {
            var updateModel = function (dateText) {
                // call $apply to bring stuff to angular model
                scope.$apply(function () {
                    ngModelCtrl.$setViewValue(dateText);
                });
            };
                var options = {
                dateFormat: scope.format,
                minDate: parseInt(scope.mindate),
                // handle jquery date change
                onSelect: function (dateText) {
                    updateModel(dateText);
                }

            };
            elem.datepicker(options);
        }
    }
});
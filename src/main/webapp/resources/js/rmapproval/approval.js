application.controller('approvalController',['$scope','$modal','$http','$timeout','$location','$rootScope',function($scope, $modal, $http,$timeout,$location,$rootScope) {
$scope.approvalInit = function() {
	//approvalInitialization ; getRMApprovalInitFromService
		$http.get(domain + '/approvalInitialization').success(
				function(data, status, headers, config) {
					$scope.approvalBody = data.jsonArray;
				}).error(function(data, status, headers, config) {
		})
	}
	$scope.approvalActionInit = function() {
		$http.get(domain + '/getResignationAction').success(
				function(data, status, headers, config) {
					//$scope.resignationaction = data.actionList;
					//$scope.noticeperiod = data.noticeList;
				//	alert(JSON.stringify({data:data.questions}))
					//$scope.formData={}
					//$scope.formData.rmfeedback=data.questions;
					$scope.rmfeedback=data.questions;
					$scope.employee_code=$scope.params.empcode;
					$scope.emp_fname=$scope.params.emp_firstname;
					$scope.emp_lname=$scope.params.emp_lastname;
					//alert(JSON.stringify({data:$scope.employee_code}))
				}).error(function(data, status, headers, config) {
			alert('Error');
		})
	
		
		/*$scope.resignAction= function(formName) {
			$scope.resAction='Accept';
			$scope.RadioChange=function(s){
				$scope.actionSelected=s;
				alert($scope.actionSelected+' '+ s);
			}*/
		
	}
	$scope.resignAction= function(formName) {
		var data='answerList='+JSON.stringify({data:$scope.rmfeedback})+'&resignAction='+$scope.resAction+'&feedbackon='+$scope.employee_code;
		$http({
			method:'POST',
			url:domain+'/insertRmFeedback',
			data:data,
			headers:{
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).success(function(data,status,headers,config){
			alert(data.status);
			location.reload();
			//alert('success');
		}).error(function(data,status,headers,config){
			alert('error');
		})
		/*var data='answerList='+JSON.stringify({data:$scope.rmfeedback})+'&resignAction='+$scope.resAction;
		alert(data);
		$http.post(domain+'/insertRmFeedback',data).success(function(data,status,headers,config){
			alert('success');
		})
		.error(function(data,status,headers,config){
			alert('error');
		});*/
		/*
		var data = 'action=' + $scope.resignation_action + '&noticeTime='
				+ $scope.notice_period + '&comment=' + $scope.comments
				+ '&empcode=' + $scope.employee_code + '&empfirstname='+ $scope.emp_fname + '&emplastname='+$scope.emp_lname;
		$http.get(domain + '/confirmApprovalActionRM?' + data).success(
				function(data, status, headers, config) {
					$scope.msg=data.result;
					alert($scope.msg);
					location.reload();
				}).error(function(data, status, headers, config) {
			alert('Error');
		})
	*/
		//alert($scope.rmfeedback[0].count + " radio button "+ $scope.resAction)
		}
	$scope.openAction = function(employeeCode,empfirstname,emplastname) {
		
		var scope=$rootScope.$new();
		scope.params={empcode:employeeCode,emp_firstname:empfirstname,emp_lastname:emplastname};
	    
		var modalInstance = $modal.open({
			scope:scope,
			templateUrl : 'resources/js/rmapproval/approvalActionModal.html',
			controller 	: 'approvalController',
			resolve:{
				name: function(){
					return name;
				}
			}
		});
	}
}]);
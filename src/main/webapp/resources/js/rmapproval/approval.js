application.controller('approvalController',['$scope','$modal','$http','$timeout','$location','$rootScope',function($scope, $modal, $http,$timeout,$location,$rootScope) {
$scope.approvalInit = function() {
		$http.get(domain + '/approvalInitialization').success(
				function(data, status, headers, config) {
					$scope.approvalBody = data.jsonArray;
				}).error(function(data, status, headers, config) {
		})
	}
	$scope.approvalActionInit = function() {
		$http.get(domain + '/getResignationAction').success(
				function(data, status, headers, config) {
					$scope.resignationaction = data.actionList;
					/*$timeout(function() {
						$scope.notice_period = data.noticeList;
					}, 2000);*/
					$scope.noticeperiod = data.noticeList;
				}).error(function(data, status, headers, config) {
			alert('Error');
		})
		$scope.employee_code=$scope.params.empcode;
		$scope.emp_fname=$scope.params.emp_firstname;
		$scope.emp_lname=$scope.params.emp_lastname;
		
		/*$scope.resignAction= function(formName) {
			$scope.resAction='Accept';
			$scope.RadioChange=function(s){
				$scope.actionSelected=s;
				alert($scope.actionSelected+' '+ s);
			}*/
		
	}
	$scope.resignAction= function(formName) {
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
	}
	$scope.openAction = function(employeeCode,empfirstname,emplastname) {
		
		var scope=$rootScope.$new();
		scope.params={empcode:employeeCode,emp_firstname:empfirstname,emp_lastname:emplastname};
	    
		var modalInstance = $modal.open({
			scope:scope,
			templateUrl : 'resources/js/approvalActionModal.html',
			controller 	: 'approvalController',
			resolve:{
				name: function(){
					return name;
				}
			}
		});
	}
}]);
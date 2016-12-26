application.controller('hrfeedbackcontroller', function($scope, $http, $modal,
		$rootScope, $window) {
	/* alert("hii"); */
	var status = 7;
	$scope.init = function() {
		/* Employee grid information */
		$http.get(domain + '/getnoduesemplist?status=' + status).success(
				function(data, status, headers, config) {
					$scope.hrfeedback = data.emplist;
					/*alert($scope.hrfeedback);*/
				})
	}
	$scope.EmployeeFeedback = function(empcode) {
		var emp_code = empcode;

		var scope = $rootScope.$new();
		scope.emp_code = emp_code;
		var modalInstance = $modal.open({
			scope : scope,
			templateUrl : "resources/js/hrfeedback/exithrmodal.html",
			controller : 'feedbackmodalcontroller'
		});
	}
	$scope.EmployeeFeedbackStatus = function(empcode) {
		var emp_code = empcode;

		var scope = $rootScope.$new();
		scope.emp_code = emp_code;
		scope.params={employeecode:emp_code}
		var modalInstance = $modal.open({
			scope : scope,
			templateUrl : "resources/js/hrfeedback/feedbackmodal.html",
			controller : 'hrfeedbackcontroller'
		});
	}
	$scope.empfeedback=function(empcode)
	{
		$scope.emp_code=$scope.params.employeecode;
$http.get(domain+'/employeefeedbackstatus?employeecode='+$scope.emp_code)
	.success(function(data,status,headers,config){
	 $scope.feedbackstatus=data.statusemp;
	 alert($scope.feedbackstatus)
		
	})
.error(function(data,status,headers,config){
	/*alert('not found');*/
		})
	}
	
});
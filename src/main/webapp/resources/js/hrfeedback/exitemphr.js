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
});
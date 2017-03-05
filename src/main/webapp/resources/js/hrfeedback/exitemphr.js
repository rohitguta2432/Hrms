application.controller('hrfeedbackcontroller', function($scope, $http, $modal,
		$rootScope, $window) {
	/* alert("hii"); */
	var status = 7;
	var stage=2;
	$scope.init = function() {
		/* Employee grid information */
		$http.get(domain + '/getnoduesemplist?status=' + status+'&stageid='+stage).success(
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

	 $scope.feedback_status=false;
/*for(var i=0;i<=$scope.feedbackstatus;i++)
	{
if($scope.feedbackstatus[i]='undefined')
		{

		$scope.feedback_status=false;
		}
	}*/
})
.error(function(data,status,headers,config){
	/*alert('not found');*/
		})
	}
	
});
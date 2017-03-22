application.controller('exitempcontroller', function($scope, $http,$location) {
	//page  initialization
	$scope.empfeedback=function(){
	$http.get(domain + '/empfeedback')
	.success(
			function(data, status, headers, config) 
			{
		   	    $scope.Employeecode = data.empcode;
				$scope.empfirstname = data.empname;
				$scope.empdepartment = data.spokecode;
				$scope.empdesignation = data.designation;
				$scope.Emplocation = data.location;
				$scope.empquestion = data.empfeedbackquestion;
				$scope.feedbackstatus=data.hasOwnProperty("feedbackdetails")?true:false;
				$scope.error="Feedbacks are submitted";
			})
	.error(function(data, status, headers, config){
		alert('error')
		});

$scope.submit = function(form) {
var emp_data = 'emp_feedback=' + JSON.stringify({
			data : $scope.empquestion
		});
		$http({
			method : 'POST',
			url : domain + '/insertempfeedback',
			data : emp_data,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).success(function(data) {
			alert("Feedbacks has been submitted");
				location.reload();
		}).error(function() {
			
		})
	}
	}
});
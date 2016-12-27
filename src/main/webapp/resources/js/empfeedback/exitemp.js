application.controller('exitempcontroller', function($scope, $http,$location) {

	$http.get(domain + '/empfeedback').success(
			function(data, status, headers, config) {

				/* alert('data are found '+ data); */
				$scope.Employeecode = data.empcode;
				$scope.empfirstname = data.empname;
				$scope.empdepartment = data.department;
				$scope.empdesignation = data.designation;
				$scope.Emplocation = data.location;
				$scope.empquestion = data.empfeedbackquestion;
				/*alert($scope.empquestion)*/
			})
	$scope.submit = function(form) {

		var emp_data = 'emp_feedback=' + JSON.stringify({
			data : $scope.empquestion
		});
		/*alert(emp_data)*/
		$http({
			method : 'POST',
			url : domain + '/insertempfeedback',
			data : emp_data,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).success(function(data) {
			alert("submitted emp feedback")
			location.reload();
		}).error(function() {
			/*alert("errors")*/
		})
	}

});
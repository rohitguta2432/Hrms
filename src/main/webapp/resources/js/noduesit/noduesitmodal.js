application.controller('noduesitmodaljscontroller', function($scope, $http,
		$window,$location) {
	$http
			.get(
					domain + '/getemployeemodalinfo?employee_code='
							+ $scope.emp_code).success(
					function(data, status, headers, config) {
						$scope.emplycode = data.empcode;
						$scope.empfirstname = data.firstname;
						$scope.emplastname = data.lastname;
						$scope.empdepartment = data.department;
						$scope.empdesignation = data.designation;
						$scope.emplocation = data.location;

						$http.get(domain + '/getitassets').success(
								function(data, status, headers, config) {
									/*
									 * alert('the data returned is :
									 * '+JSON.stringify({data : data}));
									 */
									$scope.nodueitassets = data.itassets;
									/* alert($scope.nodueitassets) */
								})

					})
	$scope.selectedItems = [];
	$scope.itemnotselected = [];
	$scope.rejected_final_status = [ 3 ];
	$scope.accepted_status = [ 2 ];

	$scope.submit = function(form) {
		angular.forEach($scope.nodueitassets, function(emp) {
			if (emp.selected) {

				$scope.selectedItems.push(emp.name);

			}
		})
		/* alert("store item "+$scope.selectedItems) */

		var emp_data = 'emp_assets=' + $scope.selectedItems + '&comments='
				+ $scope.empcomments + '&emp_code=' + $scope.emp_code;
		/* alert("accepted "+emp_data) */
		$http({
			method : 'POST',
			url : domain + '/insertitassets',
			data : emp_data,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).success(function(data) {
			alert("submitted it assets")
			location.reload();
		}).error(function() {
			/* alert("errors") */
		})
	}

	$scope.reject = function() {
		angular.forEach($scope.nodueitassets, function(emp) {
			if (emp.selected) {
				$scope.selectedItems.push(emp.name);
			}
		})
		angular.forEach($scope.nodueitassets, function(emp) {
			if (!emp.selected) {
				$scope.itemnotselected.push(emp.name)
			}
		})

		var emp_data = 'comments=' + $scope.empcomments + '&emp_code='
				+ $scope.emplycode + '&not_received=' + $scope.itemnotselected
				+ '&received_assets=' + $scope.selectedItems + '&final_status='
				+ $scope.rejected_final_status;

		$http.get(domain +'/rejectempassets?'+emp_data)
		.success(function(data) {
			alert("rejected it assets")
     location.reload();
		})
	}

});
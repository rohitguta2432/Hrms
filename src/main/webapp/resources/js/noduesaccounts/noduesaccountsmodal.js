application.controller('noduesaccountsmodalcontroller', function($scope, $http,
		$window, $modal,$location) {
	var departmentid;
	$http
			.get(
					domain + '/getemployeemodalinfo?employee_code='
							+ $scope.emp_code).success(
					function(data, status, headers, config) {
						/* alert('data are found '+ data); */

						$scope.emplycode = data.empcode;
						$scope.empfirstname = data.empname;
					    $scope.empdepartment = data.department;
						$scope.empdesignation = data.designation;
						$scope.emplocation = data.location;

						$http.get(
								domain + '/getacccountassets?employee_code='
										+ $scope.emp_code).success(
								function(data, status, headers, config) {
									$scope.nodueaccountassets = data.accountassets;
								/*alert($scope.nodueaccountassets)*/
									departmentid=$scope.nodueaccountassets[0].DepartmentId
							alert(departmentid)
								})
					})

	$scope.selectedItems = [];
	$scope.itemnotselected = [];
	$scope.rejected_final_status = [ 3 ];
	$scope.accepted_status = [ 2 ];
	/* submitting value */
	$scope.submit = function(form) {
		angular.forEach($scope.nodueaccountassets, function(emp) {
			if (emp.selected) {

				$scope.selectedItems.push(emp.name);
			}
		})
		var emp_data = 'emp_assets=' + $scope.selectedItems
				+ '&accounts_comments=' + $scope.empcomments + '&emp_code='
				+ $scope.emp_code + '&final_status=' + $scope.accepted_status+'&departmentId='+departmentid;
	/*	alert(emp_data)*/

		$http({
			method : 'POST',
			url : domain + '/insertaccountassets',
			data : emp_data,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).success(function(data) {
			alert("submitted account assets")
			location.reload();
		}).error(function() {
			/*alert("errors")*/
		})

	}
$scope.reject = function() {
		angular.forEach($scope.nodueaccountassets, function(emp) {
			if (emp.selected) {
				$scope.selectedItems.push(emp.name);
			}
		})
		/*alert("selected  item " + $scope.selectedItems)*/
		angular.forEach($scope.nodueaccountassets, function(emp) {
			if (!emp.selected) {
				$scope.itemnotselected.push(emp.name)
			}
		})
		/*alert("not received  item " + $scope.itemnotselected)*/
		var emp_data = 'comments=' + $scope.empcomments + '&emp_code='
				+ $scope.emplycode + '&not_received=' + $scope.itemnotselected
				+ '&received_assets=' + $scope.selectedItems + '&final_status='
				+ $scope.rejected_final_status+'&departmentId='+departmentid;
		 alert(emp_data) 
		$http.get(domain + '/rejectempassets?' + emp_data)
		alert("rejected account assets")
		location.reload();
}

});
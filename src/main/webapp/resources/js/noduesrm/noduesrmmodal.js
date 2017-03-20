application.controller('noduesrmmodalcontroller', function($scope, $http,
		$window, $modal,$location) {
var departmentid=1;
	$http
			.get(
					domain + '/getemployeemodalinfo?employee_code='
							+ $scope.emp_code).success(
					function(data, status, headers, config) {

						/* alert('data are found'); */

						$scope.emplycode = data.empcode;
						$scope.empfirstname = data.empname;
					    $scope.empdepartment = data.spokecode;
						$scope.empdesignation = data.designation;
						$scope.emplocation = data.location;

						$http.get(domain + '/getNoDuesAssets').success(
								function(data, status, headers, config) {
								$scope.noduesrmassets = data.list;
									/* alert($scope.noduesrmassets) */
								})
					})
	$scope.selectedItems = [];
	$scope.itemnotselected = [];
	$scope.rejected_final_status = [ 3 ];
	$scope.accepted_status = [ 2 ];

	$scope.submit = function(form) {

		angular.forEach($scope.noduesrmassets, function(emp) {
			if (emp.selected)
				$scope.selectedItems.push(emp.name)

		})

		var emp_data = 'emp_assets=' + $scope.selectedItems + '&comments='
				+ $scope.empcomments + '&emp_code=' + $scope.emplycode;
		/*alert("accepted " + emp_data)*/
$http({
			method : 'POST',
			url : domain + '/insertrmassets',
			data : emp_data,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		})
		.success(function(data) {
			alert("submitted rm assets")
			location.reload();
		}).error(function() {
			/*alert("errors")*/
		})
	}

	$scope.reject = function() {
		angular.forEach($scope.noduesrmassets, function(emp) {
			if (emp.selected) {
				$scope.selectedItems.push(emp.name);
			}
		})
		/*alert("selected  item " + $scope.selectedItems)*/
		angular.forEach($scope.noduesrmassets, function(emp) {
			if (!emp.selected) {
				$scope.itemnotselected.push(emp.name)
			}
		})
		/*alert("not received  item " + $scope.itemnotselected)*/

		var emp_data = 'comments=' + $scope.empcomments + '&emp_code='
				+ $scope.emplycode + '&not_received=' + $scope.itemnotselected
				+ '&received_assets=' + $scope.selectedItems + '&final_status='
				+ $scope.rejected_final_status+'&departmentId='+departmentid;
		/*alert(emp_data)*/
		$http.get(domain + '/rejectrmpassets?' + emp_data)
		.success(function(data) {
			alert("rejected rm asserts")
			location.reload();
		})
	}

});
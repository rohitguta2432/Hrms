application
		.controller(
				'exitratedJs',
				function($scope, $http,$location) {

					/* alert("hii"); */

					$scope.headerText = '# How Would You Rate the following Aspect of Softage..';

					$http
							.get(domain + '/empratingfeedback')
							.success(
									function(data, status, headers, config) {
										/* alert('data are found'); */

										$scope.empratingquestion = data.empratingfeedbackquestion;

										/* alert($scope.empratingquestion) */
									})
					$scope.selectedItems = [];

					$scope.getRadioValue = function(feedback) {

						$scope.selectedItems.push(feedback);

						/* alert($scope.selectedItems); */

					}
					$scope.submit = function(form) {
						var emp_data = 'emprating_feedback=' + JSON.stringify({
							data : $scope.selectedItems});
    	
$http({
									method : 'POST',
									url : domain + '/insertempratingfeedback',
									data : emp_data,
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded'
									}
								}).success(function(data) {
							alert("submitted emprating feedback")
							location.reload();
						}).error(function() {
							/*alert("errors")*/
						})
					}

				});
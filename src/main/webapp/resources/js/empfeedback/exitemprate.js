application.controller('exitratedJs',function($scope, $http, $location) {
$http.get(domain + '/empratingfeedback')
							.success(
									function(data, status, headers, config) {
									$scope.empratingquestion = data.empratingfeedbackquestion;
									$scope.feedbackstatus=data.hasOwnProperty("feedbackdetails")?true:false;
									$scope.error="Feedbacks Ratings are submitted";
                              	})
					$scope.selectedItems = [];
					$scope.getRadioValue = function(feedback) {
                    $scope.selectedItems.push(feedback);
                     /* alert($scope.selectedItems); */
                       }
					$scope.submit = function(form) {
					var emp_data = 'emprating_feedback=' + JSON.stringify({
					data : $scope.selectedItems
						});
					$http(
						{
						method : 'POST',
						url : domain + '/insertempratingfeedback',
						data : emp_data,
						headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
						}
						}).success(function(data) {
					   alert("Rating feedbacks are submitted");
					   location.reload();
					}).error(function() {
							/* alert("errors") */
					})
					}

				});
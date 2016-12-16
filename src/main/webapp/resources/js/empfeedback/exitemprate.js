application.controller('exitratedJs',function($scope,$http) {
	$scope.headerText='# How Would You Rate the following Aspect of Softage..';
	
	

	$scope.submit=function(form)
	{
		
		/*alert($scope.emp.trainingValue);
		
		alert($scope.emp.NatureValue);
		
		alert($scope.emp.AttentionValue);
		
		alert($scope.emp.growth);
		
		alert($scope.emp.skills);
		
		alert($scope.emp.salary);
		
		alert($scope.emp.increment);
		
		alert($scope.emp.promotion);
		
		alert($scope.emp.information);
		
		alert($scope.emp.satisfaction);
		
		alert($scope.emp.balance);*/
		
		
		var emp_data='emp_training='+$scope.emp.trainingValue+'&emp_nature='+$scope.emp.NatureValue+'&emp_attention='+$scope.emp.AttentionValue+'&emp_growth='+$scope.emp.growth+'&emp_skills='+$scope.emp.skills+'&emp_salary='+$scope.emp.salary+'&emp_increment='+$scope.emp.increment+'&emp_promotion='+$scope.emp.promotion+'&emp_information='+$scope.emp.information+'&emp_satisfaction='+$scope.emp.satisfaction+'&emp_balance='+$scope.emp.balance;
		
		/*alert("data are "+emp_data);*/
		
		$http.get(domain+'/emprate?'+emp_data)
		.success(function(data, status, headers, config){
		alert('submit successfully');
					})
				.error(function(data, status, headers, config){
				/*alert(data);*/
				})

	}
	

});
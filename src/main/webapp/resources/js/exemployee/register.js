application.controller('registercontroller',function($scope,$http) {
	
/*alert($scope.Employeecode);*/

	$scope.submit=function(form){
		 var emp_data='emp_code='+$scope.Employeecode+'&emp_email='+$scope.Employeeemail+'&emp_password='+$scope.passwordverify;	
		 alert(emp_data)
			
$http.get(domain+'/exemployee?'+emp_data)

	alert("done")
	}

});

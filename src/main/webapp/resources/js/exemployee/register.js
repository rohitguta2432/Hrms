application.controller('registercontroller',function($scope,$http,$location) {

	$scope.password = "";
	$scope.submit=function(form){
		 var emp_data='emp_code='+$scope.Employeecode+'&emp_email='+$scope.Employeeemail+'&emp_password='+$scope.passwordverify;	

		 $http.get(domain+'/exemployee?'+emp_data)
		 alert("registration done")
		 location.reload();
	}

});

application.controller('noduesaccountsmodalcontroller',function($scope,$http,$window) {
	

   $scope.emp_code = $window.sessionStorage.getItem("Mydata");
/*$scope.$on("Mydata",function(event, emp_code){*/
	


var store=$scope.emp_code;
	
	var selectedempcode='employee_code='+$scope.emp_code;
		
	/*alert(' selected code are '+selectedempcode);*/
		
	/*$http.get(domain+'/gethrfeedbackempcode?'+selectedempcode)*/
	$http.get(domain+'/getaccountmodalassets?'+selectedempcode)
			.success(function(data, status, headers, config){
			/*alert('data are found');*/
				
				$scope.emplycode=data.empcode;
				$scope.empfirstname=data.firstname;
				$scope.emplastname=data.lastname;
				$scope.empdepartment=data.department;
				$scope.empdesignation=data.designation;
				$scope.emplocation=data.location;
				
				
				$http.get(domain+'/getAssets')
				.success(function(data,status,headers,config){
					/*alert('the data returned is : '+JSON.stringify({data : data}));*/
					$scope.nodueaccountassets=data.list;
					alert($scope.nodueaccountassets);
					})
				})
				
});
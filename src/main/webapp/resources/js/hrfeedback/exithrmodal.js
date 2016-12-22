application.controller('feedbackmodalcontroller',function($scope,$http,$window,$modal) {
	

  /* $scope.emp_code = $window.sessionStorage.getItem("Mydata");*/
/*$scope.$on("Mydata",function(event, emp_code){*/
   
   /*var store=$scope.emp_code;*/
	
	/*var selectedempcode='employee_code='+$scope.emp_code;*/
		
	
	/*alert(selectedempcode)*/
	
	/*alert("hr feedback "+$scope.emp_code)*/
	
	$http.get(domain+'/getemployeemodalinfo?employee_code='+$scope.emp_code)
			.success(function(data, status, headers, config){
			/*alert('data are found '+ data);*/
				
				$scope.employeecode=data.empcode;
				$scope.employeefirstname=data.firstname;
				$scope.employeelastname=data.lastname;
				$scope.employeedepartment=data.department;
				$scope.employeedesignation=data.designation;
				$scope.employeelocation=data.location;
				
		$http.get(domain+'/gethrfeedbackquestions?employee_code='+$scope.emp_code)
			.success(function(data,status,headers,config){
				/*	alert('the data returned is : '+JSON.stringify({data : data}));*/
					
					$scope.hrquestion=data.hrquestionslist;
					/*alert($scope.hrquestion)*/
					})
			})
				
				

	/*submitting value*/
$scope.submit=function(form){
		/*alert($scope.hrquestion);*/
var emp_data='hr_feedback='+JSON.stringify({data:$scope.hrquestion})+'&emp_code='+$scope.emp_code;
	            	alert(emp_data)
$http({
        method: 'POST',
        url: domain+'/inserthrfeedback',
        data:emp_data,
        headers:
        {
        	'Content-Type':'application/x-www-form-urlencoded'
        }
    }).success(function(data){
       alert("submitted hr feedback")
    }).error(function(){
        alert("errors")
    })
	            	
	  }
				
	});
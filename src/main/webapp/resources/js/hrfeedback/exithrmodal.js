application.controller('feedbackmodalcontroller',function($scope,$http,$window,$modal,$location) {
	
$http.get(domain+'/getemployeemodalinfo?employee_code='+$scope.emp_code)
			.success(function(data, status, headers, config){
			    $scope.employeecode=data.empcode;
				$scope.employeefirstname=data.empname;
				$scope.employeedepartment=data.spokecode;
				$scope.employeedesignation=data.designation;
				$scope.employeelocation=data.location;
				
		$http.get(domain+'/gethrfeedbackquestions?employee_code='+$scope.emp_code)
			.success(function(data,status,headers,config){
			$scope.hrquestion=data.hrquestionslist;
					
					})
			})
	/*submitting value*/
$scope.submit=function(form){
		/*alert($scope.hrquestion);*/
var emp_data='hr_feedback='+JSON.stringify({data:$scope.hrquestion})+'&emp_code='+$scope.emp_code;
	            	/*alert(emp_data)*/
$http({
        method: 'POST',
        url: domain+'/inserthrfeedback',
        data:emp_data,
        headers:
        {
        	'Content-Type':'application/x-www-form-urlencoded'
        }
    }).success(function(data){
       alert("Hr feedbacks has been submitted");
       location.reload();
    }).error(function(){
       /* alert("errors")*/
    })
	            	
	  }
				
	});
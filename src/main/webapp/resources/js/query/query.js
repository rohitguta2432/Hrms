application.controller('queryController',function($scope,$http){
	$scope.getDepartments=function(){
		alert('initializing ')
		$http.get(domain+'/getDepartments')
		.success(function(data,status,headers,config){		
			$scope.departments=data;
		})
		.error(function(data,status,headers,config){
			alert('the error returned is : '+JSON.stringify({data : data}));
		})
	}
	$scope.submit=function(form){
			var emp_data='deptId='+$scope.departmentId+'&quertext='+$scope.queryText;
			//alert(JSON.stringify({data:$scope.selReasons}))
			$http.get(domain+'/employeeQuery?'+emp_data)
			.success(function(data, status, headers, config){
				//alert('success'+JSON.stringify({data: data}));
				//alert(data.result);
			})
			.error(function(data, status, headers, config){
				alert('error'+JSON.stringify({data: data}));
			})
			alert('successfully submitted');
	}
});
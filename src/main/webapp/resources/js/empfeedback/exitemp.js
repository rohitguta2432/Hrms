application.controller('exitempcontroller',function($scope,$http) {
	$scope.headerText='exit Employee Details..';
		/*$scope.data=[];*/
	
	$scope.init=function(){
	/*	alert('initializing_department')*/
		$http.get(domain+'/getDepartment')
		.success(function(data,status,headers,config){
			/*alert('the data returned is : '+JSON.stringify({data : data}));*/
			$scope.Empdepartment=data.DepartmentList;
			
		})
		.error(function(data,status,headers,config){
			/*alert('the error returned is : '+JSON.stringify({data : data}));*/
		})
		
		
		/*$scope.init=function(){*/
			/*alert('initializing_designation')*/
			$http.get(domain+'/getDesignation')
			.success(function(data,status,headers,config){
				/*alert('the data returned is : '+JSON.stringify({data : data}));*/
				$scope.Empdesignation=data.designationEmplist;
				
				
			})
			.error(function(data,status,headers,config){
				/*alert('the error returned is : '+JSON.stringify({data : data}));*/
			})
		}
	
			/*$scope.init=function(){
				alert('initializing_location')
				$http.get(domain+'/getLocation')
				.success(function(data,status,headers,config){
					alert('the data returned is : '+JSON.stringify({data : data}));
					$scope.Emplocation=data.locationList;
					
				})
				.error(function(data,status,headers,config){
					alert('the error returned is : '+JSON.stringify({data : data}));
				})
			}*/
		
	$scope.submit=function(form){
var emp_data='emp_name='+$scope.Empname+'&emp_department='+$scope.Empdepartment+'&emp_designation='+$scope.Empdesignation+'&emp_general='+$scope.Empgeneral+'&emp_environment='+$scope.Empenvironment+'&emp_opportunity='+$scope.Empopportunity+'&emp_description='+$scope.Empdescription+'&emp_responsibility='+$scope.Empresponsibility+'&emp_manager='+$scope.Empmanager+'&emp_decision='+$scope.Empdecision+'&emp_expectation='+$scope.Empexpectation+'&emp_identityfy='+$scope.Empidentityfy;
$http.get(domain+'/exitemp?'+emp_data)
.success(function(data, status, headers, config){
/*alert('submit successfully');*/
			})
		.error(function(data, status, headers, config){
		/*alert(data);*/
		})
		/*alert("error while submitting")*/
		}		
		});
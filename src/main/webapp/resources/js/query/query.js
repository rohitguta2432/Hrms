application.controller('queryController', ['$scope', '$http','$modal','$location','$rootScope', function ($scope, $http,$modal,$location,$rootScope) {
	
	
	
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
	$scope.submit=function(){
		
		   alert(" departmentId ="+$scope.departmentId );
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
	
	$scope.getlastQueryReply=function(){
		
			//alert(JSON.stringify({data:$scope.selReasons}))
			$http.get(domain+'/getlastQueryReply')
			.success(function(data, status, headers, config){
				
				
				//alert('success'+JSON.stringify({data: data}));
				//alert(data.result);
			})
			.error(function(data, status, headers, config){
				alert('error'+JSON.stringify({data: data}));
			})
			alert('successfully submitted');
	}
	
	
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
	
	
	$scope.createQuery=function(){
		
		var scope=$rootScope.$new();
		var modalInstance = $modal.open({
			scope:scope,
			templateUrl : 'resources/js/query/createQuery.html',
			controller 	: 'queryController',
			resolve:{
				name: function(){
					return name;
				}
			}
		});
		
		
		
	}
	
	$scope.getEmpQueryList=function(){
		alert('initializing ')
		$http.get(domain+'/getEmpQueryList')
		.success(function(data,status,headers,config){		
			$scope.empQuerys=data;
		})
		.error(function(data,status,headers,config){
			alert('the error returned is : '+JSON.stringify({data : data}));
		})
	}
	
	
	

	
	$scope.getlastQuery=function(){
		
		var scope=$rootScope.$new();
		var modalInstance = $modal.open({
			scope:scope,
			templateUrl : 'resources/js/query/queryReply.html',
			controller 	: 'queryController',
			resolve:{
				name: function(){
					return name;
				}
			}
		});
		
		
		
	}
	
}]);
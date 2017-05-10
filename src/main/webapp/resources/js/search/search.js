application.controller("searchController",function($scope,$http,$location){
	
	$scope.getData = 
		function()
		{
		//alert($scope.empcode);
		$http.post(domain+'/empSearchInfo?empCode='+$scope.empcode)
		.success(function(data,status,headers,config){
			alert('Success ');
		})
		.error(function(data,status,headers,config){
			alert('Unable to Search Employee ');
		})
		};
});
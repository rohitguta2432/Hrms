application.controller("searchController",function($scope,$http,$location){
	
	$scope.getData = 
		function()
		{
		$http.post(domain+'/empSearchInfo?empCode='+$scope.empcode)
		.success(function(data,status,headers,config){
			$scope.searchList = data;
		})
		.error(function(data,status,headers,config){
			alert('Unable to Search Employee ');
		})
		};
});
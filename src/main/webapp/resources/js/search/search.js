application.controller("searchController",function($scope,$http,$location){
	

	$scope.getData =
		function()
		{
		$http.post(domain+'/empSearchInfo?empCode='+$scope.empcode)
		.success(function(data,status,headers,config){
			$scope.searchStatus=data.msg;
			if($scope.searchStatus=='Success'){
			$scope.searchList = data;
			}
			else
			{
			alert($scope.searchStatus);
			}
		})
		.error(function(data,status,headers,config){
			alert('error ');
		})
		
		};
		
});
application.controller('resignationController',function($scope,$http,$location){
	$scope.init=function(){
		//alert('initializing')
		$http.get(domain+'/resignationIni')
		.success(function(data,status,headers,config){
			//alert('the data returned is : '+JSON.stringify({data : data}));
			$scope.resignationReason=data.ReasonJson.reasonList;
			$scope.reldate=data.reldate.releaseDate;
		})
		.error(function(data,status,headers,config){
			alert('Error ');
		})
	}
	$scope.submit=function(form){
			var emp_data='emp_reason='+$scope.selReason+'&emp_comments='+$scope.comments;
			//alert(JSON.stringify({data:$scope.selReasons}))
			$http.get(domain+'/resignation?'+emp_data)
			.success(function(data, status, headers, config){
				//alert('success'+JSON.stringify({data: data}));
				//alert(data.result);
				$scope.resignationResult=data.result;
		
				alert($scope.resignationResult);
				location.relaod();
			})
			.error(function(data, status, headers, config){
				alert('error');
			})
			
	}
});
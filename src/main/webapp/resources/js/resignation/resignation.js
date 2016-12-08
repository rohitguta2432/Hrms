application.controller('resignationController',function($scope,$http){
	$scope.init=function(){
		//alert('initializing')
		$http.get(domain+'/resignationIni')
		.success(function(data,status,headers,config){
			//alert('the data returned is : '+JSON.stringify({data : data}));
			$scope.resignationReason=data.ReasonJson.reasonList;
			$scope.reldate=data.reldate.releaseDate;
		})
		.error(function(data,status,headers,config){
			alert('the error returned is : '+JSON.stringify({data : data}));
		})
	}
	$scope.submit=function(form){
			var emp_data='emp_reason='+$scope.resignationReason+'&emp_comments='+$scope.comments;
			$http.get(domain+'/resignation?'+emp_data)
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
application.controller('documentDownload', ['$scope', '$http', function ($scope, $http) {

	$scope.downloadItem=function(){
		alert(' geting Upload Items   ');
		$http.get(domain+'/getDownloadItem')
		.success(function(data,status,headers,config){
			//alert('the data returned is : '+JSON.stringify({data : data}));
			$scope.downloadItems=data;		
			alert("List  "+$scope.downloadItems);
		})
		.error(function(data,status,headers,config){
			alert('the error returned is : '+JSON.stringify({data : data}));
		})
	}

	$scope.download=function(resignId,itemId){
		alert("geting Upload Items "+resignId);
		var data="resignId="+resignId+"&itemId="+itemId;
		 $scope.$emit('download-start');
	        window.location.href=domain +"/download?"+data;

		
		
	/*	
		$http.get(domain+'/download?resignId='+resignId)
		.success(function(data,status,headers,config){
			
			
			
		})
		.error(function(data,status,headers,config){
			alert('the error returned is : '+JSON.stringify({data : data}));
		})*/
	}

}]);
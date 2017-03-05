application.controller('MangerQueryController', ['$scope', '$http','$modal','$location','$rootScope', function ($scope, $http,$modal,$location,$rootScope) {
	
	/*app.controller('MangerQueryController', ['$scope', '$http','$modal','$location','$rootScope', function ($scope, $http,$modal,$location,$rootScope) {*/
	
	$scope.getQueryList=function(){
		//alert('initializing ')
		$http.get(domain+'/getQueryList')
		.success(function(data,status,headers,config){		
			$scope.empQueryList=data;
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

	$scope.saveQueryManger=function(){
		var queryData="queryReply="+$scope.queryReply+"&queryId="+$scope.params.queryId;
		$http.get(domain+'/saveQueryManger?'+queryData)
		.success(function(data,status,headers,config){		
			$scope.empQueryList=data;
			alert("Query Save Successfully   ");
			location.reload();
		})
		.error(function(data,status,headers,config){
			alert('the error returned is : '+JSON.stringify({data : data}));
		})
	}
	$scope.getMessage=function(){
		//alert('initializing ')
		
		$http.get(domain+'/getMessages?queryId='+$scope.params.queryId)
		.success(function(data,status,headers,config){		
			$scope.queryMssages=data;
		})
		.error(function(data,status,headers,config){
			alert('the error returned is : '+JSON.stringify({data : data}));
		})
	}
	
	$scope.openQueryView=function(empcode,queryText,queryId){
		
		var scope=$rootScope.$new();
		scope.params={empcode:empcode,queryText:queryText,queryId:queryId};
		
		var modalInstance = $modal.open({
			scope:scope,
			templateUrl : 'resources/js/managerQuery/queryview.html',
			controller 	: 'MangerQueryController',
			resolve:{
				name: function(){
					return name;
				}
			}
		});
		
		
		
	}
	
	
	
}]);
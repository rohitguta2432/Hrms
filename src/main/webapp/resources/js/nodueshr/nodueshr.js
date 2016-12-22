application.controller('nodueshrcontroller',function($scope,$http,$modal,$rootScope,$window) {
	
	$scope.init=function(){
/*Employee grid information*/
			$http.get(domain+'/getnoduesemplist')
			.success(function(data,status,headers,config){
			$scope.nodueshrtjs=data.emplist;
			/*alert($scope.nodueshrtjs);*/
})
		
			error(function(data,status,headers,config){
			/*alert('not found');*/
				})
}
	$scope.EmployeeFeedback=function(empcode)
	{
           var emp_code=empcode;
    /*$window.sessionStorage.setItem("Mydata",emp_code);*/
 
    //$rootScope.$broadcast("Mydata",emp_code);
		/*alert("hr "+emp_code);*/
		var scope = $rootScope.$new();
		scope.emp_code = emp_code;
		
		var modalInstance = $modal.open({
			scope:scope,
      		templateUrl : "resources/js/nodueshr/nodueshrmodal.html",
      		controller :'nodueshrmodalcontroller'
		});
	}
});
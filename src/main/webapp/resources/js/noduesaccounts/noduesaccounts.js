application.controller('noduesaccountscontroller',function($scope,$http,$modal,$rootScope,$window) {
	
		
	$scope.init=function(){
/*Employee grid information*/
			$http.get(domain+'/getnoduesemplist')
			.success(function(data,status,headers,config){
			$scope.noduesaccountjs=data.emplist;
			/*alert($scope.nodueitjs);*/
})
		
			error(function(data,status,headers,config){
			/*alert('not found');*/
				})
}
	$scope.EmployeeFeedback=function(empcode)
	{
           var emp_code=empcode;
    /*$window.sessionStorage.setItem("Mydata",emp_code);*/
 
           var scope = $rootScope.$new();
   		scope.emp_code = emp_code;
    /*$rootScope.$broadcast("Mydata",emp_code);*/
    alert("account empcode "+emp_code);
		
var modalInstance = $modal.open({
	  scope:scope,
      templateUrl : "resources/js/noduesaccounts/noduesaccountsmodal.html",
	  controller :'noduesaccountsmodalcontroller'	
		});
}
});
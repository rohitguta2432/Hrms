application.controller('noduesaccountscontroller',function($scope,$http,$modal,$rootScope,$window) {
	
		
	$scope.init=function(){
/*Employee grid information*/
			$http.get(domain+'/getnoduesaccounts')
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
    $window.sessionStorage.setItem("Mydata",emp_code);
 
    /*$rootScope.$broadcast("Mydata",emp_code);
		alert("mephr "+emp_code);
		*/
var modalInstance = $modal.open({
      templateUrl : "resources/js/noduesaccounts/noduesaccountsmodal.html",
			controller :'noduesaccountsmodalcontroller'	
		});
}
});
application.controller('noduesaccountscontroller',function($scope,$http,$modal,$rootScope,$window) {
	var status=5;
	var stage=1;
	$scope.init=function(){
/*Employee grid information*/
			$http.get(domain+'/getnoduesemplist?status='+status+'&stageid='+stage)
			.success(function(data,status,headers,config){
			$scope.noduesaccountjs=data.emplist;
			/*alert($scope.nodueitjs);*/
})
		
		
}
	$scope.EmployeeFeedback=function(empcode,department)
	{
           var emp_code=empcode;
           var department_id=department;
    /*$window.sessionStorage.setItem("Mydata",emp_code);*/
 
           var scope = $rootScope.$new();
   		scope.emp_code = emp_code;
    /*$rootScope.$broadcast("Mydata",emp_code);*/
   /* alert("account empcode "+emp_code);*/
   		scope.department_id=department_id;
   		var modalInstance = $modal.open({
	  scope:scope,
      templateUrl : "resources/js/noduesaccounts/noduesaccountsmodal.html",
	  controller :'noduesaccountsmodalcontroller'	
		});
}
});
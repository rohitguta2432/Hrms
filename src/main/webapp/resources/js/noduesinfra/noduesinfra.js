application.controller('noduesinfracontroller',function($scope,$http,$modal,$rootScope,$window) {
	
	$scope.init=function(){
/*Employee grid information*/
			$http.get(domain+'/getnoduesinfra')
			.success(function(data,status,headers,config){
			$scope.noduesinfrasystem=data.empinfralist;
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
 
    /*$rootScope.$broadcast("Mydata",emp_code);
		alert("mephr "+emp_code);
		*/
           var scope=$rootScope.$new();
           scope.emp_code=emp_code;
           
           /*alert(emp_code)*/
           
var modalInstance = $modal.open({
	
      scope:scope,
      templateUrl : "resources/js/noduesinfra/noduesinframodal.html",
			controller :'noduesinframodalcontroller'	
		});
}
});
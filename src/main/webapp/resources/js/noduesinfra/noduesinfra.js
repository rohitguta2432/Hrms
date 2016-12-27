application.controller('noduesinfracontroller',function($scope,$http,$modal,$rootScope,$window) {
	var status=5;
	$scope.init=function(){
/*Employee grid information*/
			$http.get(domain+'/getnoduesemplist?status='+status)
			.success(function(data,status,headers,config){
			$scope.noduesinfrasystem=data.emplist;
			/*alert($scope.nodueshrtjs);*/
})
		
			error(function(data,status,headers,config){
			/*alert('not found');*/
				})
}
	$scope.EmployeeFeedback=function(empcode)
	{
           var emp_code=empcode;
           var scope=$rootScope.$new();
           scope.emp_code=emp_code;
           
    var modalInstance = $modal.open({
	
      scope:scope,
      templateUrl : "resources/js/noduesinfra/noduesinframodal.html",
			controller :'noduesinframodalcontroller'	
		});
}
});
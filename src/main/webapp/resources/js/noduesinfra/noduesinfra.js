application.controller('noduesinfracontroller',function($scope,$http,$modal,$rootScope,$window) {
	var status=5;
	var stage=1;
	$scope.init=function(){
/*Employee grid information*/
			$http.get(domain+'/getnoduesemplist?status='+status+'&stageid='+stage)
			.success(function(data,status,headers,config){
			$scope.noduesinfrasystem=data.emplist;
			/*alert($scope.nodueshrtjs);*/
})
		
			/*error(function(data,status,headers,config){
			alert('not found');
				})*/
}
	$scope.EmployeeFeedback=function(empcode,department)
	{
           var emp_code=empcode;
           var department_id=department;
           
           
           var scope=$rootScope.$new();
           scope.emp_code=emp_code;
           scope.department_id=department_id;
    var modalInstance = $modal.open({
	scope:scope,
      templateUrl : "resources/js/noduesinfra/noduesinframodal.html",
			controller :'noduesinframodalcontroller'	
		});
}
});
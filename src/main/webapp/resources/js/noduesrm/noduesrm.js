application.controller('noduesrmcontroller',function($scope,$http,$modal,$rootScope,$window) {
var status=5;
var stage=2;
	$scope.init=function(){
/*Employee grid information*/
		
			$http.get(domain+'/getrmnoduesemplist?status='+status+'&stageid='+stage)
			.success(function(data,status,headers,config){
			$scope.noduesrmsystem=data.emplist;
			
/*alert('1');*/
})
/*
		error(function(data,status,headers,config){
			alert('not found');
				})*/
}
	$scope.EmployeeFeedback=function(empcode)
	{
           var emp_code=empcode;
           var scope=$rootScope.$new();
           scope.emp_code=emp_code;
           
var modalInstance = $modal.open({
	
      scope:scope,
      templateUrl : "resources/js/noduesrm/noduesrmmodal.html",
			controller :'noduesrmmodalcontroller'	
		});
}
});
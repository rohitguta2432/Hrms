application.controller('noduesitcontroller',function($scope,$http,$modal,$rootScope,$window) {
var status=5;
	$scope.init=function(){
/*Employee grid information*/
			$http.get(domain+'/getnoduesemplist?status='+status)
			.success(function(data,status,headers,config){
			$scope.nodueitjs=data.emplist;
			/*alert($scope.nodueitjs);*/
})
		
			error(function(data,status,headers,config){
			/*alert('not found');*/
				})
}
	$scope.EmployeeFeedback=function(empcode)
	{
           var emp_code=empcode;
   /* $window.sessionStorage.setItem("Mydata",emp_code);*/
 
    /*$rootScope.$broadcast("Mydata",emp_code);
		alert("mephr "+emp_code);
		*/
           var scope=$rootScope.$new();
           scope.emp_code=emp_code;
           
           
var modalInstance = $modal.open({
	  scope:scope,
      templateUrl : "resources/js/noduesit/noduesitmodal.html",
			controller :'noduesitmodaljscontroller'	
		});
}
});
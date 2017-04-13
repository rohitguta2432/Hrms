application.controller('noduesitcontroller',function($scope,$http,$modal,$rootScope,$window) {
var status=5;
var stage=1;
$scope.accepted=false;
$scope.$on("EVT_ACCEPTED",function(event,data){
	$scope.accepted = true;
	$scope.modalInstance.close();
});
	$scope.init=function(){
/*Employee grid information*/
			$http.get(domain+'/getnoduesemplist?status='+status+'&stageid='+stage)
			.success(function(data,status,headers,config){
			$scope.nodueitjs=data.emplist;
		
})

}
	$scope.EmployeeFeedback=function(empcode,department)
	{
           var emp_code=empcode;
           var department_id=department;
           var scope=$rootScope.$new();
           scope.emp_code=emp_code;
           scope.department_id=department_id;
           $scope.modalInstance = $modal.open({
	  scope:scope,
      templateUrl : "resources/js/noduesit/noduesitmodal.html",
			controller :'noduesitmodaljscontroller'	
		});
}
});
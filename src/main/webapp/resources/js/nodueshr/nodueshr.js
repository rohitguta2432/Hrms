application.controller('nodueshrcontroller',function($scope,$http,$modal,
		 $rootScope,$window) {
	var status=5;
	var stage=1;
	/*var count=0;*/
	$scope.init=function(){
/*Employee grid information*/
			$http.get(domain+'/getnoduesemplist?status='+status+'&stageid='+stage)
			.success(function(data,status,headers,config){
			$scope.nodueshrtjs=data.emplist;
			/*alert($scope.nodueshrtjs);*/
})		/*error(function(data,status,headers,config){
			alert('not found');
				})*/
}
	$scope.EmployeeFeedback=function(empcode,department)
	{
           var emp_code=empcode;
           var department_id=department;
        var scope = $rootScope.$new();
		scope.emp_code = emp_code;
		scope.department_id=department_id;
	    var modalInstance = $modal.open({
			scope:scope,
      		templateUrl : "resources/js/nodueshr/nodueshrmodal.html",
      		controller :'nodueshrmodalcontroller'
		});
	}
	$scope.EmpOthernoDues=function(empcode)
	{
		var emp_code=empcode;
        var scope = $rootScope.$new();
		scope.emp_code = emp_code;
		scope.params={employeecode:emp_code}
		var modalInstance = $modal.open({
			scope:scope,
	  		templateUrl : "resources/js/nodueshr/othernodues.html",
	  		controller :'nodueshrcontroller'
		})
		}
$scope.nodueStatus=function(){
	$scope.emp_code=$scope.params.employeecode;
	$http.get(domain+'/noduesstatus?employeecode='+$scope.emp_code)
	.success(function(data,status,headers,config){
	 $scope.noduestatus=data.noDuesPendingDept;
	for(var i=0;i<$scope.noduestatus.length;i++){
		if($scope.noduestatus[i]=='rm' && $scope.noduestatus[i]=='Infra' && $scope.noduestatus[i]=='it' && $scope.noduestatus[i]=='Account'){
	     $scope.accept=false;
	     count++;
	} /* alert($scope.noduestatus[i])*/
		}
	/*  alert($scope.noduestatus[i])*/
	})
.error(function(data,status,headers,config){
	})
	
	}
	
});
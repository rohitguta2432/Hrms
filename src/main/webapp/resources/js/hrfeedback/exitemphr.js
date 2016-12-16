application.controller('hrfeedbackcontroller',function($scope,$http,$modal,$window) {
	/*alert("hii");*/
	
	$scope.init=function(){
/*Employee grid information*/
			$http.get(domain+'/hrfeedbacklist')
			.success(function(data,status,headers,config){
			$scope.hrfeedback=data.feedbacklist;
			/*alert($scope.hrfeedback);*/
})
		
			/*error(function(data,status,headers,config){
			alert('not found');
				})*/
}
	$scope.EmployeeFeedback=function(empcode)
	{
           var emp_code=empcode;
    $window.sessionStorage.setItem("Mydata",emp_code);
 
    /*$rootScope.$broadcast("Mydata",emp_code);
		alert("mephr "+emp_code);
		*/
var modalInstance = $modal.open({
      templateUrl : "resources/js/hrfeedback/exithrmodal.html",
			controller :'feedbackmodalcontroller'	
		});
}
});
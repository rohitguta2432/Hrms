var reset_app=angular.module('resetPassword',[]);
var domain='/hrms'
reset_app.controller('resetController',['$rootScope','$scope','$http','$window',function($rootScope, $scope,$http,$window){
	$scope.submit=function(){
		if($scope.password!=$scope.passwordverify){
			alert('Both the provided provided doesnt match');
		}else if($scope.password==''){
			alert('Password specified is blank');
		}else if($scope.passwordverify==''){
			alert('Please confirm your password');
		}else{
			$http.get(domain+'/passwordChange',{
				params : {
					uuid : $scope.uniqueID,
					changedPassword : $scope.password
				}
			}).then(function(data,status,headers,config){
				alert('Password successfully changed,login again to the application');
				$window.location.href = domain+"/exEmployeeLogin";
			},function(data,status,headers,config){
				alert('Error in updating the password');
			});
		}
	}
}]);
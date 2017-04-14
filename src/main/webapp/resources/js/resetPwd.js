var reset_app=angular.module('resetPassword',[]);
var domain='/hrms'
reset_app.controller('resetController',['$scope','$http',function($scope,$http){
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
			}).success(function(data,status,headers,config){
				alert('Password successfully changed,login again to the application');
			}).error(function(data,status,headers,config){
				alert('Error in updating the password');
			})
		}
	}
}]);
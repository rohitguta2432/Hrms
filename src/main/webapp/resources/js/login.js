var loginApp=angular.module('login',[]);
var domain='/hrms';
loginApp.controller('loginController',function($scope,$http){
	$scope.resetPassword=function(){
		$http.get(domain+'/resetPassword',{
			params:{
				email:$scope.personal_email
			}
		})
		.success(function(data,status,headers,config){
			alert(data.messageValue);
		}).error(function(data,status,headers,config){
			
		})
	};
});
/*application.controller('loginController',function($scope,$http){
	$scope.loginCheck=function(loginform){
		$scope.sendPostData=function(){
			var data=$.param({
				userName : $scope.username,
				passWord : $scope.password
			});
			
			var config={
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8;'
					}
			}
		}
		$http.post(domain+'/authenticateLogin',data,config)
	}
});*/
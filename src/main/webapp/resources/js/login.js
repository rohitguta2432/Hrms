application.controller('loginController',function($scope,$http){
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
});
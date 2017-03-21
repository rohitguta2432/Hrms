application.controller('hrApprovalController',['$scope','$http','$rootScope','$modal','$location',function($scope,$http,$rootScope,$modal,$location){
	$scope.hr_approvalInit=function(){
		$http.get(domain+'/hrapproval')
		.success(function(data,status,headers,config){
			$scope.employeeInfo=data.empinfo;
		})
		.error(function(data,status,headers,config){
			alert('error');
		})
	};
	//hrapprovalInit;getHrApprovalFromService
	$scope.openHrAction=function(name,empcode,leaving_reason,remarks
			,noticetime,resignDate,rm_empcode,rm_email){
		var scope=$rootScope.$new();
		scope.params={empname:name,emp_code:empcode,leaveReason:leaving_reason,
				comments:remarks,notice:noticetime,resDate:resignDate,
				rm_ecode:rm_empcode,rmEmail:rm_email};
		var modalInstance=$modal.open({
			scope:scope,
			templateUrl:'resources/js/hrapproval/hrApprovalModal.html',
			controller : 'hrApprovalController',
		});
		
	}
	$scope.hrApprovalActionInit=function(){
		$scope.emp_name=$scope.params.empname;
		$scope.empcode=$scope.params.emp_code;
		$scope.resigndate=$scope.params.resDate;
		$scope.noticeperiod=$scope.params.notice;
		$scope.rmempcode=$scope.params.rm_ecode;
		$scope.rmemail=$scope.params.rmEmail;
		$scope.date1='';
	}
	
	$scope.hrApprovalAction=function(hrform){
		
		var data='empname='+$scope.emp_name +'&empCode=' + $scope.empcode
		+ '&resignationDate'+$scope.resigndate + '&noticePeriod='+$scope.noticeperiod+
		'&rmempcode'+$scope.rmempcode+'&rmemail='+$scope.rmemail+'&hrlwd='+$scope.date1
		+'&hrcomments='+$scope.comments;
		$http.get(domain+'/submitHrApproval?'+data)
		.success(function(data,status,config,headers){
			$scope.finalstatus=data.status;
			if($scope.finalstatus=='successful'){
				alert('Last working day successfully submitted');
			}
			location.reload();
			//alert($scope.finalstatus);
		})
		.error(function(data,status,headers,config){
			alert('Error');
		});
	}
}]);

application.controller('hrApprovalController',['$scope','$http','$rootScope','$modal',function($scope,$http,$rootScope,$modal){
	$scope.hr_approvalInit=function(){
		$http.get(domain+'/hrapprovalInit')
		.success(function(data,status,headers,config){
			alert('in hr initialisation');
			$scope.employeeInfo=data.empinfo;
			alert($scope.employeeInfo);
		})
		.error(function(data,status,headers,config){
			alert('error');
		})
	};
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
		$http.get(domain+'/getHrNoticeTime')
		.success(function(data,status,headers,config){
			$scope.noticePeriod=data.noticeList;
		})
		.error(function(data,status,headers,config){
			alert('Error');
		});
	}
	
	$scope.hrApprovalAction=function(hrform){
		var data='firstname='+$scope.firstname + '&lastname=' + $scope.resigndate + '&empCode=' + $scope.empcode
		+ '&resignationDate'+$scope.resigndate + '&noticePeriod='+noticeperiod+'&rmfirstname='+$scope.rm_fname
		+'&rmlastname='+$scope.rm_lname+'&rmempcode'+$scope.rmempcode+'&rmstatus='+$scope.rmstatus+
		'&rmnotice='+$scope.rmnotice+'&hrnotice'+noticeTime;
		$http.get(domain+'/submitHrApproval?'+data)
		.success(function(data,status,config,headers){
			alert('successfuly inserted')
		})
		.error(function(data,status,headers,config){
			alert('Error');
		});
	}
}]);
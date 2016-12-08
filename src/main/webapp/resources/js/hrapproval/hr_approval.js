application.controller('hrApprovalController',['$scope','$http','$rootScope','$modal',function($scope,$http,$rootScope,$modal){
	$scope.hr_approvalInit=function(){
		$http.get(domain+'/hrapprovalInit')
		.success(function(data,status,headers,config){
			$scope.employeeInfo=data.empinfo;
		})
		.error(function(data,status,headers,config){
			alert('error');
		})
	};
	$scope.openHrAction=function(firstname,lastname,empcode,leaving_reason,remarks
			,noticetime,resignDate,relDate,rm_fname,rm_lname,rm_empcode,rm_email,status,noticeByRm){
		var scope=$rootScope.$new();
		scope.params={fname:firstname,lname:lastname,emp_code:empcode,leaveReason:leaving_reason,
				comments:remarks,notice:noticetime,resDate:resignDate,releivingDate:relDate,
				rmfname:rm_fname,rmlname:rm_lname,rm_ecode:rm_empcode,rmEmail:rm_email,rmStatus:status,rmNotice:noticeByRm};
		var modalInstance=$modal.open({
			scope:scope,
			templateUrl:'resources/js/hrApprovalModal.html',
			controller : 'hrApprovalController',
		});
		
	}
	$scope.hrApprovalActionInit=function(){
		$scope.firstname=$scope.params.fname;
		$scope.lastname=$scope.params.lname;
		$scope.empcode=$scope.params.emp_code;
		$scope.resigndate=$scope.params.resDate;
		$scope.noticeperiod=$scope.params.notice;
		$scope.rm_fname=$scope.params.rmfname;
		$scope.rm_lname=$scope.params.rmlname;
		$scope.rmempcode=$scope.params.rm_ecode;
		$scope.rmstatus=$scope.params.rmStatus;
		$scope.rmnotice=$scope.params.rmNotice;
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
application.controller('trackingController', function($scope,$http,$modal,$rootScope) {
	$scope.trackingInit = function() {
		$http.get(domain + '/trackingStatusInit').success(
				function(data, status, headers, config) {
					$scope.res_user_id = data.resID;
					$scope.res_user_status_id=data.resStatusId;
					$scope.res_user_reason = data.resreason;
					$scope.res_user_status = data.resStatus;
					$scope.res_user_empcode = data.resEmpcode;
					$scope.res_user_remarks = data.resRemarks;
					$scope.res_user_resignation_date = data.resDate;
					$scope.res_user_relieving_date = data.resRelievingDate;
					$scope.res_user_rm_empcode = data.resRmEmpcode;
					$scope.res_user_hr_empcode = data.resHrEmpcode;
					$scope.res_user_rm_approval_date = data.resRmApprovalDate;
					$scope.res_user_hr_approval_date = data.resHrApprovalDate;
					$scope.res_user_lwd_date = data.resLwdDate;
				}).error(function(data, status, headers, config) {
			alert('Error');
		});
	}
	$scope.openResignationStatus=function(resID,resStatusId,resReason,resStatus
			,resEmpCode,resRemarks,resDate,resRelDate,resRmEmpCode,resHrEmpcode
			,resRmApprovalDate,resHrApprovalDate,resLwdDate){
		
		var scope=$rootScope.$new();
		scope.params={resignationID:resID,resignationStatusId:resStatusId,resignationReason:resReason,resignationStatus:resStatus,
				resignationEmpcode:resEmpCode,resignationRemarks:resRemarks,resignationDate:resDate,
				resignationRelievingDate:resRelDate,resignationRmCode:resRmEmpCode,resignationHrCode:resHrEmpcode,
				resignationApprovalDate:resRmApprovalDate,resignationHrApprovalDate:resHrApprovalDate,
				resignationLwdDate:resLwdDate};
		var modalInstance=$modal.open({
			scope:scope,
			templateUrl:'resources/js/tracking/trackingModal.html',
			controller:'trackingController',
		})
	}
	$scope.trackingModalInit=function(){
		$scope.resid=$scope.params.resignationID;
		$scope.resStatusid=$scope.params.resignationStatusId;
		$scope.resreason=$scope.params.resignationReason;
		$scope.resstatus=$scope.params.resignationStatus;
		$scope.resempcode=$scope.params.resignationEmpcode;
		$scope.resremarks=$scope.params.resignationRemarks;
		$scope.resdate=$scope.params.resignationDate;
		$scope.resrelievingdate=$scope.params.resignationRelievingDate;
		$scope.resrmcode=$scope.params.resignationRmCode;
		$scope.reshrcode=$scope.params.resignationHrCode;
		$scope.resapprovaldate=$scope.params.resignationApprovalDate;
		$scope.reshrapprovaldate=$scope.params.resignationHrApprovalDate;
		$scope.reslwddate=$scope.params.resignationLwdDate;
	}
	$scope.noduesstatusinit=function(resignationID){
		var data='resignationID='+resignationID;
		$http.get(domain + '/getNoDuesStatuses'+data)
		.success(function(data, status, headers, config){
			$scope.pendingNoDuesDeptList=data.noDuesPendingDept;
			//alert('Success');
		}).error(function(data, status, headers, config){
			alert('Error');
		})
	}
	$scope.getDocUploadedStatus=function(resID){
		var data='resignationID='+resID
		$http.get(domain + '/getDocUploadedStatuses?'+data)
		.success(function(data, status, headers, config){
			$scope.notUploadedDocs=data.docList;
		}).error(function(data, status, headers, config){
			alert('Error');
		})
	}
});
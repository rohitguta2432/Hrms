
application.controller('documentManagement', ['$scope', '$http','$modal','FileProductUploadService','$location','$rootScope', function ($scope, $http,$modal,FileProductUploadService,$location,$rootScope) {

	$scope.Message = '';
	$scope.FileInvalidMessage = '';
	$scope.SelectedFileForUpload = null;
	$scope.FileDescription = '';
	$scope.IsFormSubmitted = false;
	$scope.IsFileValid = false;
	$scope.IsFormValid = false;
	$scope.selectedProduct = {};
	$scope.uploadId="";
	$scope.$watch("f1.$valid", function (isValid) {
		$scope.IsFormValid = isValid;

	});
	$scope.checkFileValid = function (file) {
		var isValid = true;
		$scope.IsFileValid = isValid;
	};

	$scope.selectedFileforUpload = function (file) {
		$scope.SelectedFileForUpload = file[0];
	};

	$scope.SaveFile = function (ev,item) {

		$scope.uploadId=item;
		$scope.empCode=$scope.params.empcode;
		$scope.ResignId=$scope.params.resignId;
	
		
		//	var uploadId=document.getElementById ('uploadId')).value;
		//	alert("file uploaded Id >>>>>"+uploadId);

		$scope.IsFormSubmitted = true;
		$scope.Message = '';

		$scope.checkFileValid($scope.SelectedFileForUpload);
	/*	alert($scope.SelectedFileForUpload);
		alert($scope.IsFormValid);
		alert($scope.IsFileValid);*/

		if ($scope.IsFileValid) {
			FileProductUploadService.UploadFile($scope.SelectedFileForUpload,$scope.uploadId,$scope.empCode,$scope.ResignId).then(function (d) {

				/*    var confirm = $mdDialog.confirm()
                    // .title('Would you like to delete your debt?')
                    .textContent(d.data.Message)
                    .ariaLabel('Lucky day')
                    .targetEvent(ev)
                    .ok('OK')
                // .cancel('Cancel');
                $mdDialog.show(confirm).then(function() {
                    $scope.status = 'You decided to get rid of your debt.';
                    $scope.rejectReport = d.data.rejectedRecord
                    window.setTimeout(function(){
                        $scope.exportToExcel();
                        //location.reload();

                    }, 2000)
                }, function() {
                    $scope.status = 'You decided to keep your debt.';
                });*/
				ClearForm();

			}, function (err) {

				/*
                var confirm = $mdDialog.confirm()
                    // .title('Would you like to delete your debt?')
                    .textContent(err)
                    .ariaLabel('Lucky day')
                    .targetEvent(ev)
                    .ok('OK')
                // .cancel('Cancel');
                $mdDialog.show(confirm).then(function() {
                    $scope.status = 'You decided to get rid of your debt.';
                }, function() {
                    $scope.status = 'You decided to keep your debt.';
                });*/

			});
		}
		else
		{
			$scope.Message = 'all the fields are required';
		}
	};

	function ClearForm() {
		$scope.FileDescription = '';
		angular.forEach(angular.element("input[type='file']"), function (inputElem) {
			angular.element(inputElem).val(null);
		});

		$scope.IsFormSubmitted = false;
		$scope.description = '';
		$scope.SelectedFileForUpload = null;

	}

	$scope.files = [];

	$scope.$on("fileSelected", function (event, args) {
		$scope.$apply(function () {
			//add the file object to the scope's files collection
			$scope.files.push(args.file);
		});
	});


	/*    $scope.exportToExcel=function(){// ex: '#my-table'
        var blob = new Blob([document.getElementById('exportable').innerHTML], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
        });
        saveAs(blob, "Report.xls");
    }*/

	$scope.getUploadEmpList=function(){
		/*alert(' geting Upload Items   ');*/

		$http.get(domain+'/getEmpUploadList')
		.success(function(data,status,headers,config){
			//alert('the data returned is : '+JSON.stringify({data : data}));
			$scope.uploadEmpList=data.empinfo;

		})
		/*.error(function(data,status,headers,config){
			alert('the error returned is : '+JSON.stringify({data : data}));
		})*/
	}



	$scope.getItems=function(){
		alert(' geting Upload Items   ');

		$http.get(domain+'/getUploadItems')
		.success(function(data,status,headers,config){
			//alert('the data returned is : '+JSON.stringify({data : data}));
			$scope.uploadItems=data;

		/*	alert("List  "+$scope.uploadItems);*/
		})
	/*	.error(function(data,status,headers,config){
			alert('the error returned is : '+JSON.stringify({data : data}));
		})*/
	}



	$scope.actionUpload=function(empcode,resignId){
		
		alert(" action Upload   "+empcode);
		var scope=$rootScope.$new();
		scope.params={empcode:empcode,resignId:resignId};

		var modalInstance = $modal.open({
			scope:scope,
			templateUrl : 'resources/js/document/upload.html',
			controller 	: 'documentManagement',
			resolve:{
				name: function(){
					return name;
				}
			}
		});
	}



}]).factory('FileProductUploadService', function ($http, $q) {
	// alert("factory of agent");
	var fac = {};

	fac.UploadFile = function (file,uploadId,empCode,resignId) {
		alert("file UploadFile Id >>>>>"+uploadId);

		var formData = new FormData();
		formData.append("file", file);
		formData.append("uploadId", uploadId);
		formData.append("empCode", empCode);
		formData.append("resignId", resignId);

		var defer = $q.defer();
		$http.post(domain+"/upload", formData, {
			withCredentials: true,
			headers: { "Content-Type": undefined },
			transformRequest: angular.identity
		}).then(
				function (d) {
					defer.resolve(d);
				},function (err) {
					defer.reject("File Upload Failed");
				});
		return defer.promise;
	}

	return fac;
});
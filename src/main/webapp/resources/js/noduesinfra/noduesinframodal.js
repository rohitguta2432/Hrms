application.controller('noduesinframodalcontroller',function($scope,$http,$window,$location) {

	$http.get(domain+'/getemployeemodalinfo?employee_code='+$scope.emp_code)
			.success(function(data, status, headers, config){
			/*alert('data are found');*/
				$scope.emplycode=data.empcode;
				$scope.empfirstname=data.empname;
			    $scope.empdepartment=data.spokecode;
				$scope.empdesignation=data.designation;
				$scope.emplocation=data.location;
				
				$http.get(domain+'/getassets?employee_code='+$scope.emplycode+'&department='+$scope.department_id)
				.success(function(data,status,headers,config){
					$scope.nodueinfraassets=data.assets;
					if(data.hasOwnProperty("error")){
						 $scope.error = data.error;
					 }
				})
				
				})
				
	 $scope.selectedItems=[];
	 $scope.selectedItemsbarcode=[];
	 $scope.itemnotselected=[];
     $scope.rejected_final_status=[3];
     $scope.accepted_status=[2];
	
	 $scope.submit=function(form){
		 angular.forEach($scope.nodueinfraassets,function(emp){
	            if(emp.selected){
	          $scope.selectedItems.push(emp.name);
	            	$scope.selectedItemsbarcode.push(emp.barcodeno);
	            }
	            })
	        /* alert($scope.selectedItems)
	          alert($scope.selectedItemsbarcode)*/
var emp_data='emp_assets='+$scope.selectedItems+'&comments='+$scope.empcomments+'&emp_code='+$scope.emp_code
+'&departmentId='+$scope.department_id+'&emp_barcode='+$scope.selectedItemsbarcode;
		 /*alert("accept "+emp_data)*/
		 $http({
		        method: 'POST',
		        url: domain+'/insertinfraassets',
		        data:emp_data,
		        headers:
		        {
		        	'Content-Type':'application/x-www-form-urlencoded'
		        }
		    }).success(function(data){
		       alert("Infra assets has been Received");
		       location.reload();
		    }).error(function(){
		        /*alert("errors")*/
		    })
	}
	 
$scope.reject=function()
		{
	angular.forEach($scope.nodueinfraassets,function(emp){
	        if(emp.selected){
	            $scope.selectedItems.push(emp.name);
	       }
	          })
	               angular.forEach($scope.nodueinfraassets,function(emp){
	          if(!emp.selected)
	          	{
	          	$scope.itemnotselected.push(emp.name)
	          	}
	          	})
	          var emp_data='comments='+$scope.empcomments+'&emp_code='+$scope.emplycode+'&not_received='+$scope.itemnotselected+'&received_assets='+$scope.selectedItems+'&final_status='+$scope.rejected_final_status
	      +'&departmentId='+$scope.department_id;
	    	$http.get(domain+'/rejectempassets?'+emp_data)
		 	 .success(function(data){
		       alert("Infra assets has been Rejected");
		      location.reload();		
		 })
		}
	});
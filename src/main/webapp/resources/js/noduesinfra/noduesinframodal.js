application.controller('noduesinframodalcontroller',function($scope,$http,$window,$location) {
	var departmentid;
	$http.get(domain+'/getemployeemodalinfo?employee_code='+$scope.emp_code)
			.success(function(data, status, headers, config){
			/*alert('data are found');*/
				$scope.emplycode=data.empcode;
				$scope.empfirstname=data.empname;
			    $scope.empdepartment=data.department;
				$scope.empdesignation=data.designation;
				$scope.emplocation=data.location;
				
				$http.get(domain+'/getinframodalassets?employee_code='+$scope.emplycode)
				.success(function(data,status,headers,config){
					/*alert('the data returned is : '+JSON.stringify({data : data}));*/
					$scope.nodueinfraassets=data.infraassets;
					departmentid=$scope.nodueinfraassets[0].departmentId
			alert(departmentid)
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
+'&departmentId='+departmentid+'&emp_barcode='+$scope.selectedItemsbarcode;
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
		       alert("submitted infra assets")
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
	         /* alert("selected  item "+$scope.selectedItems)*/
	          angular.forEach($scope.nodueinfraassets,function(emp){
	          if(!emp.selected)
	          	{
	          	$scope.itemnotselected.push(emp.name)
	          	}
	          	})
	      	/*alert("not received  item "+$scope.itemnotselected)*/
	      var emp_data='comments='+$scope.empcomments+'&emp_code='+$scope.emplycode+'&not_received='+$scope.itemnotselected+'&received_assets='+$scope.selectedItems+'&final_status='+$scope.rejected_final_status
	      +'&departmentId='+departmentid;
	      		alert(emp_data)
	      		$http.get(domain+'/rejectempassets?'+emp_data)
		 
		 .success(function(data){
		       alert("rejected infra assets")
		       location.reload();		
		 })
		}
	});
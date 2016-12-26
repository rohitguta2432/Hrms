application.controller('nodueshrmodalcontroller',function($scope,$http,$window,$modal,$location) {
$http.get(domain+'/getemployeemodalinfo?employee_code='+$scope.emp_code)
			.success(function(data, status, headers, config){
			/*alert('data are found');*/
				
				$scope.emplycode=data.empcode;
				$scope.empfirstname=data.firstname;
				$scope.emplastname=data.lastname;
				$scope.empdepartment=data.department;
				$scope.empdesignation=data.designation;
				$scope.emplocation=data.location;
				
				
				$http.get(domain+'/getNoDuesAssets')
				.success(function(data,status,headers,config){
					/*alert('the data returned is : '+JSON.stringify({data : data}));*/
					$scope.nodueshrassets=data.list;
					/*alert($scope.nodueshrassets)*/
					})
				})
			   $scope.selectedItems = [];
	           $scope.itemnotselected=[];
               $scope.rejected_final_status=[3];
               $scope.accepted_status=[2];
	
	$scope.submit=function(form){
		angular.forEach($scope.nodueshrassets,function(emp){
		if (emp.selected) $scope.selectedItems.push(emp.name)
			
		})
	var emp_data='emp_assets='+$scope.selectedItems+'&hr_comments='+$scope.empcomments+'&emp_code='+$scope.emplycode;
		/*alert("data is"+emp_data)*/

	$http({
        method: 'POST',
        url: domain+'/inserthrassets',
        data:emp_data,
        headers:
        {
        	'Content-Type':'application/x-www-form-urlencoded'
        }
    }).success(function(data){
       alert("submitted hr asserts")
       location.reload();
    }).error(function(){
       /* alert("errors")*/
    })
				}		
$scope.reject=function()
	{
		angular.forEach($scope.nodueshrassets,function(emp){
	        if(emp.selected){
	      $scope.selectedItems.push(emp.name);
	 }
	    })
	    /*alert("selected  item "+$scope.selectedItems)*/
	    angular.forEach($scope.nodueshrassets,function(emp){
	    if(!emp.selected)
	    	{
	    	$scope.itemnotselected.push(emp.name)
	    	}
	    	})
		/*alert("not received  item "+$scope.itemnotselected)*/
		
var emp_data='comments='+$scope.empcomments+'&emp_code='+$scope.emplycode+'&not_received='+$scope.itemnotselected+'&received_assets='+$scope.selectedItems+'&final_status='+$scope.rejected_final_status;
/*alert(emp_data)*/
$http.get(domain+'/rejectempassets?'+emp_data)
.success(function(data){
       alert("rejected hr asserts")
       location.reload();
				
	})
	}
});
'use strict';

appControllers.controller('MainController', ['$rootScope', '$scope', '$http', 'authorization',
    function($rootScope, $scope, $http, authorization) {
        $scope.status = 'running...';
        $scope.profile = authorization.profile;
        $scope.isAdmin = authorization.hasRealmRole('admin');
        $scope.isManager = authorization.hasRealmRole('manager')
        
        /*$scope.getContracts = function() {
        	$http.get("http://localhost:8090/api/contracts").success(function(data) {
            	$scope.contracts = data;
            });
        }*/
        
        $scope.getEmployees = function() {
        	$http.get("http://localhost:8090/api/employees").success(function(data) {
        		
            	$scope.employees = data;
            });
        };
        $scope.deleteEmployee = function(id) {
        	$scope.emp.id = id;
        	$('#confirm').modal('show');
        };
        $scope.updateEmployee = function(id) {
        	$scope.emp.id = id;
        	
        	$http.get("http://localhost:8090/api/employee/get/"+id).success(function(data) {
        		console.log(data);
            	$scope.emp = data;
            	if(!$scope.showDiv)
            		$scope.showDiv = !$scope.showDiv;
            	
            });
        };
        $scope.deleteEmp = function() {
        	var id = $scope.emp.id;
        	$http.get("http://localhost:8090/api/employee/delete/"+id).success(function(data) {
        		$('#successMsg').find('span').html(data.message);
                $('#successMsg').show();
                setTimeout(function() { $('#successMsg').hide() }, 3000);
                $scope.getEmployees();
                $('#confirm').modal('hide');
                $scope.emp = {};
            });
        };
        $scope.updateEmpRecord = function(emp) {
        	var config = {
		         headers : {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
		      };
        	
        	var data = $.param({firstName: emp.firstName, lastName: emp.lastName, day: emp.day, id: emp.id});
        	$http.post("http://localhost:8090/api/employee/save", data, config)
            .then(function(response) {
            	$('#successMsg').find('span').html(response.data.message);
                 $('#successMsg').show();
                 setTimeout(function() { $('#successMsg').hide() }, 3000);
                 $scope.getEmployees();
                 if(!$scope.showDiv)
                	 $scope.showDiv = !$scope.showDiv;
                 $scope.emp = {};
            });
        };
        $scope.getEmployees();
        
        $scope.logout = function() {
        	authorization.logout();
        };
    }
]);
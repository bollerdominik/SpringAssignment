import { Component,ViewChild } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import {ROUTER_DIRECTIVES, Router} from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import 'rxjs/add/operator/map';
import { CommonModule } from '@angular/common';
import 'rxjs/Rx';

import { KeycloakService } from './keycloak.service';

@Component({
  selector: 'my-app',
  directives: [ROUTER_DIRECTIVES,CommonModule,FormsModule],
  templateUrl: 'app/employee.html'
})
export class AppComponent implements OnInit {

 @ViewChild('select') selectElRef;
 
  employees: any[] = [];
  shifts: any[] = [];
  firstName = 'Demo';
  showDiv = true;
  emp = {};
  token = '';
  selectedValues = []; 

  constructor(private http: Http, private kc: KeycloakService) {}

	ngOnInit() {this.reloadData();this.loadProfile();}
	
  //Handle Logout 
  logout() {
    this.kc.logout();
  }
  
  //Load User Profile
  loadProfile() {
  	this.kc.getUserProfile().then(profile => {
  		this.firstName = profile.firstName;
    });  
  }
  
  //Reload Employees data
  reloadData() {
    //angular dont have http interceptor yet

    this.kc.getToken()
      .then(token => {
      	this.token = token;
        let headers = new Headers({
          'Accept': 'application/json',
          'Authorization': 'Bearer ' + token
        });

        let options = new RequestOptions({ headers });

        this.http.get("http://localhost:8090/api/employees", options)
          .map(res => res.json())
          .subscribe(employees => this.employees = employees,
            error => console.log(error));
        this.http.get("http://localhost:8090/api/shifts", options)
          .map(res => res.json())
          .subscribe(shifts => this.shifts = shifts,
            error => console.log(error));
      })
      .catch(error => console.log(error));
  }
  
  	change(options) {
	    this.selectedValues = Array.apply(null,options)
	      .filter(option => option.selected)
	      .map(option => option.value)
	  }
	  
	  updateSelectList() {
		    let options = $('#shiftIds').find('option');
		    for(let i=0; i < options.length; i++) {
		      options[i].selected = this.selectedValues.indexOf(options[i].value) > -1;
		    }
		  }
  
  	//Add/Update Emp record
  	updateEmpRecord(emp) {
		
		let headers = new Headers({
          'Accept': 'application/json',
          'Authorization': 'Bearer ' + this.token,
          'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        });

        let options = new RequestOptions({ headers });
        
        if(emp.firstName != undefined && emp.firstName.trim() != '' && emp.lastName != undefined && 
		    emp.lastName.trim() != '' && emp.day != undefined && emp.day.trim() != '' && 
		    emp.shiftIds != undefined && emp.shiftIds != '') {
		    
			var data = $.param({firstName: emp.firstName, lastName: emp.lastName, day: emp.day, shiftIds: emp.shiftIds, id: emp.id});
	
			this.http.post("http://localhost:8090/api/employee/save", data, options)
				.map(res => res.json())
	          	.subscribe(data => {
	          		$('#successMsg').find('span').html(data.message);
			        $('#successMsg').show();
			       	setTimeout(function() { $('#successMsg').hide() }, 3000);
			        this.reloadData();
			        if(!this.showDiv)
			        	this.showDiv = !this.showDiv;
			        this.emp = {};
	          	},
	            error => console.log(error));
	    } else {
	    	$('#errorDiv').show();
	    	setTimeout(function() { $('#errorDiv').hide(); }, 5000);
	    } 
	};
	
	//open Emp form and set data
	updateEmployee(id) {
    	this.emp.id = id;
    	
    	let headers = new Headers({
          'Accept': 'application/json',
          'Authorization': 'Bearer ' + this.token
        });

        let options = new RequestOptions({ headers });
    	
    	this.http.get("http://localhost:8090/api/employee/get/"+id, options)
    		.map(res => res.json())
          	.subscribe(data => {
          		console.log(data);
	        	this.emp = data;
	        	console.log(data.shifts);
	        	
	        	this.emp.shiftIds = [];
	        	this.selectedValues= [];
	        	if(data.shifts != null && data.shifts.length > 0) {
	        		for(let i=0; i < data.shifts.length; i++) {
	        			let shift = data.shifts[i].shift;
	        			
	        			this.selectedValues.push(data.shifts[i].id);
	        			this.emp.shiftIds.push(data.shifts[i].id);
	        			$('#shiftIds').find('option:contains("'+shift+'")').attr('selected', 'selected');
	        		}
	        	}
	        	
	        	this.updateSelectList();
	        	
	        	if(this.showDiv)
	        		this.showDiv = !this.showDiv;
          	},
            error => console.log(error));
        
    };
    
    isSelectedShift(id) {
    
    	if(this.selectedShiftIds.indexOf(id) < -1 )
    		return false;
    	else
    		return true;
    };
    
  
  	//Open confirm modal
  	deleteEmployee(id) {
    	this.emp.id = id;
    	$('#confirm').modal('show');
    };
    
    showHideForm() {
    	this.showDiv = !this.showDiv;
    	this.emp = {};
    }
    
    //Delete Emp record
    deleteEmp() {
    	var id = this.emp.id;
    	
    	let headers = new Headers({
          'Accept': 'application/json',
          'Authorization': 'Bearer ' + this.token
        });

        let options = new RequestOptions({ headers });
        
    	this.http.get("http://localhost:8090/api/employee/delete/"+id, options)
    		.map(res => res.json())
          	.subscribe(data => {
	    		$('#successMsg').find('span').html(data.message);
	            $('#successMsg').show();
	            setTimeout(function() { $('#successMsg').hide() }, 3000);
	            this.reloadData();
	            $('#confirm').modal('hide');
	            this.emp = {};
        	},
            error => console.log(error));
    };

  //Handle Error
  private handleError(error: Response) {
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
  }
}
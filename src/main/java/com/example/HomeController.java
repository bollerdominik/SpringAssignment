package com.example;

/**
 * Created by dubsta on 22.02.2017.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.domain.Employee;
import com.example.domain.Shift;
import com.example.domain.respository.EmployeeRepository;
import com.example.domain.respository.ShiftRepository;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {

    private final EmployeeRepository repositoryEmp;
    private final ShiftRepository repositoryShift;

    @Autowired
    public HomeController(EmployeeRepository repositoryEmp, ShiftRepository repositoryShift) {
        this.repositoryEmp = repositoryEmp;
        this.repositoryShift = repositoryShift;
    }

    // Add a shift to an employee
    @RequestMapping(value = "/api/employees/{eID}/shifts/{sID}", method = RequestMethod.PUT)
    public @ResponseBody
    Shift addShiftToEmployee(@PathVariable("eID") long eid, @PathVariable("sID") long sid) {
        Shift shift = repositoryShift.findOne(sid);
        Employee emp = repositoryEmp.findOne(eid);

        if (shift == null || emp == null){
            return null;
        }

        List<Shift> listS= emp.getShifts();
        boolean alreadyExists = false;
        for (Shift s : listS){
            if (s.getId() == shift.getId()){
                alreadyExists = true;
            }
        }

        if (!alreadyExists){
            listS.add(shift);
        }

        emp.setShifts(listS);
        this.repositoryEmp.save(emp);

        return shift;
    }

    // Done this way so I can retrieve a JSON with the shift included
    @RequestMapping(value="/api/employees",method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getEmployees(KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal) {

        return (List<Employee>) repositoryEmp.findAll();

    }

    @RequestMapping(value="/api/shifts",method = RequestMethod.GET)
    @ResponseBody
    public List<Shift> getShifts(KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal) {

        return (List<Shift>) repositoryShift.findAll();
    }

    @RequestMapping(value="/api/employee/save",method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, Object> handleEmployee(@RequestParam("shiftIds[]") List<String> to, Employee emp, HttpServletRequest req) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        if(emp.getId() != null){
            map.put("message", "Updated employee successfully");
        }
        else{
            map.put("message", "Added employee successfully");
        }

        System.out.println(emp.getFirstName());
        System.out.println(req.getParameter("firstName"));

        if(to != null) {
            List<Shift> records = new ArrayList<Shift>();
            for(int i=0;i < to.size(); i++) {
                Shift shift = repositoryShift.findOne(Long.parseLong(to.get(i)));
                records.add(shift);
            }
            emp.setShifts(records);
        }

        emp = repositoryEmp.save(emp);

        map.put("emp", emp);
        return map;
    }
}


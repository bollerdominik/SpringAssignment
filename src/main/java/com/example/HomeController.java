package com.example;

/**
 * Created by dubsta on 22.02.2017.
 */

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


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

    @RequestMapping(value="/api/employees",method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getEmployees(KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal) {

        AccessToken token = principal.getKeycloakSecurityContext().getToken();

        String id = token.getId();
        String firstName = token.getGivenName();
        String lastName = token.getFamilyName();
        return (List<Employee>) repositoryEmp.findAll();
    }

    @RequestMapping(value="/api/employee/save",method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, Object> handleEmployee(Employee emp, HttpServletRequest req) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        if(emp.getId() != null)
            map.put("message", "Updated employee successfully");
        else
            map.put("message", "Added employee successfully");
        System.out.println(emp.getFirstName());
        System.out.println(req.getParameter("firstName"));

        emp = repositoryEmp.save(emp);

        map.put("emp", emp);
        return map;
    }

    @RequestMapping(value="/api/employee/get/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Employee handleEmployee(@PathVariable("id") long id) {

        return repositoryEmp.findOne(id);
    }

    @RequestMapping(value="/api/employee/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object deleteEmployee(@PathVariable("id") long id) {

        repositoryEmp.delete(id);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("message", "Delete employee Successfully");
        return map;
    }
}


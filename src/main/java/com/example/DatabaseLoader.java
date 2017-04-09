package com.example;

import com.example.domain.Employee;
import com.example.domain.Shift;
import com.example.domain.respository.EmployeeRepository;
import com.example.domain.respository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by dubsta on 21.02.2017.
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

    private final EmployeeRepository repositoryEmp;
    private final ShiftRepository repositoryShift;

    @Autowired
    public DatabaseLoader(EmployeeRepository repositoryEmp, ShiftRepository repositoryShift) {
        this.repositoryEmp = repositoryEmp;
        this.repositoryShift = repositoryShift;
    }

    @Override
    public void run(String... strings) throws Exception {

        Employee employeeA = new Employee("Mike","Hunt", Employee.Days.WEDNESDAY, new ArrayList<>());
        Shift nightShift = new Shift(Shift.Shifts.EVENING,Arrays.asList(employeeA));
        employeeA.setShifts(Arrays.asList(nightShift));

        this.repositoryEmp.save(employeeA);
        this.repositoryShift.save(nightShift);
    }
}

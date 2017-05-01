package com.example;

import java.util.ArrayList;
import java.util.Arrays;

import com.example.domain.Employee;
import com.example.domain.Shift;
import com.example.domain.respository.EmployeeRepository;
import com.example.domain.respository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


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
        Shift eveningShift = new Shift(Shift.Shifts.EVENING,new ArrayList<>());
        this.repositoryShift.save(eveningShift);

        employeeA.setShifts(Arrays.asList(eveningShift));

        this.repositoryEmp.save(employeeA);

        Shift morningShift = new Shift(Shift.Shifts.MORNING,new ArrayList<>());
        Shift nightShift = new Shift(Shift.Shifts.NIGHT,new ArrayList<>());
        Shift dayShift = new Shift(Shift.Shifts.DAY, new ArrayList<>());
        this.repositoryShift.save(nightShift);
        this.repositoryShift.save(eveningShift);
        this.repositoryShift.save(morningShift);
        this.repositoryShift.save(dayShift);
    }
}

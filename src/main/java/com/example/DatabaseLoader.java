package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by dubsta on 21.02.2017.
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

    private final EmployeeRepository repository;

    @Autowired
    public DatabaseLoader(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.repository.save(new Employee("Mike", "Hunt", Employee.Days.MONDAY, Employee.Shifts.EVENING));
        this.repository.save(new Employee("Tom", "Rest", Employee.Days.TUESDAY, Employee.Shifts.NIGHT));
        //this.repository.save(new Employee("Gandalf", "the Grey", "wizard"));
        //this.repository.save(new Employee("Samwise", "Gamgee", "gardener"));
        //this.repository.save(new Employee("Meriadoc", "Brandybuck", "pony rider"));
        //this.repository.save(new Employee("Peregrin", "Took", "pipe smoker"));
    }
}

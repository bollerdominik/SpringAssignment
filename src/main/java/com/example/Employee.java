package com.example;

import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by dubsta on 21.02.2017.
 */
@Data
@Entity
public class Employee {
    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    public enum Days{MONDAY,TUESDAY, WEDNESDAY, THURSDAY, FRIDAY}
    private Days day;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_shift", joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "shift_id", referencedColumnName = "id"))
    @Setter private List<Shift> shifts;

    private Employee() {}

    public Employee(String firstName, String lastName, Days day, List<Shift> shifts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.day = day;
        this.shifts = shifts;

    }
}

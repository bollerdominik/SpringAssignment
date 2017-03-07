package com.example;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by dubsta on 06.03.2017.
 */
@Data
@Entity
public class Shift {
    private @Id @GeneratedValue Long id;
    private Shifts shift;
    public enum Shifts{EVENING,NIGHT, MORNING};

    @ManyToMany(mappedBy = "shifts")
    private List<Employee> employees;

    private Shift() {}

    public Shift(Shifts shift, List<Employee> employees) {
        this.shift = shift;
        this.employees = employees;
    }
}

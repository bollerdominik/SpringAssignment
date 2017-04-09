package com.example.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

/**
 * Created by dubsta on 06.03.2017.
 */
@Data
@Entity
public class Shift implements Serializable {


    private static final long serialVersionUID = 1L;

    private @Id @GeneratedValue Long id;
    private Shifts shift;
    public enum Shifts{EVENING,NIGHT, MORNING};

    @ManyToMany(mappedBy = "shifts")
    @JsonBackReference
    private List<Employee> employees;

    private Shift() {}

    public Shift(Shifts shift, List<Employee> employees) {
        this.shift = shift;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shifts getShift() {
        return shift;
    }

    public void setShift(Shifts shift) {
        this.shift = shift;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}


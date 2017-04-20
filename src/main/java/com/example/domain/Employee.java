package com.example.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by dubsta on 21.02.2017.
 */
@Data
@Entity
public class Employee implements Serializable {


    private static final long serialVersionUID = 1L;
    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    public enum Days{MONDAY,TUESDAY, WEDNESDAY, THURSDAY, FRIDAY}
    private Days day;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_shift", joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "shift_id", referencedColumnName = "id"))
    @Setter @JsonManagedReference
    private List<Shift> shifts;

    private Employee() {}

    public Employee(String firstName, String lastName, Days day, List<Shift> shifts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.day = day;
        this.shifts = shifts;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Days getDay() {
        return day;
    }

    public void setDay(Days day) {
        this.day = day;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }
}

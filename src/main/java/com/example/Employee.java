package com.example;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
    public enum Shifts{EVENING,NIGHT}
    private Days day;

    private Employee() {}

    public Employee(String firstName, String lastName, Days day) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.day = day;
    }
}

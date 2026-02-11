package io.github.chitralabs.sheetz.examples.model;

import io.github.chitralabs.sheetz.annotation.Column;

import java.time.LocalDate;

/**
 * Employee model for multi-sheet workbook demos.
 */
public class Employee {

    @Column(value = "Name", required = true, width = 20)
    private String name;

    @Column(value = "Department", width = 15)
    private String department;

    @Column(value = "Salary")
    private Double salary;

    @Column(value = "Hire Date", format = "yyyy-MM-dd")
    private LocalDate hireDate;

    public Employee() {}

    public Employee(String name, String department, Double salary, LocalDate hireDate) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    @Override
    public String toString() {
        return String.format("Employee{name='%s', department='%s', salary=%.2f, hireDate=%s}",
                name, department, salary, hireDate);
    }
}

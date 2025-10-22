package com.syslab.ems.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employees", indexes = {
        @Index(name = "idx_emp_email", columnList = "email", unique = true),
        @Index(name = "idx_emp_name", columnList = "firstName,lastName")
})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 60)
    private String firstName;

    @NotBlank @Size(max = 60)
    private String lastName;

    @NotBlank @Email @Size(max = 120)
    private String email;

    @Size(max = 80)
    private String department;

    @Min(0)
    private int salary;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }
}

package com.example.java.spring.eureka.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "age", nullable = false)
    private Integer age;
    @Column(name = "salary", nullable = false)
    private Integer salary;

    @JsonIgnore
    @Column(name="presenceFlag", nullable = false)
    private boolean presenceFlag = true;

    public Doctor(int id, String firstName, String lastName, Integer age, Integer salary)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
    }

    public Doctor() {

    }

    //LAZY – стратегия, которая не предусматривает получение полной связи сущностей, и при первом обращении к связи будет выполнятся запрос на получение данных с БД.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentID")
    @JsonIgnore
    private Department department;
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }


    @OneToMany(cascade = CascadeType.ALL)
    private Set<Patient> patients;
    public Set<Patient> getPatients() {
        return patients;
    }
    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }


    public int getId() { return id; }
    public void setId(int id) {
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
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Integer getSalary() {
        return salary;
    }
    public void setSalary(Integer salary) {
        this.salary = salary;
    }
    public boolean getPresenceFlag() {
        return presenceFlag;
    }
    public void setPresenceFlag(boolean flag) {
        this.presenceFlag = flag;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        Doctor doc = (Doctor)obj;
        if (this.getLastName().equals(doc.getLastName()) && this.getFirstName().equals(doc.getFirstName()))
            return false;
        return (this.getSalary().equals(doc.getSalary()));
    }
}


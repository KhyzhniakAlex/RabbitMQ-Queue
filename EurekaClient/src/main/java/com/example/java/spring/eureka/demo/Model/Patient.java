package com.example.java.spring.eureka.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "age", nullable = false)
    private Integer age;
    @Column(name = "diagnosis", nullable = false)
    private String diagnosis;

    @JsonIgnore
    @Column(name="presenceFlag", nullable = false)
    private boolean presenceFlag = true;

    public Patient(int id, String firstName, String lastName, Integer age, String diagnosis) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.diagnosis = diagnosis;
    }

    public Patient() {}


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorID")
    @JsonIgnore
    private Doctor doctor;
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    public int getId() {
        return id;
    }
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
    public String getDiagnosis() {
        return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    public boolean getPresenceFlag() {
        return presenceFlag;
    }
    public void setPresenceFlag(boolean presenceFlag) {
        this.presenceFlag = presenceFlag;
    }
}

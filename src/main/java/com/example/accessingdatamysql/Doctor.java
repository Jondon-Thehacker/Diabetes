package com.example.accessingdatamysql;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;
    private String doctorName;
    private String hospital;
    private String email;

    @ManyToMany(cascade = { CascadeType.ALL })  //TODO: added mappedBy
    @JoinTable(
            name = "Assignment",
            joinColumns = { @JoinColumn(name = "patientId")},
            inverseJoinColumns = { @JoinColumn(name = "doctorId") }
    )
    private Set<Patient> patients = new HashSet<>();

    public void setDoctorName(String firstName) {
        this.doctorName = doctorName;
    }

    public void setHospital(String lastName) {
        this.hospital = hospital;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getHospital() {
        return hospital;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

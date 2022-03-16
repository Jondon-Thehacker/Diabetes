package com.example.accessingdatamysql;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="patientId")
    private Long patientId;
    private String patientName;
    @Column(unique = false)
    private String email;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //mapped by name of field
    private Set<Notes> notes = new HashSet<>();
    // @OneToMany(mappedBy = "Patient")
    // private Set<Measurement> measurements = new HashSet<>();

    @ManyToMany(mappedBy = "patients")
    private Set<Doctor> doctors = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    private Set<Measurement> measurements = new HashSet<>();

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getEmail() {
        return email;
    }
}

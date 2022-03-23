package com.example.accessingdatamysql;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    private Long doctorId;
    private String doctorName;
    private String hospital;
    private String email;

    @ManyToMany(cascade = { CascadeType.ALL })  //TODO: added mappedBy
    @JoinTable(
            name = "Assignment",
            joinColumns = { @JoinColumn(name = "doctorId")},
            inverseJoinColumns = { @JoinColumn(name = "patientId") }
    )
    private List<Patient> patients;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //mapped by name of field
    private List<Notes> notes;

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

    public List<SimplePatient> getPatients(){
        return this.patients.stream().map(p -> new SimplePatient(p.getPatientId(), p.getPatientName())).collect(Collectors.toList());
    }

    public Patient getPatientById(Long patientId){
        return this.patients.stream()
                            .filter(patient -> patient.getPatientId() == patientId)
                            .findFirst()
                            .orElse(null);
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.example.accessingdatamysql;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
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
    @JsonIgnore
    private List<Patient> patients;


    @OneToMany(mappedBy = "doctor", cascade = CascadeType.MERGE, fetch = FetchType.LAZY) //mapped by name of field
    @JsonIgnore
    private List<Notes> notes;

    @OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

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
/*
    public List<SimplePatient> getPatients(){
        return this.patients.stream().map(p -> new SimplePatient(p.getPatientId(), p.getPatientName())).collect(Collectors.toList());
    }

 */

    public List<Patient> getPatients(){
        return this.patients;
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

    public List<Notes> getNotes() {
        return notes;
    }

    public void addNote(Notes note){
        this.notes.add(note);
    }

    public void removeNote(Notes note){
        this.notes.remove(note);
    }

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return doctorId.equals(doctor.doctorId) && doctorName.equals(doctor.doctorName) && hospital.equals(doctor.hospital) && email.equals(doctor.email) && Objects.equals(patients, doctor.patients) && Objects.equals(notes, doctor.notes) && Objects.equals(user, doctor.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, doctorName, hospital, email, patients, notes, user);
    }
}

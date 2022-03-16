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
    private List<Notes> notes;
    // @OneToMany(mappedBy = "Patient")
    // private Set<Measurement> measurements = new HashSet<>();

    @ManyToMany(mappedBy = "patients")
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "patient")
    private List<Measurement> measurements;

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
        return this.patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getEmail() {
        return email;
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

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public void addMeasurement(Measurement measurement){
        this.measurements.add(measurement);
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
}

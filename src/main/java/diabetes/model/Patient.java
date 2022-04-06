package diabetes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @Column(name="patientId")
    private Long patientId;

    private String patientName;

    @Column(unique = false)
    private String email;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.MERGE, fetch = FetchType.LAZY) //mapped by name of field
    @JsonIgnore
    private List<Notes> notes;

    @ManyToMany(mappedBy = "patients")
    @JsonIgnore
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Measurement> measurements;

    public Patient(Long patientId, String patientName, String email, List<Notes> notes, List<Doctor> doctors, List<Measurement> measurements){
        this.patientId = patientId;
        this.patientName = patientName;
        this.email = email;
        this.notes = notes;
        this.doctors = doctors;
        this.measurements = measurements;
    }

    public Patient(){

    }

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

    public List<Long> getDoctors() {
        return doctors.stream().map(d -> d.getDoctorId()).collect(Collectors.toList());
    }

    public List<Measurement> getMeasurementOfTypeAndDate(String type, String startDate, String endDate){

        return measurements.stream().filter(m -> m.getMeasurementName().equals(type) && isBetween(m.getTime(), startDate, endDate)).collect(Collectors.toList());
    }

    public List<Measurement> getMeasurementOfType(String type){
        return measurements.stream().filter(m -> m.getMeasurementName().equals(type)).collect(Collectors.toList());
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return patientId.equals(patient.patientId) && patientName.equals(patient.patientName) && Objects.equals(email, patient.email) && Objects.equals(notes, patient.notes) && Objects.equals(doctors, patient.doctors) && Objects.equals(measurements, patient.measurements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, patientName, email, notes, doctors, measurements);
    }


}



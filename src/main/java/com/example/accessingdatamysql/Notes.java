package com.example.accessingdatamysql;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@IdClass(NotesId.class)
@Table(name = "notes")
public class Notes {

    @Id
    @Column(name = "date")
    private java.sql.Timestamp date;

    private String note;

    @Id
    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    @Id
    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notes notes = (Notes) o;
        return date.equals(notes.date) && note.equals(notes.note) && patient.equals(notes.patient) && doctor.equals(notes.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, note, patient, doctor);
    }

    public java.sql.Timestamp getDate() {
        return date;
    }

    public void setDate(java.sql.Timestamp date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

  /*  public SimplePatient getSimplePatient(){
        //System.out.println(patient);
        return new SimplePatient(patient.getPatientId(), patient.getPatientName());
    }

   */

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
/*
    public SimpleDoctor getSimpleDoctor() {
        return new SimpleDoctor(doctor.getDoctorId(), doctor.getDoctorName());
    }

 */

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}

package com.example.accessingdatamysql;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


public class NotesId implements Serializable {
    private java.sql.Timestamp date;
    private Patient patient;
    private Doctor doctor;

    public NotesId(java.sql.Timestamp date, Patient patient, Doctor doctor) {
        this.date = date;
        this.patient = patient;
        this.doctor = doctor;
    }

    public NotesId(){

    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotesId notesId = (NotesId) o;
        return date.equals(notesId.date) && patient.equals(notesId.patient) && doctor.equals(notesId.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, patient, doctor);
    }
}

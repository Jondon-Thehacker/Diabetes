package com.example.accessingdatamysql;

import java.io.Serializable;
import java.util.Objects;

public class NotesId implements Serializable {
    private int date;
    private Patient patient;
    private Doctor doctor;

    public NotesId(int date, Patient patient, Doctor doctor) {
        this.date = date;
        this.patient = patient;
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotesId notesId = (NotesId) o;
        return date == notesId.date && patient.equals(notesId.patient) && doctor.equals(notesId.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, patient, doctor);
    }
}

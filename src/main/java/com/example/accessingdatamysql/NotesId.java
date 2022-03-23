package com.example.accessingdatamysql;

import java.io.Serializable;
import java.util.Objects;

public class NotesId implements Serializable {
    private int date;
    private Patient patient;

    public NotesId(int date, Patient patient) {
        this.date = date;
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotesId notesId = (NotesId) o;
        return date == notesId.date && patient.equals(notesId.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, patient);
    }
}

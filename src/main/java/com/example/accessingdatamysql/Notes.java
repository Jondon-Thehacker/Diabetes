package com.example.accessingdatamysql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(NotesId.class)
@Table(name = "notes")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "date")
    private int date;

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
        return date == notes.date && Objects.equals(patient, notes.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, patient);
    }
}

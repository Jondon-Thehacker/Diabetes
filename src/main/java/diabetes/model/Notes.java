package diabetes.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

//Emil Pontoppidan Rasmussen, s204441
@Entity
@Table(name = "notes")
public class Notes implements Serializable {
    //Private key. Auto-incremented.
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long noteId;

    private String note;

    //Foreign key.
    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    //Foreign key.
    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    @Column(name = "date")
    private java.sql.Timestamp date;

    //Standard constructor, mainly for testing
    public Notes(Long noteId, String note, Patient patient, Doctor doctor, java.sql.Timestamp date){
        this.noteId = noteId;
        this.note = note;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }

    public Notes(){

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

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        noteId = noteId;
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

    //Overriding equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notes notes = (Notes) o;
        return Objects.equals(note, notes.note) && noteId.equals(notes.noteId) && patient.equals(notes.patient) && doctor.equals(notes.doctor) && date.equals(notes.date);
    }

    //Overriding equals and hashCode
    @Override
    public int hashCode() {
        return Objects.hash(note, noteId, patient, doctor, date);
    }
}

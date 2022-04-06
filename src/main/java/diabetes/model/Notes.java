package diabetes.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notes notes = (Notes) o;
        return Objects.equals(note, notes.note) && noteId.equals(notes.noteId) && patient.equals(notes.patient) && doctor.equals(notes.doctor) && date.equals(notes.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(note, noteId, patient, doctor, date);
    }
}

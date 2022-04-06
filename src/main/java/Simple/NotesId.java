package Simple;

import diabetes.model.Patient;
import diabetes.model.Doctor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class NotesId implements Serializable {
    //private java.sql.Timestamp date;
    //private Patient patient;
    //private Doctor doctor;


    public NotesId(java.sql.Timestamp date, Patient patient, Doctor doctor) {
        this.date = date;
        this.patient = patient;
        this.doctor = doctor;
    }

    public NotesId(){

    }

    @ManyToOne
    @JoinColumn(name = "patientId", referencedColumnName = "patientId")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctorId", referencedColumnName = "doctorId")
    private Doctor doctor;

    @Column(name = "date")
    private java.sql.Timestamp date;


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
